package com.autocrm.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.autocrm.data.local.dao.AuditLogDao;
import com.autocrm.data.local.dao.AuditLogDao_Impl;
import com.autocrm.data.local.dao.CarDao;
import com.autocrm.data.local.dao.CarDao_Impl;
import com.autocrm.data.local.dao.ClientDao;
import com.autocrm.data.local.dao.ClientDao_Impl;
import com.autocrm.data.local.dao.DeliveryDao;
import com.autocrm.data.local.dao.DeliveryDao_Impl;
import com.autocrm.data.local.dao.ExpenseDao;
import com.autocrm.data.local.dao.ExpenseDao_Impl;
import com.autocrm.data.local.dao.PhotoDao;
import com.autocrm.data.local.dao.PhotoDao_Impl;
import com.autocrm.data.local.dao.SupplierDao;
import com.autocrm.data.local.dao.SupplierDao_Impl;
import com.autocrm.data.local.dao.SyncQueueDao;
import com.autocrm.data.local.dao.SyncQueueDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile CarDao _carDao;

  private volatile ExpenseDao _expenseDao;

  private volatile PhotoDao _photoDao;

  private volatile SupplierDao _supplierDao;

  private volatile ClientDao _clientDao;

  private volatile DeliveryDao _deliveryDao;

  private volatile SyncQueueDao _syncQueueDao;

  private volatile AuditLogDao _auditLogDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `cars` (`uuid` TEXT NOT NULL, `make` TEXT NOT NULL, `model` TEXT NOT NULL, `year` INTEGER NOT NULL, `vin` TEXT, `mileage` INTEGER, `country` TEXT NOT NULL, `color` TEXT, `engine_vol` REAL, `transmission` TEXT, `purchase_price` REAL NOT NULL, `est_sale_price` REAL, `sale_price` REAL, `currency` TEXT NOT NULL, `status` TEXT NOT NULL, `purchase_date` INTEGER NOT NULL, `sale_date` INTEGER, `arrival_date` INTEGER, `notes` TEXT, `supplier_uuid` TEXT, `client_uuid` TEXT, `delivery_status` TEXT NOT NULL, `server_id` TEXT, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, `is_deleted` INTEGER NOT NULL, `sync_status` TEXT NOT NULL, `version` INTEGER NOT NULL, PRIMARY KEY(`uuid`), FOREIGN KEY(`supplier_uuid`) REFERENCES `suppliers`(`uuid`) ON UPDATE NO ACTION ON DELETE SET NULL , FOREIGN KEY(`client_uuid`) REFERENCES `clients`(`uuid`) ON UPDATE NO ACTION ON DELETE SET NULL )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_cars_supplier_uuid` ON `cars` (`supplier_uuid`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_cars_client_uuid` ON `cars` (`client_uuid`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_cars_status` ON `cars` (`status`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_cars_sync_status` ON `cars` (`sync_status`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_cars_updated_at` ON `cars` (`updated_at`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `car_photos` (`uuid` TEXT NOT NULL, `car_uuid` TEXT NOT NULL, `file_path` TEXT NOT NULL, `remote_url` TEXT, `sort_order` INTEGER NOT NULL, `is_primary` INTEGER NOT NULL, `server_id` TEXT, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, `is_deleted` INTEGER NOT NULL, `sync_status` TEXT NOT NULL, `version` INTEGER NOT NULL, PRIMARY KEY(`uuid`), FOREIGN KEY(`car_uuid`) REFERENCES `cars`(`uuid`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_car_photos_car_uuid` ON `car_photos` (`car_uuid`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `expenses` (`uuid` TEXT NOT NULL, `car_uuid` TEXT NOT NULL, `category` TEXT NOT NULL, `amount` REAL NOT NULL, `currency` TEXT NOT NULL, `description` TEXT, `date` INTEGER NOT NULL, `server_id` TEXT, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, `is_deleted` INTEGER NOT NULL, `sync_status` TEXT NOT NULL, `version` INTEGER NOT NULL, PRIMARY KEY(`uuid`), FOREIGN KEY(`car_uuid`) REFERENCES `cars`(`uuid`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_expenses_car_uuid` ON `expenses` (`car_uuid`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_expenses_sync_status` ON `expenses` (`sync_status`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_expenses_updated_at` ON `expenses` (`updated_at`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `suppliers` (`uuid` TEXT NOT NULL, `company_name` TEXT NOT NULL, `contact_person` TEXT, `phone` TEXT, `telegram` TEXT, `whatsapp` TEXT, `country` TEXT NOT NULL, `city` TEXT, `address` TEXT, `notes` TEXT, `rating` REAL NOT NULL, `server_id` TEXT, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, `is_deleted` INTEGER NOT NULL, `sync_status` TEXT NOT NULL, `version` INTEGER NOT NULL, PRIMARY KEY(`uuid`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `clients` (`uuid` TEXT NOT NULL, `full_name` TEXT NOT NULL, `phone` TEXT NOT NULL, `telegram` TEXT, `whatsapp` TEXT, `city` TEXT, `preferred_brands` TEXT, `notes` TEXT, `server_id` TEXT, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, `is_deleted` INTEGER NOT NULL, `sync_status` TEXT NOT NULL, `version` INTEGER NOT NULL, PRIMARY KEY(`uuid`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `delivery_events` (`uuid` TEXT NOT NULL, `car_uuid` TEXT NOT NULL, `status` TEXT NOT NULL, `date` INTEGER NOT NULL, `location` TEXT, `port` TEXT, `carrier_name` TEXT, `container_number` TEXT, `notes` TEXT, `est_delivery_date` INTEGER, `act_delivery_date` INTEGER, `server_id` TEXT, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, `is_deleted` INTEGER NOT NULL, `sync_status` TEXT NOT NULL, `version` INTEGER NOT NULL, PRIMARY KEY(`uuid`), FOREIGN KEY(`car_uuid`) REFERENCES `cars`(`uuid`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_delivery_events_car_uuid` ON `delivery_events` (`car_uuid`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `delivery_photos` (`uuid` TEXT NOT NULL, `event_uuid` TEXT NOT NULL, `file_path` TEXT NOT NULL, `remote_url` TEXT, `server_id` TEXT, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, `is_deleted` INTEGER NOT NULL, `sync_status` TEXT NOT NULL, `version` INTEGER NOT NULL, PRIMARY KEY(`uuid`), FOREIGN KEY(`event_uuid`) REFERENCES `delivery_events`(`uuid`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_delivery_photos_event_uuid` ON `delivery_photos` (`event_uuid`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `sync_queue` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `operation_id` TEXT NOT NULL, `entity_type` TEXT NOT NULL, `entity_id` TEXT NOT NULL, `operation_type` TEXT NOT NULL, `payload` TEXT, `created_at` INTEGER NOT NULL, `last_attempt_at` INTEGER NOT NULL, `retry_count` INTEGER NOT NULL, `status` TEXT NOT NULL)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_sync_queue_status` ON `sync_queue` (`status`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_sync_queue_entity_type` ON `sync_queue` (`entity_type`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_sync_queue_entity_id` ON `sync_queue` (`entity_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `audit_logs` (`uuid` TEXT NOT NULL, `entity_type` TEXT NOT NULL, `entity_id` TEXT NOT NULL, `action_type` TEXT NOT NULL, `changed_fields` TEXT, `old_value` TEXT, `new_value` TEXT, `timestamp` INTEGER NOT NULL, `user_id` TEXT, PRIMARY KEY(`uuid`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '70586131db9ea7bcca7389d1988d812a')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `cars`");
        db.execSQL("DROP TABLE IF EXISTS `car_photos`");
        db.execSQL("DROP TABLE IF EXISTS `expenses`");
        db.execSQL("DROP TABLE IF EXISTS `suppliers`");
        db.execSQL("DROP TABLE IF EXISTS `clients`");
        db.execSQL("DROP TABLE IF EXISTS `delivery_events`");
        db.execSQL("DROP TABLE IF EXISTS `delivery_photos`");
        db.execSQL("DROP TABLE IF EXISTS `sync_queue`");
        db.execSQL("DROP TABLE IF EXISTS `audit_logs`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsCars = new HashMap<String, TableInfo.Column>(28);
        _columnsCars.put("uuid", new TableInfo.Column("uuid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("make", new TableInfo.Column("make", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("model", new TableInfo.Column("model", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("year", new TableInfo.Column("year", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("vin", new TableInfo.Column("vin", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("mileage", new TableInfo.Column("mileage", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("country", new TableInfo.Column("country", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("color", new TableInfo.Column("color", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("engine_vol", new TableInfo.Column("engine_vol", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("transmission", new TableInfo.Column("transmission", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("purchase_price", new TableInfo.Column("purchase_price", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("est_sale_price", new TableInfo.Column("est_sale_price", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("sale_price", new TableInfo.Column("sale_price", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("currency", new TableInfo.Column("currency", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("purchase_date", new TableInfo.Column("purchase_date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("sale_date", new TableInfo.Column("sale_date", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("arrival_date", new TableInfo.Column("arrival_date", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("supplier_uuid", new TableInfo.Column("supplier_uuid", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("client_uuid", new TableInfo.Column("client_uuid", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("delivery_status", new TableInfo.Column("delivery_status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("server_id", new TableInfo.Column("server_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("is_deleted", new TableInfo.Column("is_deleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("sync_status", new TableInfo.Column("sync_status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("version", new TableInfo.Column("version", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCars = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysCars.add(new TableInfo.ForeignKey("suppliers", "SET NULL", "NO ACTION", Arrays.asList("supplier_uuid"), Arrays.asList("uuid")));
        _foreignKeysCars.add(new TableInfo.ForeignKey("clients", "SET NULL", "NO ACTION", Arrays.asList("client_uuid"), Arrays.asList("uuid")));
        final HashSet<TableInfo.Index> _indicesCars = new HashSet<TableInfo.Index>(5);
        _indicesCars.add(new TableInfo.Index("index_cars_supplier_uuid", false, Arrays.asList("supplier_uuid"), Arrays.asList("ASC")));
        _indicesCars.add(new TableInfo.Index("index_cars_client_uuid", false, Arrays.asList("client_uuid"), Arrays.asList("ASC")));
        _indicesCars.add(new TableInfo.Index("index_cars_status", false, Arrays.asList("status"), Arrays.asList("ASC")));
        _indicesCars.add(new TableInfo.Index("index_cars_sync_status", false, Arrays.asList("sync_status"), Arrays.asList("ASC")));
        _indicesCars.add(new TableInfo.Index("index_cars_updated_at", false, Arrays.asList("updated_at"), Arrays.asList("ASC")));
        final TableInfo _infoCars = new TableInfo("cars", _columnsCars, _foreignKeysCars, _indicesCars);
        final TableInfo _existingCars = TableInfo.read(db, "cars");
        if (!_infoCars.equals(_existingCars)) {
          return new RoomOpenHelper.ValidationResult(false, "cars(com.autocrm.data.local.entities.CarEntity).\n"
                  + " Expected:\n" + _infoCars + "\n"
                  + " Found:\n" + _existingCars);
        }
        final HashMap<String, TableInfo.Column> _columnsCarPhotos = new HashMap<String, TableInfo.Column>(12);
        _columnsCarPhotos.put("uuid", new TableInfo.Column("uuid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCarPhotos.put("car_uuid", new TableInfo.Column("car_uuid", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCarPhotos.put("file_path", new TableInfo.Column("file_path", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCarPhotos.put("remote_url", new TableInfo.Column("remote_url", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCarPhotos.put("sort_order", new TableInfo.Column("sort_order", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCarPhotos.put("is_primary", new TableInfo.Column("is_primary", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCarPhotos.put("server_id", new TableInfo.Column("server_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCarPhotos.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCarPhotos.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCarPhotos.put("is_deleted", new TableInfo.Column("is_deleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCarPhotos.put("sync_status", new TableInfo.Column("sync_status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCarPhotos.put("version", new TableInfo.Column("version", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCarPhotos = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysCarPhotos.add(new TableInfo.ForeignKey("cars", "CASCADE", "NO ACTION", Arrays.asList("car_uuid"), Arrays.asList("uuid")));
        final HashSet<TableInfo.Index> _indicesCarPhotos = new HashSet<TableInfo.Index>(1);
        _indicesCarPhotos.add(new TableInfo.Index("index_car_photos_car_uuid", false, Arrays.asList("car_uuid"), Arrays.asList("ASC")));
        final TableInfo _infoCarPhotos = new TableInfo("car_photos", _columnsCarPhotos, _foreignKeysCarPhotos, _indicesCarPhotos);
        final TableInfo _existingCarPhotos = TableInfo.read(db, "car_photos");
        if (!_infoCarPhotos.equals(_existingCarPhotos)) {
          return new RoomOpenHelper.ValidationResult(false, "car_photos(com.autocrm.data.local.entities.CarPhotoEntity).\n"
                  + " Expected:\n" + _infoCarPhotos + "\n"
                  + " Found:\n" + _existingCarPhotos);
        }
        final HashMap<String, TableInfo.Column> _columnsExpenses = new HashMap<String, TableInfo.Column>(13);
        _columnsExpenses.put("uuid", new TableInfo.Column("uuid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("car_uuid", new TableInfo.Column("car_uuid", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("amount", new TableInfo.Column("amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("currency", new TableInfo.Column("currency", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("server_id", new TableInfo.Column("server_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("is_deleted", new TableInfo.Column("is_deleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("sync_status", new TableInfo.Column("sync_status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("version", new TableInfo.Column("version", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysExpenses = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysExpenses.add(new TableInfo.ForeignKey("cars", "CASCADE", "NO ACTION", Arrays.asList("car_uuid"), Arrays.asList("uuid")));
        final HashSet<TableInfo.Index> _indicesExpenses = new HashSet<TableInfo.Index>(3);
        _indicesExpenses.add(new TableInfo.Index("index_expenses_car_uuid", false, Arrays.asList("car_uuid"), Arrays.asList("ASC")));
        _indicesExpenses.add(new TableInfo.Index("index_expenses_sync_status", false, Arrays.asList("sync_status"), Arrays.asList("ASC")));
        _indicesExpenses.add(new TableInfo.Index("index_expenses_updated_at", false, Arrays.asList("updated_at"), Arrays.asList("ASC")));
        final TableInfo _infoExpenses = new TableInfo("expenses", _columnsExpenses, _foreignKeysExpenses, _indicesExpenses);
        final TableInfo _existingExpenses = TableInfo.read(db, "expenses");
        if (!_infoExpenses.equals(_existingExpenses)) {
          return new RoomOpenHelper.ValidationResult(false, "expenses(com.autocrm.data.local.entities.ExpenseEntity).\n"
                  + " Expected:\n" + _infoExpenses + "\n"
                  + " Found:\n" + _existingExpenses);
        }
        final HashMap<String, TableInfo.Column> _columnsSuppliers = new HashMap<String, TableInfo.Column>(17);
        _columnsSuppliers.put("uuid", new TableInfo.Column("uuid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSuppliers.put("company_name", new TableInfo.Column("company_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSuppliers.put("contact_person", new TableInfo.Column("contact_person", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSuppliers.put("phone", new TableInfo.Column("phone", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSuppliers.put("telegram", new TableInfo.Column("telegram", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSuppliers.put("whatsapp", new TableInfo.Column("whatsapp", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSuppliers.put("country", new TableInfo.Column("country", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSuppliers.put("city", new TableInfo.Column("city", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSuppliers.put("address", new TableInfo.Column("address", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSuppliers.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSuppliers.put("rating", new TableInfo.Column("rating", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSuppliers.put("server_id", new TableInfo.Column("server_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSuppliers.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSuppliers.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSuppliers.put("is_deleted", new TableInfo.Column("is_deleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSuppliers.put("sync_status", new TableInfo.Column("sync_status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSuppliers.put("version", new TableInfo.Column("version", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSuppliers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSuppliers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSuppliers = new TableInfo("suppliers", _columnsSuppliers, _foreignKeysSuppliers, _indicesSuppliers);
        final TableInfo _existingSuppliers = TableInfo.read(db, "suppliers");
        if (!_infoSuppliers.equals(_existingSuppliers)) {
          return new RoomOpenHelper.ValidationResult(false, "suppliers(com.autocrm.data.local.entities.SupplierEntity).\n"
                  + " Expected:\n" + _infoSuppliers + "\n"
                  + " Found:\n" + _existingSuppliers);
        }
        final HashMap<String, TableInfo.Column> _columnsClients = new HashMap<String, TableInfo.Column>(14);
        _columnsClients.put("uuid", new TableInfo.Column("uuid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("full_name", new TableInfo.Column("full_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("phone", new TableInfo.Column("phone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("telegram", new TableInfo.Column("telegram", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("whatsapp", new TableInfo.Column("whatsapp", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("city", new TableInfo.Column("city", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("preferred_brands", new TableInfo.Column("preferred_brands", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("server_id", new TableInfo.Column("server_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("is_deleted", new TableInfo.Column("is_deleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("sync_status", new TableInfo.Column("sync_status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClients.put("version", new TableInfo.Column("version", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysClients = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesClients = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoClients = new TableInfo("clients", _columnsClients, _foreignKeysClients, _indicesClients);
        final TableInfo _existingClients = TableInfo.read(db, "clients");
        if (!_infoClients.equals(_existingClients)) {
          return new RoomOpenHelper.ValidationResult(false, "clients(com.autocrm.data.local.entities.ClientEntity).\n"
                  + " Expected:\n" + _infoClients + "\n"
                  + " Found:\n" + _existingClients);
        }
        final HashMap<String, TableInfo.Column> _columnsDeliveryEvents = new HashMap<String, TableInfo.Column>(17);
        _columnsDeliveryEvents.put("uuid", new TableInfo.Column("uuid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryEvents.put("car_uuid", new TableInfo.Column("car_uuid", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryEvents.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryEvents.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryEvents.put("location", new TableInfo.Column("location", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryEvents.put("port", new TableInfo.Column("port", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryEvents.put("carrier_name", new TableInfo.Column("carrier_name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryEvents.put("container_number", new TableInfo.Column("container_number", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryEvents.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryEvents.put("est_delivery_date", new TableInfo.Column("est_delivery_date", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryEvents.put("act_delivery_date", new TableInfo.Column("act_delivery_date", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryEvents.put("server_id", new TableInfo.Column("server_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryEvents.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryEvents.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryEvents.put("is_deleted", new TableInfo.Column("is_deleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryEvents.put("sync_status", new TableInfo.Column("sync_status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryEvents.put("version", new TableInfo.Column("version", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDeliveryEvents = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysDeliveryEvents.add(new TableInfo.ForeignKey("cars", "CASCADE", "NO ACTION", Arrays.asList("car_uuid"), Arrays.asList("uuid")));
        final HashSet<TableInfo.Index> _indicesDeliveryEvents = new HashSet<TableInfo.Index>(1);
        _indicesDeliveryEvents.add(new TableInfo.Index("index_delivery_events_car_uuid", false, Arrays.asList("car_uuid"), Arrays.asList("ASC")));
        final TableInfo _infoDeliveryEvents = new TableInfo("delivery_events", _columnsDeliveryEvents, _foreignKeysDeliveryEvents, _indicesDeliveryEvents);
        final TableInfo _existingDeliveryEvents = TableInfo.read(db, "delivery_events");
        if (!_infoDeliveryEvents.equals(_existingDeliveryEvents)) {
          return new RoomOpenHelper.ValidationResult(false, "delivery_events(com.autocrm.data.local.entities.DeliveryEventEntity).\n"
                  + " Expected:\n" + _infoDeliveryEvents + "\n"
                  + " Found:\n" + _existingDeliveryEvents);
        }
        final HashMap<String, TableInfo.Column> _columnsDeliveryPhotos = new HashMap<String, TableInfo.Column>(10);
        _columnsDeliveryPhotos.put("uuid", new TableInfo.Column("uuid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryPhotos.put("event_uuid", new TableInfo.Column("event_uuid", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryPhotos.put("file_path", new TableInfo.Column("file_path", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryPhotos.put("remote_url", new TableInfo.Column("remote_url", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryPhotos.put("server_id", new TableInfo.Column("server_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryPhotos.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryPhotos.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryPhotos.put("is_deleted", new TableInfo.Column("is_deleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryPhotos.put("sync_status", new TableInfo.Column("sync_status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryPhotos.put("version", new TableInfo.Column("version", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDeliveryPhotos = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysDeliveryPhotos.add(new TableInfo.ForeignKey("delivery_events", "CASCADE", "NO ACTION", Arrays.asList("event_uuid"), Arrays.asList("uuid")));
        final HashSet<TableInfo.Index> _indicesDeliveryPhotos = new HashSet<TableInfo.Index>(1);
        _indicesDeliveryPhotos.add(new TableInfo.Index("index_delivery_photos_event_uuid", false, Arrays.asList("event_uuid"), Arrays.asList("ASC")));
        final TableInfo _infoDeliveryPhotos = new TableInfo("delivery_photos", _columnsDeliveryPhotos, _foreignKeysDeliveryPhotos, _indicesDeliveryPhotos);
        final TableInfo _existingDeliveryPhotos = TableInfo.read(db, "delivery_photos");
        if (!_infoDeliveryPhotos.equals(_existingDeliveryPhotos)) {
          return new RoomOpenHelper.ValidationResult(false, "delivery_photos(com.autocrm.data.local.entities.DeliveryPhotoEntity).\n"
                  + " Expected:\n" + _infoDeliveryPhotos + "\n"
                  + " Found:\n" + _existingDeliveryPhotos);
        }
        final HashMap<String, TableInfo.Column> _columnsSyncQueue = new HashMap<String, TableInfo.Column>(10);
        _columnsSyncQueue.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncQueue.put("operation_id", new TableInfo.Column("operation_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncQueue.put("entity_type", new TableInfo.Column("entity_type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncQueue.put("entity_id", new TableInfo.Column("entity_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncQueue.put("operation_type", new TableInfo.Column("operation_type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncQueue.put("payload", new TableInfo.Column("payload", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncQueue.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncQueue.put("last_attempt_at", new TableInfo.Column("last_attempt_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncQueue.put("retry_count", new TableInfo.Column("retry_count", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncQueue.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSyncQueue = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSyncQueue = new HashSet<TableInfo.Index>(3);
        _indicesSyncQueue.add(new TableInfo.Index("index_sync_queue_status", false, Arrays.asList("status"), Arrays.asList("ASC")));
        _indicesSyncQueue.add(new TableInfo.Index("index_sync_queue_entity_type", false, Arrays.asList("entity_type"), Arrays.asList("ASC")));
        _indicesSyncQueue.add(new TableInfo.Index("index_sync_queue_entity_id", false, Arrays.asList("entity_id"), Arrays.asList("ASC")));
        final TableInfo _infoSyncQueue = new TableInfo("sync_queue", _columnsSyncQueue, _foreignKeysSyncQueue, _indicesSyncQueue);
        final TableInfo _existingSyncQueue = TableInfo.read(db, "sync_queue");
        if (!_infoSyncQueue.equals(_existingSyncQueue)) {
          return new RoomOpenHelper.ValidationResult(false, "sync_queue(com.autocrm.data.local.entities.SyncQueueEntity).\n"
                  + " Expected:\n" + _infoSyncQueue + "\n"
                  + " Found:\n" + _existingSyncQueue);
        }
        final HashMap<String, TableInfo.Column> _columnsAuditLogs = new HashMap<String, TableInfo.Column>(9);
        _columnsAuditLogs.put("uuid", new TableInfo.Column("uuid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuditLogs.put("entity_type", new TableInfo.Column("entity_type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuditLogs.put("entity_id", new TableInfo.Column("entity_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuditLogs.put("action_type", new TableInfo.Column("action_type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuditLogs.put("changed_fields", new TableInfo.Column("changed_fields", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuditLogs.put("old_value", new TableInfo.Column("old_value", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuditLogs.put("new_value", new TableInfo.Column("new_value", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuditLogs.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuditLogs.put("user_id", new TableInfo.Column("user_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAuditLogs = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAuditLogs = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAuditLogs = new TableInfo("audit_logs", _columnsAuditLogs, _foreignKeysAuditLogs, _indicesAuditLogs);
        final TableInfo _existingAuditLogs = TableInfo.read(db, "audit_logs");
        if (!_infoAuditLogs.equals(_existingAuditLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "audit_logs(com.autocrm.data.local.entities.AuditLogEntity).\n"
                  + " Expected:\n" + _infoAuditLogs + "\n"
                  + " Found:\n" + _existingAuditLogs);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "70586131db9ea7bcca7389d1988d812a", "a8fbbce422996d62f98726ba261b4a52");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "cars","car_photos","expenses","suppliers","clients","delivery_events","delivery_photos","sync_queue","audit_logs");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `cars`");
      _db.execSQL("DELETE FROM `car_photos`");
      _db.execSQL("DELETE FROM `expenses`");
      _db.execSQL("DELETE FROM `suppliers`");
      _db.execSQL("DELETE FROM `clients`");
      _db.execSQL("DELETE FROM `delivery_events`");
      _db.execSQL("DELETE FROM `delivery_photos`");
      _db.execSQL("DELETE FROM `sync_queue`");
      _db.execSQL("DELETE FROM `audit_logs`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(CarDao.class, CarDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ExpenseDao.class, ExpenseDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PhotoDao.class, PhotoDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SupplierDao.class, SupplierDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ClientDao.class, ClientDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(DeliveryDao.class, DeliveryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SyncQueueDao.class, SyncQueueDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AuditLogDao.class, AuditLogDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public CarDao carDao() {
    if (_carDao != null) {
      return _carDao;
    } else {
      synchronized(this) {
        if(_carDao == null) {
          _carDao = new CarDao_Impl(this);
        }
        return _carDao;
      }
    }
  }

  @Override
  public ExpenseDao expenseDao() {
    if (_expenseDao != null) {
      return _expenseDao;
    } else {
      synchronized(this) {
        if(_expenseDao == null) {
          _expenseDao = new ExpenseDao_Impl(this);
        }
        return _expenseDao;
      }
    }
  }

  @Override
  public PhotoDao photoDao() {
    if (_photoDao != null) {
      return _photoDao;
    } else {
      synchronized(this) {
        if(_photoDao == null) {
          _photoDao = new PhotoDao_Impl(this);
        }
        return _photoDao;
      }
    }
  }

  @Override
  public SupplierDao supplierDao() {
    if (_supplierDao != null) {
      return _supplierDao;
    } else {
      synchronized(this) {
        if(_supplierDao == null) {
          _supplierDao = new SupplierDao_Impl(this);
        }
        return _supplierDao;
      }
    }
  }

  @Override
  public ClientDao clientDao() {
    if (_clientDao != null) {
      return _clientDao;
    } else {
      synchronized(this) {
        if(_clientDao == null) {
          _clientDao = new ClientDao_Impl(this);
        }
        return _clientDao;
      }
    }
  }

  @Override
  public DeliveryDao deliveryDao() {
    if (_deliveryDao != null) {
      return _deliveryDao;
    } else {
      synchronized(this) {
        if(_deliveryDao == null) {
          _deliveryDao = new DeliveryDao_Impl(this);
        }
        return _deliveryDao;
      }
    }
  }

  @Override
  public SyncQueueDao syncQueueDao() {
    if (_syncQueueDao != null) {
      return _syncQueueDao;
    } else {
      synchronized(this) {
        if(_syncQueueDao == null) {
          _syncQueueDao = new SyncQueueDao_Impl(this);
        }
        return _syncQueueDao;
      }
    }
  }

  @Override
  public AuditLogDao auditLogDao() {
    if (_auditLogDao != null) {
      return _auditLogDao;
    } else {
      synchronized(this) {
        if(_auditLogDao == null) {
          _auditLogDao = new AuditLogDao_Impl(this);
        }
        return _auditLogDao;
      }
    }
  }
}
