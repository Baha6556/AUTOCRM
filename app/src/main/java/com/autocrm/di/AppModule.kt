package com.autocrm.di

import android.content.Context
import androidx.room.Room
import com.autocrm.data.local.AppDatabase
import com.autocrm.data.local.dao.CarDao
import com.autocrm.data.local.dao.ExpenseDao
import com.autocrm.data.local.dao.PhotoDao
import com.autocrm.data.repository.CarRepositoryImpl
import com.autocrm.domain.repository.CarRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

import com.autocrm.data.local.dao.SyncQueueDao
import com.autocrm.data.local.dao.AuditLogDao
import com.autocrm.sync.ConflictResolver
import com.autocrm.sync.EntityConflictResolver

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext ctx: Context): AppDatabase =
        Room.databaseBuilder(ctx, AppDatabase::class.java, "autocrm.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides fun provideCarDao(db: AppDatabase): CarDao         = db.carDao()
    @Provides fun provideExpenseDao(db: AppDatabase): ExpenseDao = db.expenseDao()
    @Provides fun providePhotoDao(db: AppDatabase): PhotoDao     = db.photoDao()
    @Provides fun provideSyncQueueDao(db: AppDatabase): SyncQueueDao = db.syncQueueDao()
    @Provides fun provideAuditLogDao(db: AppDatabase): AuditLogDao = db.auditLogDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCarRepository(impl: CarRepositoryImpl): CarRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class SyncModule {
    @Binds
    @Singleton
    abstract fun bindConflictResolver(impl: EntityConflictResolver): ConflictResolver
}
