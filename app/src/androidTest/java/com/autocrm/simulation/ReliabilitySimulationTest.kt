package com.autocrm.simulation

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.autocrm.data.local.AppDatabase
import com.autocrm.data.local.dao.*
import com.autocrm.data.local.entities.*
import com.autocrm.data.repository.CarRepositoryImpl
import com.autocrm.domain.model.*
import com.autocrm.sync.EntityConflictResolver
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import java.util.UUID

/**
 * STAGE 3: SYSTEM VALIDATION & FAILURE SIMULATION
 * Tests reliability of Room transactions, SyncQueue guarantees, and Conflict Resolution.
 */
@RunWith(AndroidJUnit4::class)
class ReliabilitySimulationTest {

    private lateinit var db: AppDatabase
    private lateinit var carDao: CarDao
    private lateinit var syncQueueDao: SyncQueueDao
    private lateinit var auditLogDao: AuditLogDao
    private lateinit var carRepository: CarRepositoryImpl
    private lateinit var conflictResolver: EntityConflictResolver

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        carDao = db.carDao()
        syncQueueDao = db.syncQueueDao()
        auditLogDao = db.auditLogDao()
        
        conflictResolver = EntityConflictResolver()
        
        carRepository = CarRepositoryImpl(
            db = db,
            carDao = carDao,
            expenseDao = db.expenseDao(),
            photoDao = db.photoDao(),
            syncQueueDao = syncQueueDao,
            auditLogDao = auditLogDao
        )
    }

    @After
    fun closeDb() {
        db.close()
    }

    // ==========================================
    // 1. TRANSACTION CRASH SIMULATION
    // ==========================================
    @Test
    fun testTransactionRollback_WhenCrashDuringSave() = runBlocking {
        val car = Car(
            make = "Toyota",
            model = "Camry",
            year = 2023,
            purchasePrice = 20000.0,
            currency = "TJS"
        )
        
        // Simulating a crash by throwing an exception inside the transaction.
        // We write a custom extension on db to simulate failure.
        var exceptionThrown = false
        try {
            db.runInTransaction {
                runBlocking {
                    carDao.insertCar(car.toEntity())
                    // Simulate crash BEFORE SyncQueue and AuditLog
                    throw RuntimeException("Simulated crash during transaction!")
                }
            }
        } catch (e: Exception) {
            exceptionThrown = true
        }

        assertTrue("Exception should be thrown", exceptionThrown)
        
        // VERIFY: The database should rollback the insertCar operation.
        val cars = carDao.getAllCarsWithDetails().first()
        assertTrue("Database should be empty due to rollback", cars.isEmpty())
        
        // SyncQueue and AuditLog should also be empty
        val queue = syncQueueDao.getPendingOperations()
        assertTrue(queue.isEmpty())
    }

    // ==========================================
    // 2. DATA CONSISTENCY AUDIT TEST
    // ==========================================
    @Test
    fun testDataConsistency_AfterSuccessfulSave() = runBlocking {
        val car = Car(
            make = "Honda",
            model = "Accord",
            year = 2022,
            purchasePrice = 18000.0
        )
        
        carRepository.saveCar(car)
        
        // VERIFY: All 3 sources must be consistent
        val cars = carDao.getAllCarsWithDetails().first()
        assertEquals(1, cars.size)
        assertEquals(car.uuid, cars[0].car.uuid)
        
        val queue = syncQueueDao.getPendingOperations()
        assertEquals(1, queue.size)
        assertEquals("Car", queue[0].entityType)
        assertEquals(car.uuid, queue[0].entityId)
        assertEquals("UPDATE", queue[0].operationType)
        
        val auditLogs = auditLogDao.getRecentLogs(10).first()
        assertEquals(1, auditLogs.size)
        assertEquals("Car", auditLogs[0].entityType)
        assertEquals("UPDATE", auditLogs[0].actionType)
    }

    // ==========================================
    // 3. CONFLICT EDGE CASE SIMULATION
    // ==========================================
    @Test
    fun testConflictResolution_LWW_Timestamp() {
        val oldTime = 1000L
        val newTime = 2000L
        
        val localCar = Car(make = "BMW", model = "X5", year = 2020, purchasePrice = 30000.0, updatedAt = oldTime, version = 1)
        val remoteCar = localCar.copy(updatedAt = newTime, version = 1, notes = "Remote Note")
        
        // Remote has same version but newer timestamp -> Remote wins
        val resolved = conflictResolver.resolveCarConflict(localCar, remoteCar)
        assertEquals("Remote Note", resolved.notes)
        assertEquals(newTime, resolved.updatedAt)
    }
    
    @Test
    fun testConflictResolution_LWW_Version() {
        val time = 1000L
        val localCar = Car(make = "Audi", model = "Q7", year = 2021, purchasePrice = 35000.0, updatedAt = time, version = 2, notes = "Local Note")
        val remoteCar = localCar.copy(updatedAt = time + 1000L, version = 1, notes = "Remote Note") // Older version, but newer time!
        
        // Local has higher version -> Local wins despite time
        val resolved = conflictResolver.resolveCarConflict(localCar, remoteCar)
        assertEquals("Local Note", resolved.notes)
        assertEquals(2, resolved.version)
    }

    @Test
    fun testConflictResolution_AppendOnlyExpense() {
        val expense = Expense(carUuid = UUID.randomUUID().toString(), category = ExpenseCategory.DELIVERY, amount = 500.0)
        val remoteExpense = expense.copy(amount = 600.0) // Malicious or wrong server update
        
        // Append-only means we preserve local if it exists, or just append remote as new if UUID differs. 
        // Here UUID is same, local wins.
        val resolved = conflictResolver.resolveExpenseConflict(expense, remoteExpense)
        assertEquals(500.0, resolved.amount, 0.0)
    }

    // ==========================================
    // 4. OUTBOX / SYNC QUEUE GUARANTEE TEST
    // ==========================================
    @Test
    fun testSyncQueue_DeadLetterTransition() = runBlocking {
        val entity = SyncQueueEntity(
            operationId = UUID.randomUUID().toString(),
            entityType = "Car",
            entityId = UUID.randomUUID().toString(),
            operationType = "CREATE",
            payload = null,
            retryCount = 4 // Max retries
        )
        syncQueueDao.insert(entity)
        
        // Simulate failure marking
        val pending = syncQueueDao.getPendingOperations()
        assertEquals(1, pending.size)
        
        val op = pending[0]
        syncQueueDao.markDeadLetter(op.id)
        
        // Verify it's no longer pending
        val newPending = syncQueueDao.getPendingOperations()
        assertTrue(newPending.isEmpty())
    }
}
