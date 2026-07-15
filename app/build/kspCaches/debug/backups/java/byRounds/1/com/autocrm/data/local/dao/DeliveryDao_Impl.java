package com.autocrm.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
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
import com.autocrm.data.local.entities.DeliveryEventEntity;
import com.autocrm.data.local.entities.DeliveryEventWithPhotosEntity;
import com.autocrm.data.local.entities.DeliveryPhotoEntity;
import com.autocrm.domain.model.DeliveryStatus;
import com.autocrm.domain.model.EntitySyncStatus;
import java.lang.Class;
import java.lang.Exception;
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
public final class DeliveryDao_Impl implements DeliveryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DeliveryEventEntity> __insertionAdapterOfDeliveryEventEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<DeliveryEventEntity> __updateAdapterOfDeliveryEventEntity;

  private final SharedSQLiteStatement __preparedStmtOfSoftDelete;

  private final SharedSQLiteStatement __preparedStmtOfMarkSynced;

  private final SharedSQLiteStatement __preparedStmtOfMarkFailed;

  public DeliveryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDeliveryEventEntity = new EntityInsertionAdapter<DeliveryEventEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `delivery_events` (`uuid`,`car_uuid`,`status`,`date`,`location`,`port`,`carrier_name`,`container_number`,`notes`,`est_delivery_date`,`act_delivery_date`,`server_id`,`created_at`,`updated_at`,`is_deleted`,`sync_status`,`version`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DeliveryEventEntity entity) {
        statement.bindString(1, entity.getUuid());
        statement.bindString(2, entity.getCarUuid());
        final String _tmp = __converters.fromDeliveryStatus(entity.getStatus());
        statement.bindString(3, _tmp);
        statement.bindLong(4, entity.getDate());
        if (entity.getLocation() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getLocation());
        }
        if (entity.getPort() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPort());
        }
        if (entity.getCarrierName() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getCarrierName());
        }
        if (entity.getContainerNumber() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getContainerNumber());
        }
        if (entity.getNotes() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getNotes());
        }
        if (entity.getEstimatedDeliveryDate() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getEstimatedDeliveryDate());
        }
        if (entity.getActualDeliveryDate() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getActualDeliveryDate());
        }
        if (entity.getServerId() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getServerId());
        }
        statement.bindLong(13, entity.getCreatedAt());
        statement.bindLong(14, entity.getUpdatedAt());
        final int _tmp_1 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(15, _tmp_1);
        final String _tmp_2 = __converters.fromEntitySyncStatus(entity.getSyncStatus());
        statement.bindString(16, _tmp_2);
        statement.bindLong(17, entity.getVersion());
      }
    };
    this.__updateAdapterOfDeliveryEventEntity = new EntityDeletionOrUpdateAdapter<DeliveryEventEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `delivery_events` SET `uuid` = ?,`car_uuid` = ?,`status` = ?,`date` = ?,`location` = ?,`port` = ?,`carrier_name` = ?,`container_number` = ?,`notes` = ?,`est_delivery_date` = ?,`act_delivery_date` = ?,`server_id` = ?,`created_at` = ?,`updated_at` = ?,`is_deleted` = ?,`sync_status` = ?,`version` = ? WHERE `uuid` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DeliveryEventEntity entity) {
        statement.bindString(1, entity.getUuid());
        statement.bindString(2, entity.getCarUuid());
        final String _tmp = __converters.fromDeliveryStatus(entity.getStatus());
        statement.bindString(3, _tmp);
        statement.bindLong(4, entity.getDate());
        if (entity.getLocation() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getLocation());
        }
        if (entity.getPort() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPort());
        }
        if (entity.getCarrierName() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getCarrierName());
        }
        if (entity.getContainerNumber() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getContainerNumber());
        }
        if (entity.getNotes() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getNotes());
        }
        if (entity.getEstimatedDeliveryDate() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getEstimatedDeliveryDate());
        }
        if (entity.getActualDeliveryDate() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getActualDeliveryDate());
        }
        if (entity.getServerId() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getServerId());
        }
        statement.bindLong(13, entity.getCreatedAt());
        statement.bindLong(14, entity.getUpdatedAt());
        final int _tmp_1 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(15, _tmp_1);
        final String _tmp_2 = __converters.fromEntitySyncStatus(entity.getSyncStatus());
        statement.bindString(16, _tmp_2);
        statement.bindLong(17, entity.getVersion());
        statement.bindString(18, entity.getUuid());
      }
    };
    this.__preparedStmtOfSoftDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE delivery_events SET is_deleted = 1, sync_status = 'PENDING', updated_at = ? WHERE uuid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkSynced = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE delivery_events SET server_id = ?, sync_status = 'SYNCED', updated_at = ? WHERE uuid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkFailed = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE delivery_events SET sync_status = 'FAILED' WHERE uuid = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final DeliveryEventEntity event,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDeliveryEventEntity.insert(event);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final DeliveryEventEntity event,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfDeliveryEventEntity.handle(event);
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
  public Flow<List<DeliveryEventWithPhotosEntity>> getEventsForCar(final String carUuid) {
    final String _sql = "SELECT * FROM delivery_events WHERE car_uuid = ? AND is_deleted = 0 ORDER BY date ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, carUuid);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"delivery_photos",
        "delivery_events"}, new Callable<List<DeliveryEventWithPhotosEntity>>() {
      @Override
      @NonNull
      public List<DeliveryEventWithPhotosEntity> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
            final int _cursorIndexOfCarUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "car_uuid");
            final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
            final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
            final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
            final int _cursorIndexOfPort = CursorUtil.getColumnIndexOrThrow(_cursor, "port");
            final int _cursorIndexOfCarrierName = CursorUtil.getColumnIndexOrThrow(_cursor, "carrier_name");
            final int _cursorIndexOfContainerNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "container_number");
            final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
            final int _cursorIndexOfEstimatedDeliveryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "est_delivery_date");
            final int _cursorIndexOfActualDeliveryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "act_delivery_date");
            final int _cursorIndexOfServerId = CursorUtil.getColumnIndexOrThrow(_cursor, "server_id");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
            final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
            final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "sync_status");
            final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
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
            final List<DeliveryEventWithPhotosEntity> _result = new ArrayList<DeliveryEventWithPhotosEntity>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final DeliveryEventWithPhotosEntity _item;
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
              final String _tmpKey_1;
              _tmpKey_1 = _cursor.getString(_cursorIndexOfUuid);
              _tmpPhotosCollection = _collectionPhotos.get(_tmpKey_1);
              _item = new DeliveryEventWithPhotosEntity(_tmpEvent,_tmpPhotosCollection);
              _result.add(_item);
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
  public Object getUnsyncedEvents(
      final Continuation<? super List<DeliveryEventEntity>> $completion) {
    final String _sql = "SELECT * FROM delivery_events WHERE sync_status != 'SYNCED' AND is_deleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<DeliveryEventEntity>>() {
      @Override
      @NonNull
      public List<DeliveryEventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfCarUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "car_uuid");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfPort = CursorUtil.getColumnIndexOrThrow(_cursor, "port");
          final int _cursorIndexOfCarrierName = CursorUtil.getColumnIndexOrThrow(_cursor, "carrier_name");
          final int _cursorIndexOfContainerNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "container_number");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfEstimatedDeliveryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "est_delivery_date");
          final int _cursorIndexOfActualDeliveryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "act_delivery_date");
          final int _cursorIndexOfServerId = CursorUtil.getColumnIndexOrThrow(_cursor, "server_id");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "sync_status");
          final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
          final List<DeliveryEventEntity> _result = new ArrayList<DeliveryEventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DeliveryEventEntity _item;
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
            _item = new DeliveryEventEntity(_tmpUuid,_tmpCarUuid,_tmpStatus,_tmpDate,_tmpLocation,_tmpPort,_tmpCarrierName,_tmpContainerNumber,_tmpNotes,_tmpEstimatedDeliveryDate,_tmpActualDeliveryDate,_tmpServerId,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsDeleted,_tmpSyncStatus,_tmpVersion);
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
}
