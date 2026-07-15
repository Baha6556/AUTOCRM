package com.autocrm.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.RelationUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.autocrm.data.local.converters.Converters;
import com.autocrm.data.local.entities.CarEntity;
import com.autocrm.data.local.entities.CarPhotoEntity;
import com.autocrm.data.local.entities.CarWithDetailsEntity;
import com.autocrm.data.local.entities.ClientEntity;
import com.autocrm.data.local.entities.DeliveryEventEntity;
import com.autocrm.data.local.entities.DeliveryEventWithPhotosEntity;
import com.autocrm.data.local.entities.DeliveryPhotoEntity;
import com.autocrm.data.local.entities.ExpenseEntity;
import com.autocrm.data.local.entities.SupplierEntity;
import com.autocrm.data.local.entities.SupplierWithCarsEntity;
import com.autocrm.domain.model.CarStatus;
import com.autocrm.domain.model.DeliveryStatus;
import com.autocrm.domain.model.EntitySyncStatus;
import com.autocrm.domain.model.ExpenseCategory;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SupplierDao_Impl implements SupplierDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SupplierEntity> __insertionAdapterOfSupplierEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<SupplierEntity> __updateAdapterOfSupplierEntity;

  private final SharedSQLiteStatement __preparedStmtOfSoftDelete;

  private final SharedSQLiteStatement __preparedStmtOfMarkSynced;

  private final SharedSQLiteStatement __preparedStmtOfMarkFailed;

  public SupplierDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSupplierEntity = new EntityInsertionAdapter<SupplierEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `suppliers` (`uuid`,`company_name`,`contact_person`,`phone`,`telegram`,`whatsapp`,`country`,`city`,`address`,`notes`,`rating`,`server_id`,`created_at`,`updated_at`,`is_deleted`,`sync_status`,`version`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SupplierEntity entity) {
        statement.bindString(1, entity.getUuid());
        statement.bindString(2, entity.getCompanyName());
        if (entity.getContactPerson() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getContactPerson());
        }
        if (entity.getPhone() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getPhone());
        }
        if (entity.getTelegram() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getTelegram());
        }
        if (entity.getWhatsapp() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getWhatsapp());
        }
        statement.bindString(7, entity.getCountry());
        if (entity.getCity() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getCity());
        }
        if (entity.getAddress() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getAddress());
        }
        if (entity.getNotes() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getNotes());
        }
        statement.bindDouble(11, entity.getRating());
        if (entity.getServerId() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getServerId());
        }
        statement.bindLong(13, entity.getCreatedAt());
        statement.bindLong(14, entity.getUpdatedAt());
        final int _tmp = entity.isDeleted() ? 1 : 0;
        statement.bindLong(15, _tmp);
        final String _tmp_1 = __converters.fromEntitySyncStatus(entity.getSyncStatus());
        statement.bindString(16, _tmp_1);
        statement.bindLong(17, entity.getVersion());
      }
    };
    this.__updateAdapterOfSupplierEntity = new EntityDeletionOrUpdateAdapter<SupplierEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `suppliers` SET `uuid` = ?,`company_name` = ?,`contact_person` = ?,`phone` = ?,`telegram` = ?,`whatsapp` = ?,`country` = ?,`city` = ?,`address` = ?,`notes` = ?,`rating` = ?,`server_id` = ?,`created_at` = ?,`updated_at` = ?,`is_deleted` = ?,`sync_status` = ?,`version` = ? WHERE `uuid` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SupplierEntity entity) {
        statement.bindString(1, entity.getUuid());
        statement.bindString(2, entity.getCompanyName());
        if (entity.getContactPerson() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getContactPerson());
        }
        if (entity.getPhone() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getPhone());
        }
        if (entity.getTelegram() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getTelegram());
        }
        if (entity.getWhatsapp() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getWhatsapp());
        }
        statement.bindString(7, entity.getCountry());
        if (entity.getCity() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getCity());
        }
        if (entity.getAddress() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getAddress());
        }
        if (entity.getNotes() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getNotes());
        }
        statement.bindDouble(11, entity.getRating());
        if (entity.getServerId() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getServerId());
        }
        statement.bindLong(13, entity.getCreatedAt());
        statement.bindLong(14, entity.getUpdatedAt());
        final int _tmp = entity.isDeleted() ? 1 : 0;
        statement.bindLong(15, _tmp);
        final String _tmp_1 = __converters.fromEntitySyncStatus(entity.getSyncStatus());
        statement.bindString(16, _tmp_1);
        statement.bindLong(17, entity.getVersion());
        statement.bindString(18, entity.getUuid());
      }
    };
    this.__preparedStmtOfSoftDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE suppliers SET is_deleted = 1, sync_status = 'PENDING', updated_at = ? WHERE uuid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkSynced = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE suppliers SET server_id = ?, sync_status = 'SYNCED', updated_at = ? WHERE uuid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkFailed = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE suppliers SET sync_status = 'FAILED' WHERE uuid = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final SupplierEntity supplier,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSupplierEntity.insert(supplier);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final SupplierEntity supplier,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfSupplierEntity.handle(supplier);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object softDelete(final String uuid, final long ts,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSoftDelete.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, ts);
        _argIndex = 2;
        _stmt.bindString(_argIndex, uuid);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfSoftDelete.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markSynced(final String uuid, final String sid, final long ts,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkSynced.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, sid);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, ts);
        _argIndex = 3;
        _stmt.bindString(_argIndex, uuid);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkSynced.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markFailed(final String uuid, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkFailed.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, uuid);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkFailed.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<SupplierEntity>> getAllSuppliers() {
    final String _sql = "SELECT * FROM suppliers WHERE is_deleted = 0 ORDER BY created_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"suppliers"}, new Callable<List<SupplierEntity>>() {
      @Override
      @NonNull
      public List<SupplierEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfCompanyName = CursorUtil.getColumnIndexOrThrow(_cursor, "company_name");
          final int _cursorIndexOfContactPerson = CursorUtil.getColumnIndexOrThrow(_cursor, "contact_person");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfTelegram = CursorUtil.getColumnIndexOrThrow(_cursor, "telegram");
          final int _cursorIndexOfWhatsapp = CursorUtil.getColumnIndexOrThrow(_cursor, "whatsapp");
          final int _cursorIndexOfCountry = CursorUtil.getColumnIndexOrThrow(_cursor, "country");
          final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfServerId = CursorUtil.getColumnIndexOrThrow(_cursor, "server_id");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "sync_status");
          final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
          final List<SupplierEntity> _result = new ArrayList<SupplierEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SupplierEntity _item;
            final String _tmpUuid;
            _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
            final String _tmpCompanyName;
            _tmpCompanyName = _cursor.getString(_cursorIndexOfCompanyName);
            final String _tmpContactPerson;
            if (_cursor.isNull(_cursorIndexOfContactPerson)) {
              _tmpContactPerson = null;
            } else {
              _tmpContactPerson = _cursor.getString(_cursorIndexOfContactPerson);
            }
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            final String _tmpTelegram;
            if (_cursor.isNull(_cursorIndexOfTelegram)) {
              _tmpTelegram = null;
            } else {
              _tmpTelegram = _cursor.getString(_cursorIndexOfTelegram);
            }
            final String _tmpWhatsapp;
            if (_cursor.isNull(_cursorIndexOfWhatsapp)) {
              _tmpWhatsapp = null;
            } else {
              _tmpWhatsapp = _cursor.getString(_cursorIndexOfWhatsapp);
            }
            final String _tmpCountry;
            _tmpCountry = _cursor.getString(_cursorIndexOfCountry);
            final String _tmpCity;
            if (_cursor.isNull(_cursorIndexOfCity)) {
              _tmpCity = null;
            } else {
              _tmpCity = _cursor.getString(_cursorIndexOfCity);
            }
            final String _tmpAddress;
            if (_cursor.isNull(_cursorIndexOfAddress)) {
              _tmpAddress = null;
            } else {
              _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final float _tmpRating;
            _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
            final String _tmpServerId;
            if (_cursor.isNull(_cursorIndexOfServerId)) {
              _tmpServerId = null;
            } else {
              _tmpServerId = _cursor.getString(_cursorIndexOfServerId);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsDeleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp != 0;
            final EntitySyncStatus _tmpSyncStatus;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfSyncStatus);
            _tmpSyncStatus = __converters.toEntitySyncStatus(_tmp_1);
            final long _tmpVersion;
            _tmpVersion = _cursor.getLong(_cursorIndexOfVersion);
            _item = new SupplierEntity(_tmpUuid,_tmpCompanyName,_tmpContactPerson,_tmpPhone,_tmpTelegram,_tmpWhatsapp,_tmpCountry,_tmpCity,_tmpAddress,_tmpNotes,_tmpRating,_tmpServerId,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsDeleted,_tmpSyncStatus,_tmpVersion);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<SupplierWithCarsEntity> getSupplierWithCars(final String uuid) {
    final String _sql = "SELECT * FROM suppliers WHERE uuid = ? AND is_deleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, uuid);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"expenses", "car_photos",
        "delivery_photos", "delivery_events", "suppliers", "clients",
        "cars"}, new Callable<SupplierWithCarsEntity>() {
      @Override
      @Nullable
      public SupplierWithCarsEntity call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
            final int _cursorIndexOfCompanyName = CursorUtil.getColumnIndexOrThrow(_cursor, "company_name");
            final int _cursorIndexOfContactPerson = CursorUtil.getColumnIndexOrThrow(_cursor, "contact_person");
            final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
            final int _cursorIndexOfTelegram = CursorUtil.getColumnIndexOrThrow(_cursor, "telegram");
            final int _cursorIndexOfWhatsapp = CursorUtil.getColumnIndexOrThrow(_cursor, "whatsapp");
            final int _cursorIndexOfCountry = CursorUtil.getColumnIndexOrThrow(_cursor, "country");
            final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
            final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
            final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
            final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
            final int _cursorIndexOfServerId = CursorUtil.getColumnIndexOrThrow(_cursor, "server_id");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
            final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
            final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "sync_status");
            final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
            final ArrayMap<String, ArrayList<CarWithDetailsEntity>> _collectionCars = new ArrayMap<String, ArrayList<CarWithDetailsEntity>>();
            while (_cursor.moveToNext()) {
              final String _tmpKey;
              _tmpKey = _cursor.getString(_cursorIndexOfUuid);
              if (!_collectionCars.containsKey(_tmpKey)) {
                _collectionCars.put(_tmpKey, new ArrayList<CarWithDetailsEntity>());
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipcarsAscomAutocrmDataLocalEntitiesCarWithDetailsEntity(_collectionCars);
            final SupplierWithCarsEntity _result;
            if (_cursor.moveToFirst()) {
              final SupplierEntity _tmpSupplier;
              final String _tmpUuid;
              _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
              final String _tmpCompanyName;
              _tmpCompanyName = _cursor.getString(_cursorIndexOfCompanyName);
              final String _tmpContactPerson;
              if (_cursor.isNull(_cursorIndexOfContactPerson)) {
                _tmpContactPerson = null;
              } else {
                _tmpContactPerson = _cursor.getString(_cursorIndexOfContactPerson);
              }
              final String _tmpPhone;
              if (_cursor.isNull(_cursorIndexOfPhone)) {
                _tmpPhone = null;
              } else {
                _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
              }
              final String _tmpTelegram;
              if (_cursor.isNull(_cursorIndexOfTelegram)) {
                _tmpTelegram = null;
              } else {
                _tmpTelegram = _cursor.getString(_cursorIndexOfTelegram);
              }
              final String _tmpWhatsapp;
              if (_cursor.isNull(_cursorIndexOfWhatsapp)) {
                _tmpWhatsapp = null;
              } else {
                _tmpWhatsapp = _cursor.getString(_cursorIndexOfWhatsapp);
              }
              final String _tmpCountry;
              _tmpCountry = _cursor.getString(_cursorIndexOfCountry);
              final String _tmpCity;
              if (_cursor.isNull(_cursorIndexOfCity)) {
                _tmpCity = null;
              } else {
                _tmpCity = _cursor.getString(_cursorIndexOfCity);
              }
              final String _tmpAddress;
              if (_cursor.isNull(_cursorIndexOfAddress)) {
                _tmpAddress = null;
              } else {
                _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
              }
              final String _tmpNotes;
              if (_cursor.isNull(_cursorIndexOfNotes)) {
                _tmpNotes = null;
              } else {
                _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
              }
              final float _tmpRating;
              _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
              final String _tmpServerId;
              if (_cursor.isNull(_cursorIndexOfServerId)) {
                _tmpServerId = null;
              } else {
                _tmpServerId = _cursor.getString(_cursorIndexOfServerId);
              }
              final long _tmpCreatedAt;
              _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
              final long _tmpUpdatedAt;
              _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
              final boolean _tmpIsDeleted;
              final int _tmp;
              _tmp = _cursor.getInt(_cursorIndexOfIsDeleted);
              _tmpIsDeleted = _tmp != 0;
              final EntitySyncStatus _tmpSyncStatus;
              final String _tmp_1;
              _tmp_1 = _cursor.getString(_cursorIndexOfSyncStatus);
              _tmpSyncStatus = __converters.toEntitySyncStatus(_tmp_1);
              final long _tmpVersion;
              _tmpVersion = _cursor.getLong(_cursorIndexOfVersion);
              _tmpSupplier = new SupplierEntity(_tmpUuid,_tmpCompanyName,_tmpContactPerson,_tmpPhone,_tmpTelegram,_tmpWhatsapp,_tmpCountry,_tmpCity,_tmpAddress,_tmpNotes,_tmpRating,_tmpServerId,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsDeleted,_tmpSyncStatus,_tmpVersion);
              final ArrayList<CarWithDetailsEntity> _tmpCarsCollection;
              final String _tmpKey_1;
              _tmpKey_1 = _cursor.getString(_cursorIndexOfUuid);
              _tmpCarsCollection = _collectionCars.get(_tmpKey_1);
              _result = new SupplierWithCarsEntity(_tmpSupplier,_tmpCarsCollection);
            } else {
              _result = null;
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getUnsyncedSuppliers(final Continuation<? super List<SupplierEntity>> $completion) {
    final String _sql = "SELECT * FROM suppliers WHERE sync_status != 'SYNCED' AND is_deleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SupplierEntity>>() {
      @Override
      @NonNull
      public List<SupplierEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfCompanyName = CursorUtil.getColumnIndexOrThrow(_cursor, "company_name");
          final int _cursorIndexOfContactPerson = CursorUtil.getColumnIndexOrThrow(_cursor, "contact_person");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfTelegram = CursorUtil.getColumnIndexOrThrow(_cursor, "telegram");
          final int _cursorIndexOfWhatsapp = CursorUtil.getColumnIndexOrThrow(_cursor, "whatsapp");
          final int _cursorIndexOfCountry = CursorUtil.getColumnIndexOrThrow(_cursor, "country");
          final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfServerId = CursorUtil.getColumnIndexOrThrow(_cursor, "server_id");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "sync_status");
          final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
          final List<SupplierEntity> _result = new ArrayList<SupplierEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SupplierEntity _item;
            final String _tmpUuid;
            _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
            final String _tmpCompanyName;
            _tmpCompanyName = _cursor.getString(_cursorIndexOfCompanyName);
            final String _tmpContactPerson;
            if (_cursor.isNull(_cursorIndexOfContactPerson)) {
              _tmpContactPerson = null;
            } else {
              _tmpContactPerson = _cursor.getString(_cursorIndexOfContactPerson);
            }
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            final String _tmpTelegram;
            if (_cursor.isNull(_cursorIndexOfTelegram)) {
              _tmpTelegram = null;
            } else {
              _tmpTelegram = _cursor.getString(_cursorIndexOfTelegram);
            }
            final String _tmpWhatsapp;
            if (_cursor.isNull(_cursorIndexOfWhatsapp)) {
              _tmpWhatsapp = null;
            } else {
              _tmpWhatsapp = _cursor.getString(_cursorIndexOfWhatsapp);
            }
            final String _tmpCountry;
            _tmpCountry = _cursor.getString(_cursorIndexOfCountry);
            final String _tmpCity;
            if (_cursor.isNull(_cursorIndexOfCity)) {
              _tmpCity = null;
            } else {
              _tmpCity = _cursor.getString(_cursorIndexOfCity);
            }
            final String _tmpAddress;
            if (_cursor.isNull(_cursorIndexOfAddress)) {
              _tmpAddress = null;
            } else {
              _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final float _tmpRating;
            _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
            final String _tmpServerId;
            if (_cursor.isNull(_cursorIndexOfServerId)) {
              _tmpServerId = null;
            } else {
              _tmpServerId = _cursor.getString(_cursorIndexOfServerId);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsDeleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp != 0;
            final EntitySyncStatus _tmpSyncStatus;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfSyncStatus);
            _tmpSyncStatus = __converters.toEntitySyncStatus(_tmp_1);
            final long _tmpVersion;
            _tmpVersion = _cursor.getLong(_cursorIndexOfVersion);
            _item = new SupplierEntity(_tmpUuid,_tmpCompanyName,_tmpContactPerson,_tmpPhone,_tmpTelegram,_tmpWhatsapp,_tmpCountry,_tmpCity,_tmpAddress,_tmpNotes,_tmpRating,_tmpServerId,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsDeleted,_tmpSyncStatus,_tmpVersion);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshipexpensesAscomAutocrmDataLocalEntitiesExpenseEntity(
      @NonNull final ArrayMap<String, ArrayList<ExpenseEntity>> _map) {
    final Set<String> __mapKeySet = _map.keySet();
    if (__mapKeySet.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchArrayMap(_map, true, (map) -> {
        __fetchRelationshipexpensesAscomAutocrmDataLocalEntitiesExpenseEntity(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `uuid`,`car_uuid`,`category`,`amount`,`currency`,`description`,`date`,`server_id`,`created_at`,`updated_at`,`is_deleted`,`sync_status`,`version` FROM `expenses` WHERE `car_uuid` IN (");
    final int _inputSize = __mapKeySet.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (String _item : __mapKeySet) {
      _stmt.bindString(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "car_uuid");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfUuid = 0;
      final int _cursorIndexOfCarUuid = 1;
      final int _cursorIndexOfCategory = 2;
      final int _cursorIndexOfAmount = 3;
      final int _cursorIndexOfCurrency = 4;
      final int _cursorIndexOfDescription = 5;
      final int _cursorIndexOfDate = 6;
      final int _cursorIndexOfServerId = 7;
      final int _cursorIndexOfCreatedAt = 8;
      final int _cursorIndexOfUpdatedAt = 9;
      final int _cursorIndexOfIsDeleted = 10;
      final int _cursorIndexOfSyncStatus = 11;
      final int _cursorIndexOfVersion = 12;
      while (_cursor.moveToNext()) {
        final String _tmpKey;
        _tmpKey = _cursor.getString(_itemKeyIndex);
        final ArrayList<ExpenseEntity> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final ExpenseEntity _item_1;
          final String _tmpUuid;
          _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
          final String _tmpCarUuid;
          _tmpCarUuid = _cursor.getString(_cursorIndexOfCarUuid);
          final ExpenseCategory _tmpCategory;
          final String _tmp;
          _tmp = _cursor.getString(_cursorIndexOfCategory);
          _tmpCategory = __converters.toExpenseCategory(_tmp);
          final double _tmpAmount;
          _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
          final String _tmpCurrency;
          _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
          final String _tmpDescription;
          if (_cursor.isNull(_cursorIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
          }
          final long _tmpDate;
          _tmpDate = _cursor.getLong(_cursorIndexOfDate);
          final String _tmpServerId;
          if (_cursor.isNull(_cursorIndexOfServerId)) {
            _tmpServerId = null;
          } else {
            _tmpServerId = _cursor.getString(_cursorIndexOfServerId);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
          final long _tmpUpdatedAt;
          _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
          final boolean _tmpIsDeleted;
          final int _tmp_1;
          _tmp_1 = _cursor.getInt(_cursorIndexOfIsDeleted);
          _tmpIsDeleted = _tmp_1 != 0;
          final EntitySyncStatus _tmpSyncStatus;
          final String _tmp_2;
          _tmp_2 = _cursor.getString(_cursorIndexOfSyncStatus);
          _tmpSyncStatus = __converters.toEntitySyncStatus(_tmp_2);
          final long _tmpVersion;
          _tmpVersion = _cursor.getLong(_cursorIndexOfVersion);
          _item_1 = new ExpenseEntity(_tmpUuid,_tmpCarUuid,_tmpCategory,_tmpAmount,_tmpCurrency,_tmpDescription,_tmpDate,_tmpServerId,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsDeleted,_tmpSyncStatus,_tmpVersion);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private void __fetchRelationshipcarPhotosAscomAutocrmDataLocalEntitiesCarPhotoEntity(
      @NonNull final ArrayMap<String, ArrayList<CarPhotoEntity>> _map) {
    final Set<String> __mapKeySet = _map.keySet();
    if (__mapKeySet.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchArrayMap(_map, true, (map) -> {
        __fetchRelationshipcarPhotosAscomAutocrmDataLocalEntitiesCarPhotoEntity(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `uuid`,`car_uuid`,`file_path`,`remote_url`,`sort_order`,`is_primary`,`server_id`,`created_at`,`updated_at`,`is_deleted`,`sync_status`,`version` FROM `car_photos` WHERE `car_uuid` IN (");
    final int _inputSize = __mapKeySet.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (String _item : __mapKeySet) {
      _stmt.bindString(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "car_uuid");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfUuid = 0;
      final int _cursorIndexOfCarUuid = 1;
      final int _cursorIndexOfFilePath = 2;
      final int _cursorIndexOfRemoteUrl = 3;
      final int _cursorIndexOfSortOrder = 4;
      final int _cursorIndexOfIsPrimary = 5;
      final int _cursorIndexOfServerId = 6;
      final int _cursorIndexOfCreatedAt = 7;
      final int _cursorIndexOfUpdatedAt = 8;
      final int _cursorIndexOfIsDeleted = 9;
      final int _cursorIndexOfSyncStatus = 10;
      final int _cursorIndexOfVersion = 11;
      while (_cursor.moveToNext()) {
        final String _tmpKey;
        _tmpKey = _cursor.getString(_itemKeyIndex);
        final ArrayList<CarPhotoEntity> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final CarPhotoEntity _item_1;
          final String _tmpUuid;
          _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
          final String _tmpCarUuid;
          _tmpCarUuid = _cursor.getString(_cursorIndexOfCarUuid);
          final String _tmpFilePath;
          _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
          final String _tmpRemoteUrl;
          if (_cursor.isNull(_cursorIndexOfRemoteUrl)) {
            _tmpRemoteUrl = null;
          } else {
            _tmpRemoteUrl = _cursor.getString(_cursorIndexOfRemoteUrl);
          }
          final int _tmpSortOrder;
          _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
          final boolean _tmpIsPrimary;
          final int _tmp;
          _tmp = _cursor.getInt(_cursorIndexOfIsPrimary);
          _tmpIsPrimary = _tmp != 0;
          final String _tmpServerId;
          if (_cursor.isNull(_cursorIndexOfServerId)) {
            _tmpServerId = null;
          } else {
            _tmpServerId = _cursor.getString(_cursorIndexOfServerId);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
          final long _tmpUpdatedAt;
          _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
          final boolean _tmpIsDeleted;
          final int _tmp_1;
          _tmp_1 = _cursor.getInt(_cursorIndexOfIsDeleted);
          _tmpIsDeleted = _tmp_1 != 0;
          final EntitySyncStatus _tmpSyncStatus;
          final String _tmp_2;
          _tmp_2 = _cursor.getString(_cursorIndexOfSyncStatus);
          _tmpSyncStatus = __converters.toEntitySyncStatus(_tmp_2);
          final long _tmpVersion;
          _tmpVersion = _cursor.getLong(_cursorIndexOfVersion);
          _item_1 = new CarPhotoEntity(_tmpUuid,_tmpCarUuid,_tmpFilePath,_tmpRemoteUrl,_tmpSortOrder,_tmpIsPrimary,_tmpServerId,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsDeleted,_tmpSyncStatus,_tmpVersion);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private void __fetchRelationshipdeliveryPhotosAscomAutocrmDataLocalEntitiesDeliveryPhotoEntity(
      @NonNull final ArrayMap<String, ArrayList<DeliveryPhotoEntity>> _map) {
    final Set<String> __mapKeySet = _map.keySet();
    if (__mapKeySet.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchArrayMap(_map, true, (map) -> {
        __fetchRelationshipdeliveryPhotosAscomAutocrmDataLocalEntitiesDeliveryPhotoEntity(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `uuid`,`event_uuid`,`file_path`,`remote_url`,`server_id`,`created_at`,`updated_at`,`is_deleted`,`sync_status`,`version` FROM `delivery_photos` WHERE `event_uuid` IN (");
    final int _inputSize = __mapKeySet.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (String _item : __mapKeySet) {
      _stmt.bindString(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "event_uuid");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfUuid = 0;
      final int _cursorIndexOfEventUuid = 1;
      final int _cursorIndexOfFilePath = 2;
      final int _cursorIndexOfRemoteUrl = 3;
      final int _cursorIndexOfServerId = 4;
      final int _cursorIndexOfCreatedAt = 5;
      final int _cursorIndexOfUpdatedAt = 6;
      final int _cursorIndexOfIsDeleted = 7;
      final int _cursorIndexOfSyncStatus = 8;
      final int _cursorIndexOfVersion = 9;
      while (_cursor.moveToNext()) {
        final String _tmpKey;
        _tmpKey = _cursor.getString(_itemKeyIndex);
        final ArrayList<DeliveryPhotoEntity> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final DeliveryPhotoEntity _item_1;
          final String _tmpUuid;
          _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
          final String _tmpEventUuid;
          _tmpEventUuid = _cursor.getString(_cursorIndexOfEventUuid);
          final String _tmpFilePath;
          _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
          final String _tmpRemoteUrl;
          if (_cursor.isNull(_cursorIndexOfRemoteUrl)) {
            _tmpRemoteUrl = null;
          } else {
            _tmpRemoteUrl = _cursor.getString(_cursorIndexOfRemoteUrl);
          }
          final String _tmpServerId;
          if (_cursor.isNull(_cursorIndexOfServerId)) {
            _tmpServerId = null;
          } else {
            _tmpServerId = _cursor.getString(_cursorIndexOfServerId);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
          final long _tmpUpdatedAt;
          _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
          final boolean _tmpIsDeleted;
          final int _tmp;
          _tmp = _cursor.getInt(_cursorIndexOfIsDeleted);
          _tmpIsDeleted = _tmp != 0;
          final EntitySyncStatus _tmpSyncStatus;
          final String _tmp_1;
          _tmp_1 = _cursor.getString(_cursorIndexOfSyncStatus);
          _tmpSyncStatus = __converters.toEntitySyncStatus(_tmp_1);
          final long _tmpVersion;
          _tmpVersion = _cursor.getLong(_cursorIndexOfVersion);
          _item_1 = new DeliveryPhotoEntity(_tmpUuid,_tmpEventUuid,_tmpFilePath,_tmpRemoteUrl,_tmpServerId,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsDeleted,_tmpSyncStatus,_tmpVersion);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private void __fetchRelationshipdeliveryEventsAscomAutocrmDataLocalEntitiesDeliveryEventWithPhotosEntity(
      @NonNull final ArrayMap<String, ArrayList<DeliveryEventWithPhotosEntity>> _map) {
    final Set<String> __mapKeySet = _map.keySet();
    if (__mapKeySet.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchArrayMap(_map, true, (map) -> {
        __fetchRelationshipdeliveryEventsAscomAutocrmDataLocalEntitiesDeliveryEventWithPhotosEntity(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `uuid`,`car_uuid`,`status`,`date`,`location`,`port`,`carrier_name`,`container_number`,`notes`,`est_delivery_date`,`act_delivery_date`,`server_id`,`created_at`,`updated_at`,`is_deleted`,`sync_status`,`version` FROM `delivery_events` WHERE `car_uuid` IN (");
    final int _inputSize = __mapKeySet.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (String _item : __mapKeySet) {
      _stmt.bindString(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, true, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "car_uuid");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfUuid = 0;
      final int _cursorIndexOfCarUuid = 1;
      final int _cursorIndexOfStatus = 2;
      final int _cursorIndexOfDate = 3;
      final int _cursorIndexOfLocation = 4;
      final int _cursorIndexOfPort = 5;
      final int _cursorIndexOfCarrierName = 6;
      final int _cursorIndexOfContainerNumber = 7;
      final int _cursorIndexOfNotes = 8;
      final int _cursorIndexOfEstimatedDeliveryDate = 9;
      final int _cursorIndexOfActualDeliveryDate = 10;
      final int _cursorIndexOfServerId = 11;
      final int _cursorIndexOfCreatedAt = 12;
      final int _cursorIndexOfUpdatedAt = 13;
      final int _cursorIndexOfIsDeleted = 14;
      final int _cursorIndexOfSyncStatus = 15;
      final int _cursorIndexOfVersion = 16;
      final ArrayMap<String, ArrayList<DeliveryPhotoEntity>> _collectionPhotos = new ArrayMap<String, ArrayList<DeliveryPhotoEntity>>();
      while (_cursor.moveToNext()) {
        final String _tmpKey;
        _tmpKey = _cursor.getString(_cursorIndexOfUuid);
        if (!_collectionPhotos.containsKey(_tmpKey)) {
          _collectionPhotos.put(_tmpKey, new ArrayList<DeliveryPhotoEntity>());
        }
      }
      _cursor.moveToPosition(-1);
      __fetchRelationshipdeliveryPhotosAscomAutocrmDataLocalEntitiesDeliveryPhotoEntity(_collectionPhotos);
      while (_cursor.moveToNext()) {
        final String _tmpKey_1;
        _tmpKey_1 = _cursor.getString(_itemKeyIndex);
        final ArrayList<DeliveryEventWithPhotosEntity> _tmpRelation = _map.get(_tmpKey_1);
        if (_tmpRelation != null) {
          final DeliveryEventWithPhotosEntity _item_1;
          final DeliveryEventEntity _tmpEvent;
          final String _tmpUuid;
          _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
          final String _tmpCarUuid;
          _tmpCarUuid = _cursor.getString(_cursorIndexOfCarUuid);
          final DeliveryStatus _tmpStatus;
          final String _tmp;
          _tmp = _cursor.getString(_cursorIndexOfStatus);
          _tmpStatus = __converters.toDeliveryStatus(_tmp);
          final long _tmpDate;
          _tmpDate = _cursor.getLong(_cursorIndexOfDate);
          final String _tmpLocation;
          if (_cursor.isNull(_cursorIndexOfLocation)) {
            _tmpLocation = null;
          } else {
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
          }
          final String _tmpPort;
          if (_cursor.isNull(_cursorIndexOfPort)) {
            _tmpPort = null;
          } else {
            _tmpPort = _cursor.getString(_cursorIndexOfPort);
          }
          final String _tmpCarrierName;
          if (_cursor.isNull(_cursorIndexOfCarrierName)) {
            _tmpCarrierName = null;
          } else {
            _tmpCarrierName = _cursor.getString(_cursorIndexOfCarrierName);
          }
          final String _tmpContainerNumber;
          if (_cursor.isNull(_cursorIndexOfContainerNumber)) {
            _tmpContainerNumber = null;
          } else {
            _tmpContainerNumber = _cursor.getString(_cursorIndexOfContainerNumber);
          }
          final String _tmpNotes;
          if (_cursor.isNull(_cursorIndexOfNotes)) {
            _tmpNotes = null;
          } else {
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
          }
          final Long _tmpEstimatedDeliveryDate;
          if (_cursor.isNull(_cursorIndexOfEstimatedDeliveryDate)) {
            _tmpEstimatedDeliveryDate = null;
          } else {
            _tmpEstimatedDeliveryDate = _cursor.getLong(_cursorIndexOfEstimatedDeliveryDate);
          }
          final Long _tmpActualDeliveryDate;
          if (_cursor.isNull(_cursorIndexOfActualDeliveryDate)) {
            _tmpActualDeliveryDate = null;
          } else {
            _tmpActualDeliveryDate = _cursor.getLong(_cursorIndexOfActualDeliveryDate);
          }
          final String _tmpServerId;
          if (_cursor.isNull(_cursorIndexOfServerId)) {
            _tmpServerId = null;
          } else {
            _tmpServerId = _cursor.getString(_cursorIndexOfServerId);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
          final long _tmpUpdatedAt;
          _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
          final boolean _tmpIsDeleted;
          final int _tmp_1;
          _tmp_1 = _cursor.getInt(_cursorIndexOfIsDeleted);
          _tmpIsDeleted = _tmp_1 != 0;
          final EntitySyncStatus _tmpSyncStatus;
          final String _tmp_2;
          _tmp_2 = _cursor.getString(_cursorIndexOfSyncStatus);
          _tmpSyncStatus = __converters.toEntitySyncStatus(_tmp_2);
          final long _tmpVersion;
          _tmpVersion = _cursor.getLong(_cursorIndexOfVersion);
          _tmpEvent = new DeliveryEventEntity(_tmpUuid,_tmpCarUuid,_tmpStatus,_tmpDate,_tmpLocation,_tmpPort,_tmpCarrierName,_tmpContainerNumber,_tmpNotes,_tmpEstimatedDeliveryDate,_tmpActualDeliveryDate,_tmpServerId,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsDeleted,_tmpSyncStatus,_tmpVersion);
          final ArrayList<DeliveryPhotoEntity> _tmpPhotosCollection;
          final String _tmpKey_2;
          _tmpKey_2 = _cursor.getString(_cursorIndexOfUuid);
          _tmpPhotosCollection = _collectionPhotos.get(_tmpKey_2);
          _item_1 = new DeliveryEventWithPhotosEntity(_tmpEvent,_tmpPhotosCollection);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private void __fetchRelationshipsuppliersAscomAutocrmDataLocalEntitiesSupplierEntity(
      @NonNull final ArrayMap<String, SupplierEntity> _map) {
    final Set<String> __mapKeySet = _map.keySet();
    if (__mapKeySet.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchArrayMap(_map, false, (map) -> {
        __fetchRelationshipsuppliersAscomAutocrmDataLocalEntitiesSupplierEntity(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `uuid`,`company_name`,`contact_person`,`phone`,`telegram`,`whatsapp`,`country`,`city`,`address`,`notes`,`rating`,`server_id`,`created_at`,`updated_at`,`is_deleted`,`sync_status`,`version` FROM `suppliers` WHERE `uuid` IN (");
    final int _inputSize = __mapKeySet.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (String _item : __mapKeySet) {
      _stmt.bindString(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "uuid");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfUuid = 0;
      final int _cursorIndexOfCompanyName = 1;
      final int _cursorIndexOfContactPerson = 2;
      final int _cursorIndexOfPhone = 3;
      final int _cursorIndexOfTelegram = 4;
      final int _cursorIndexOfWhatsapp = 5;
      final int _cursorIndexOfCountry = 6;
      final int _cursorIndexOfCity = 7;
      final int _cursorIndexOfAddress = 8;
      final int _cursorIndexOfNotes = 9;
      final int _cursorIndexOfRating = 10;
      final int _cursorIndexOfServerId = 11;
      final int _cursorIndexOfCreatedAt = 12;
      final int _cursorIndexOfUpdatedAt = 13;
      final int _cursorIndexOfIsDeleted = 14;
      final int _cursorIndexOfSyncStatus = 15;
      final int _cursorIndexOfVersion = 16;
      while (_cursor.moveToNext()) {
        final String _tmpKey;
        _tmpKey = _cursor.getString(_itemKeyIndex);
        if (_map.containsKey(_tmpKey)) {
          final SupplierEntity _item_1;
          final String _tmpUuid;
          _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
          final String _tmpCompanyName;
          _tmpCompanyName = _cursor.getString(_cursorIndexOfCompanyName);
          final String _tmpContactPerson;
          if (_cursor.isNull(_cursorIndexOfContactPerson)) {
            _tmpContactPerson = null;
          } else {
            _tmpContactPerson = _cursor.getString(_cursorIndexOfContactPerson);
          }
          final String _tmpPhone;
          if (_cursor.isNull(_cursorIndexOfPhone)) {
            _tmpPhone = null;
          } else {
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
          }
          final String _tmpTelegram;
          if (_cursor.isNull(_cursorIndexOfTelegram)) {
            _tmpTelegram = null;
          } else {
            _tmpTelegram = _cursor.getString(_cursorIndexOfTelegram);
          }
          final String _tmpWhatsapp;
          if (_cursor.isNull(_cursorIndexOfWhatsapp)) {
            _tmpWhatsapp = null;
          } else {
            _tmpWhatsapp = _cursor.getString(_cursorIndexOfWhatsapp);
          }
          final String _tmpCountry;
          _tmpCountry = _cursor.getString(_cursorIndexOfCountry);
          final String _tmpCity;
          if (_cursor.isNull(_cursorIndexOfCity)) {
            _tmpCity = null;
          } else {
            _tmpCity = _cursor.getString(_cursorIndexOfCity);
          }
          final String _tmpAddress;
          if (_cursor.isNull(_cursorIndexOfAddress)) {
            _tmpAddress = null;
          } else {
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
          }
          final String _tmpNotes;
          if (_cursor.isNull(_cursorIndexOfNotes)) {
            _tmpNotes = null;
          } else {
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
          }
          final float _tmpRating;
          _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
          final String _tmpServerId;
          if (_cursor.isNull(_cursorIndexOfServerId)) {
            _tmpServerId = null;
          } else {
            _tmpServerId = _cursor.getString(_cursorIndexOfServerId);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
          final long _tmpUpdatedAt;
          _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
          final boolean _tmpIsDeleted;
          final int _tmp;
          _tmp = _cursor.getInt(_cursorIndexOfIsDeleted);
          _tmpIsDeleted = _tmp != 0;
          final EntitySyncStatus _tmpSyncStatus;
          final String _tmp_1;
          _tmp_1 = _cursor.getString(_cursorIndexOfSyncStatus);
          _tmpSyncStatus = __converters.toEntitySyncStatus(_tmp_1);
          final long _tmpVersion;
          _tmpVersion = _cursor.getLong(_cursorIndexOfVersion);
          _item_1 = new SupplierEntity(_tmpUuid,_tmpCompanyName,_tmpContactPerson,_tmpPhone,_tmpTelegram,_tmpWhatsapp,_tmpCountry,_tmpCity,_tmpAddress,_tmpNotes,_tmpRating,_tmpServerId,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsDeleted,_tmpSyncStatus,_tmpVersion);
          _map.put(_tmpKey, _item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private void __fetchRelationshipclientsAscomAutocrmDataLocalEntitiesClientEntity(
      @NonNull final ArrayMap<String, ClientEntity> _map) {
    final Set<String> __mapKeySet = _map.keySet();
    if (__mapKeySet.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchArrayMap(_map, false, (map) -> {
        __fetchRelationshipclientsAscomAutocrmDataLocalEntitiesClientEntity(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `uuid`,`full_name`,`phone`,`telegram`,`whatsapp`,`city`,`preferred_brands`,`notes`,`server_id`,`created_at`,`updated_at`,`is_deleted`,`sync_status`,`version` FROM `clients` WHERE `uuid` IN (");
    final int _inputSize = __mapKeySet.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (String _item : __mapKeySet) {
      _stmt.bindString(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "uuid");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfUuid = 0;
      final int _cursorIndexOfFullName = 1;
      final int _cursorIndexOfPhone = 2;
      final int _cursorIndexOfTelegram = 3;
      final int _cursorIndexOfWhatsapp = 4;
      final int _cursorIndexOfCity = 5;
      final int _cursorIndexOfPreferredBrands = 6;
      final int _cursorIndexOfNotes = 7;
      final int _cursorIndexOfServerId = 8;
      final int _cursorIndexOfCreatedAt = 9;
      final int _cursorIndexOfUpdatedAt = 10;
      final int _cursorIndexOfIsDeleted = 11;
      final int _cursorIndexOfSyncStatus = 12;
      final int _cursorIndexOfVersion = 13;
      while (_cursor.moveToNext()) {
        final String _tmpKey;
        _tmpKey = _cursor.getString(_itemKeyIndex);
        if (_map.containsKey(_tmpKey)) {
          final ClientEntity _item_1;
          final String _tmpUuid;
          _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
          final String _tmpFullName;
          _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
          final String _tmpPhone;
          _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
          final String _tmpTelegram;
          if (_cursor.isNull(_cursorIndexOfTelegram)) {
            _tmpTelegram = null;
          } else {
            _tmpTelegram = _cursor.getString(_cursorIndexOfTelegram);
          }
          final String _tmpWhatsapp;
          if (_cursor.isNull(_cursorIndexOfWhatsapp)) {
            _tmpWhatsapp = null;
          } else {
            _tmpWhatsapp = _cursor.getString(_cursorIndexOfWhatsapp);
          }
          final String _tmpCity;
          if (_cursor.isNull(_cursorIndexOfCity)) {
            _tmpCity = null;
          } else {
            _tmpCity = _cursor.getString(_cursorIndexOfCity);
          }
          final String _tmpPreferredBrands;
          if (_cursor.isNull(_cursorIndexOfPreferredBrands)) {
            _tmpPreferredBrands = null;
          } else {
            _tmpPreferredBrands = _cursor.getString(_cursorIndexOfPreferredBrands);
          }
          final String _tmpNotes;
          if (_cursor.isNull(_cursorIndexOfNotes)) {
            _tmpNotes = null;
          } else {
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
          }
          final String _tmpServerId;
          if (_cursor.isNull(_cursorIndexOfServerId)) {
            _tmpServerId = null;
          } else {
            _tmpServerId = _cursor.getString(_cursorIndexOfServerId);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
          final long _tmpUpdatedAt;
          _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
          final boolean _tmpIsDeleted;
          final int _tmp;
          _tmp = _cursor.getInt(_cursorIndexOfIsDeleted);
          _tmpIsDeleted = _tmp != 0;
          final EntitySyncStatus _tmpSyncStatus;
          final String _tmp_1;
          _tmp_1 = _cursor.getString(_cursorIndexOfSyncStatus);
          _tmpSyncStatus = __converters.toEntitySyncStatus(_tmp_1);
          final long _tmpVersion;
          _tmpVersion = _cursor.getLong(_cursorIndexOfVersion);
          _item_1 = new ClientEntity(_tmpUuid,_tmpFullName,_tmpPhone,_tmpTelegram,_tmpWhatsapp,_tmpCity,_tmpPreferredBrands,_tmpNotes,_tmpServerId,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsDeleted,_tmpSyncStatus,_tmpVersion);
          _map.put(_tmpKey, _item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private void __fetchRelationshipcarsAscomAutocrmDataLocalEntitiesCarWithDetailsEntity(
      @NonNull final ArrayMap<String, ArrayList<CarWithDetailsEntity>> _map) {
    final Set<String> __mapKeySet = _map.keySet();
    if (__mapKeySet.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchArrayMap(_map, true, (map) -> {
        __fetchRelationshipcarsAscomAutocrmDataLocalEntitiesCarWithDetailsEntity(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `uuid`,`make`,`model`,`year`,`vin`,`mileage`,`country`,`color`,`engine_vol`,`transmission`,`purchase_price`,`est_sale_price`,`sale_price`,`currency`,`status`,`purchase_date`,`sale_date`,`arrival_date`,`notes`,`supplier_uuid`,`client_uuid`,`delivery_status`,`server_id`,`created_at`,`updated_at`,`is_deleted`,`sync_status`,`version` FROM `cars` WHERE `supplier_uuid` IN (");
    final int _inputSize = __mapKeySet.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (String _item : __mapKeySet) {
      _stmt.bindString(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, true, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "supplier_uuid");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfUuid = 0;
      final int _cursorIndexOfMake = 1;
      final int _cursorIndexOfModel = 2;
      final int _cursorIndexOfYear = 3;
      final int _cursorIndexOfVin = 4;
      final int _cursorIndexOfMileage = 5;
      final int _cursorIndexOfCountryOfOrigin = 6;
      final int _cursorIndexOfColor = 7;
      final int _cursorIndexOfEngineVolume = 8;
      final int _cursorIndexOfTransmission = 9;
      final int _cursorIndexOfPurchasePrice = 10;
      final int _cursorIndexOfEstimatedSalePrice = 11;
      final int _cursorIndexOfSalePrice = 12;
      final int _cursorIndexOfCurrency = 13;
      final int _cursorIndexOfStatus = 14;
      final int _cursorIndexOfPurchaseDate = 15;
      final int _cursorIndexOfSaleDate = 16;
      final int _cursorIndexOfArrivalDate = 17;
      final int _cursorIndexOfNotes = 18;
      final int _cursorIndexOfSupplierUuid = 19;
      final int _cursorIndexOfClientUuid = 20;
      final int _cursorIndexOfDeliveryStatus = 21;
      final int _cursorIndexOfServerId = 22;
      final int _cursorIndexOfCreatedAt = 23;
      final int _cursorIndexOfUpdatedAt = 24;
      final int _cursorIndexOfIsDeleted = 25;
      final int _cursorIndexOfSyncStatus = 26;
      final int _cursorIndexOfVersion = 27;
      final ArrayMap<String, ArrayList<ExpenseEntity>> _collectionExpenses = new ArrayMap<String, ArrayList<ExpenseEntity>>();
      final ArrayMap<String, ArrayList<CarPhotoEntity>> _collectionPhotos = new ArrayMap<String, ArrayList<CarPhotoEntity>>();
      final ArrayMap<String, ArrayList<DeliveryEventWithPhotosEntity>> _collectionDeliveryEvents = new ArrayMap<String, ArrayList<DeliveryEventWithPhotosEntity>>();
      final ArrayMap<String, SupplierEntity> _collectionSupplier = new ArrayMap<String, SupplierEntity>();
      final ArrayMap<String, ClientEntity> _collectionClient = new ArrayMap<String, ClientEntity>();
      while (_cursor.moveToNext()) {
        final String _tmpKey;
        _tmpKey = _cursor.getString(_cursorIndexOfUuid);
        if (!_collectionExpenses.containsKey(_tmpKey)) {
          _collectionExpenses.put(_tmpKey, new ArrayList<ExpenseEntity>());
        }
        final String _tmpKey_1;
        _tmpKey_1 = _cursor.getString(_cursorIndexOfUuid);
        if (!_collectionPhotos.containsKey(_tmpKey_1)) {
          _collectionPhotos.put(_tmpKey_1, new ArrayList<CarPhotoEntity>());
        }
        final String _tmpKey_2;
        _tmpKey_2 = _cursor.getString(_cursorIndexOfUuid);
        if (!_collectionDeliveryEvents.containsKey(_tmpKey_2)) {
          _collectionDeliveryEvents.put(_tmpKey_2, new ArrayList<DeliveryEventWithPhotosEntity>());
        }
        final String _tmpKey_3;
        if (_cursor.isNull(_cursorIndexOfSupplierUuid)) {
          _tmpKey_3 = null;
        } else {
          _tmpKey_3 = _cursor.getString(_cursorIndexOfSupplierUuid);
        }
        if (_tmpKey_3 != null) {
          _collectionSupplier.put(_tmpKey_3, null);
        }
        final String _tmpKey_4;
        if (_cursor.isNull(_cursorIndexOfClientUuid)) {
          _tmpKey_4 = null;
        } else {
          _tmpKey_4 = _cursor.getString(_cursorIndexOfClientUuid);
        }
        if (_tmpKey_4 != null) {
          _collectionClient.put(_tmpKey_4, null);
        }
      }
      _cursor.moveToPosition(-1);
      __fetchRelationshipexpensesAscomAutocrmDataLocalEntitiesExpenseEntity(_collectionExpenses);
      __fetchRelationshipcarPhotosAscomAutocrmDataLocalEntitiesCarPhotoEntity(_collectionPhotos);
      __fetchRelationshipdeliveryEventsAscomAutocrmDataLocalEntitiesDeliveryEventWithPhotosEntity(_collectionDeliveryEvents);
      __fetchRelationshipsuppliersAscomAutocrmDataLocalEntitiesSupplierEntity(_collectionSupplier);
      __fetchRelationshipclientsAscomAutocrmDataLocalEntitiesClientEntity(_collectionClient);
      while (_cursor.moveToNext()) {
        final String _tmpKey_5;
        if (_cursor.isNull(_itemKeyIndex)) {
          _tmpKey_5 = null;
        } else {
          _tmpKey_5 = _cursor.getString(_itemKeyIndex);
        }
        if (_tmpKey_5 != null) {
          final ArrayList<CarWithDetailsEntity> _tmpRelation = _map.get(_tmpKey_5);
          if (_tmpRelation != null) {
            final CarWithDetailsEntity _item_1;
            final CarEntity _tmpCar;
            final String _tmpUuid;
            _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
            final String _tmpMake;
            _tmpMake = _cursor.getString(_cursorIndexOfMake);
            final String _tmpModel;
            _tmpModel = _cursor.getString(_cursorIndexOfModel);
            final int _tmpYear;
            _tmpYear = _cursor.getInt(_cursorIndexOfYear);
            final String _tmpVin;
            if (_cursor.isNull(_cursorIndexOfVin)) {
              _tmpVin = null;
            } else {
              _tmpVin = _cursor.getString(_cursorIndexOfVin);
            }
            final Integer _tmpMileage;
            if (_cursor.isNull(_cursorIndexOfMileage)) {
              _tmpMileage = null;
            } else {
              _tmpMileage = _cursor.getInt(_cursorIndexOfMileage);
            }
            final String _tmpCountryOfOrigin;
            _tmpCountryOfOrigin = _cursor.getString(_cursorIndexOfCountryOfOrigin);
            final String _tmpColor;
            if (_cursor.isNull(_cursorIndexOfColor)) {
              _tmpColor = null;
            } else {
              _tmpColor = _cursor.getString(_cursorIndexOfColor);
            }
            final Double _tmpEngineVolume;
            if (_cursor.isNull(_cursorIndexOfEngineVolume)) {
              _tmpEngineVolume = null;
            } else {
              _tmpEngineVolume = _cursor.getDouble(_cursorIndexOfEngineVolume);
            }
            final String _tmpTransmission;
            if (_cursor.isNull(_cursorIndexOfTransmission)) {
              _tmpTransmission = null;
            } else {
              _tmpTransmission = _cursor.getString(_cursorIndexOfTransmission);
            }
            final double _tmpPurchasePrice;
            _tmpPurchasePrice = _cursor.getDouble(_cursorIndexOfPurchasePrice);
            final Double _tmpEstimatedSalePrice;
            if (_cursor.isNull(_cursorIndexOfEstimatedSalePrice)) {
              _tmpEstimatedSalePrice = null;
            } else {
              _tmpEstimatedSalePrice = _cursor.getDouble(_cursorIndexOfEstimatedSalePrice);
            }
            final Double _tmpSalePrice;
            if (_cursor.isNull(_cursorIndexOfSalePrice)) {
              _tmpSalePrice = null;
            } else {
              _tmpSalePrice = _cursor.getDouble(_cursorIndexOfSalePrice);
            }
            final String _tmpCurrency;
            _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
            final CarStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toCarStatus(_tmp);
            final long _tmpPurchaseDate;
            _tmpPurchaseDate = _cursor.getLong(_cursorIndexOfPurchaseDate);
            final Long _tmpSaleDate;
            if (_cursor.isNull(_cursorIndexOfSaleDate)) {
              _tmpSaleDate = null;
            } else {
              _tmpSaleDate = _cursor.getLong(_cursorIndexOfSaleDate);
            }
            final Long _tmpArrivalDate;
            if (_cursor.isNull(_cursorIndexOfArrivalDate)) {
              _tmpArrivalDate = null;
            } else {
              _tmpArrivalDate = _cursor.getLong(_cursorIndexOfArrivalDate);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpSupplierUuid;
            if (_cursor.isNull(_cursorIndexOfSupplierUuid)) {
              _tmpSupplierUuid = null;
            } else {
              _tmpSupplierUuid = _cursor.getString(_cursorIndexOfSupplierUuid);
            }
            final String _tmpClientUuid;
            if (_cursor.isNull(_cursorIndexOfClientUuid)) {
              _tmpClientUuid = null;
            } else {
              _tmpClientUuid = _cursor.getString(_cursorIndexOfClientUuid);
            }
            final DeliveryStatus _tmpDeliveryStatus;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfDeliveryStatus);
            _tmpDeliveryStatus = __converters.toDeliveryStatus(_tmp_1);
            final String _tmpServerId;
            if (_cursor.isNull(_cursorIndexOfServerId)) {
              _tmpServerId = null;
            } else {
              _tmpServerId = _cursor.getString(_cursorIndexOfServerId);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsDeleted;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_2 != 0;
            final EntitySyncStatus _tmpSyncStatus;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfSyncStatus);
            _tmpSyncStatus = __converters.toEntitySyncStatus(_tmp_3);
            final long _tmpVersion;
            _tmpVersion = _cursor.getLong(_cursorIndexOfVersion);
            _tmpCar = new CarEntity(_tmpUuid,_tmpMake,_tmpModel,_tmpYear,_tmpVin,_tmpMileage,_tmpCountryOfOrigin,_tmpColor,_tmpEngineVolume,_tmpTransmission,_tmpPurchasePrice,_tmpEstimatedSalePrice,_tmpSalePrice,_tmpCurrency,_tmpStatus,_tmpPurchaseDate,_tmpSaleDate,_tmpArrivalDate,_tmpNotes,_tmpSupplierUuid,_tmpClientUuid,_tmpDeliveryStatus,_tmpServerId,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsDeleted,_tmpSyncStatus,_tmpVersion);
            final ArrayList<ExpenseEntity> _tmpExpensesCollection;
            final String _tmpKey_6;
            _tmpKey_6 = _cursor.getString(_cursorIndexOfUuid);
            _tmpExpensesCollection = _collectionExpenses.get(_tmpKey_6);
            final ArrayList<CarPhotoEntity> _tmpPhotosCollection;
            final String _tmpKey_7;
            _tmpKey_7 = _cursor.getString(_cursorIndexOfUuid);
            _tmpPhotosCollection = _collectionPhotos.get(_tmpKey_7);
            final ArrayList<DeliveryEventWithPhotosEntity> _tmpDeliveryEventsCollection;
            final String _tmpKey_8;
            _tmpKey_8 = _cursor.getString(_cursorIndexOfUuid);
            _tmpDeliveryEventsCollection = _collectionDeliveryEvents.get(_tmpKey_8);
            final SupplierEntity _tmpSupplier;
            final String _tmpKey_9;
            if (_cursor.isNull(_cursorIndexOfSupplierUuid)) {
              _tmpKey_9 = null;
            } else {
              _tmpKey_9 = _cursor.getString(_cursorIndexOfSupplierUuid);
            }
            if (_tmpKey_9 != null) {
              _tmpSupplier = _collectionSupplier.get(_tmpKey_9);
            } else {
              _tmpSupplier = null;
            }
            final ClientEntity _tmpClient;
            final String _tmpKey_10;
            if (_cursor.isNull(_cursorIndexOfClientUuid)) {
              _tmpKey_10 = null;
            } else {
              _tmpKey_10 = _cursor.getString(_cursorIndexOfClientUuid);
            }
            if (_tmpKey_10 != null) {
              _tmpClient = _collectionClient.get(_tmpKey_10);
            } else {
              _tmpClient = null;
            }
            _item_1 = new CarWithDetailsEntity(_tmpCar,_tmpExpensesCollection,_tmpPhotosCollection,_tmpDeliveryEventsCollection,_tmpSupplier,_tmpClient);
            _tmpRelation.add(_item_1);
          }
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
