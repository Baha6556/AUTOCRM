package com.autocrm.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.autocrm.data.local.entities.AuditLogEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AuditLogDao_Impl implements AuditLogDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AuditLogEntity> __insertionAdapterOfAuditLogEntity;

  public AuditLogDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAuditLogEntity = new EntityInsertionAdapter<AuditLogEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `audit_logs` (`uuid`,`entity_type`,`entity_id`,`action_type`,`changed_fields`,`old_value`,`new_value`,`timestamp`,`user_id`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AuditLogEntity entity) {
        statement.bindString(1, entity.getUuid());
        statement.bindString(2, entity.getEntityType());
        statement.bindString(3, entity.getEntityId());
        statement.bindString(4, entity.getActionType());
        if (entity.getChangedFields() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getChangedFields());
        }
        if (entity.getOldValue() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getOldValue());
        }
        if (entity.getNewValue() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getNewValue());
        }
        statement.bindLong(8, entity.getTimestamp());
        if (entity.getUserId() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getUserId());
        }
      }
    };
  }

  @Override
  public Object insert(final AuditLogEntity auditLog,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAuditLogEntity.insert(auditLog);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<AuditLogEntity> auditLogs,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAuditLogEntity.insert(auditLogs);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<AuditLogEntity>> getRecentLogs(final int limit) {
    final String _sql = "SELECT * FROM audit_logs ORDER BY timestamp DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"audit_logs"}, new Callable<List<AuditLogEntity>>() {
      @Override
      @NonNull
      public List<AuditLogEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUuid = CursorUtil.getColumnIndexOrThrow(_cursor, "uuid");
          final int _cursorIndexOfEntityType = CursorUtil.getColumnIndexOrThrow(_cursor, "entity_type");
          final int _cursorIndexOfEntityId = CursorUtil.getColumnIndexOrThrow(_cursor, "entity_id");
          final int _cursorIndexOfActionType = CursorUtil.getColumnIndexOrThrow(_cursor, "action_type");
          final int _cursorIndexOfChangedFields = CursorUtil.getColumnIndexOrThrow(_cursor, "changed_fields");
          final int _cursorIndexOfOldValue = CursorUtil.getColumnIndexOrThrow(_cursor, "old_value");
          final int _cursorIndexOfNewValue = CursorUtil.getColumnIndexOrThrow(_cursor, "new_value");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "user_id");
          final List<AuditLogEntity> _result = new ArrayList<AuditLogEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AuditLogEntity _item;
            final String _tmpUuid;
            _tmpUuid = _cursor.getString(_cursorIndexOfUuid);
            final String _tmpEntityType;
            _tmpEntityType = _cursor.getString(_cursorIndexOfEntityType);
            final String _tmpEntityId;
            _tmpEntityId = _cursor.getString(_cursorIndexOfEntityId);
            final String _tmpActionType;
            _tmpActionType = _cursor.getString(_cursorIndexOfActionType);
            final String _tmpChangedFields;
            if (_cursor.isNull(_cursorIndexOfChangedFields)) {
              _tmpChangedFields = null;
            } else {
              _tmpChangedFields = _cursor.getString(_cursorIndexOfChangedFields);
            }
            final String _tmpOldValue;
            if (_cursor.isNull(_cursorIndexOfOldValue)) {
              _tmpOldValue = null;
            } else {
              _tmpOldValue = _cursor.getString(_cursorIndexOfOldValue);
            }
            final String _tmpNewValue;
            if (_cursor.isNull(_cursorIndexOfNewValue)) {
              _tmpNewValue = null;
            } else {
              _tmpNewValue = _cursor.getString(_cursorIndexOfNewValue);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            }
            _item = new AuditLogEntity(_tmpUuid,_tmpEntityType,_tmpEntityId,_tmpActionType,_tmpChangedFields,_tmpOldValue,_tmpNewValue,_tmpTimestamp,_tmpUserId);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
