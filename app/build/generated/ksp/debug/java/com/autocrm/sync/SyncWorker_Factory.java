package com.autocrm.sync;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.autocrm.data.local.AppDatabase;
import com.autocrm.data.local.dao.AuditLogDao;
import com.autocrm.data.local.dao.CarDao;
import com.autocrm.data.local.dao.SyncQueueDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class SyncWorker_Factory {
  private final Provider<AppDatabase> dbProvider;

  private final Provider<SyncQueueDao> syncQueueDaoProvider;

  private final Provider<AuditLogDao> auditLogDaoProvider;

  private final Provider<CarDao> carDaoProvider;

  private final Provider<ConflictResolver> conflictResolverProvider;

  public SyncWorker_Factory(Provider<AppDatabase> dbProvider,
      Provider<SyncQueueDao> syncQueueDaoProvider, Provider<AuditLogDao> auditLogDaoProvider,
      Provider<CarDao> carDaoProvider, Provider<ConflictResolver> conflictResolverProvider) {
    this.dbProvider = dbProvider;
    this.syncQueueDaoProvider = syncQueueDaoProvider;
    this.auditLogDaoProvider = auditLogDaoProvider;
    this.carDaoProvider = carDaoProvider;
    this.conflictResolverProvider = conflictResolverProvider;
  }

  public SyncWorker get(Context appContext, WorkerParameters workerParams) {
    return newInstance(appContext, workerParams, dbProvider.get(), syncQueueDaoProvider.get(), auditLogDaoProvider.get(), carDaoProvider.get(), conflictResolverProvider.get());
  }

  public static SyncWorker_Factory create(Provider<AppDatabase> dbProvider,
      Provider<SyncQueueDao> syncQueueDaoProvider, Provider<AuditLogDao> auditLogDaoProvider,
      Provider<CarDao> carDaoProvider, Provider<ConflictResolver> conflictResolverProvider) {
    return new SyncWorker_Factory(dbProvider, syncQueueDaoProvider, auditLogDaoProvider, carDaoProvider, conflictResolverProvider);
  }

  public static SyncWorker newInstance(Context appContext, WorkerParameters workerParams,
      AppDatabase db, SyncQueueDao syncQueueDao, AuditLogDao auditLogDao, CarDao carDao,
      ConflictResolver conflictResolver) {
    return new SyncWorker(appContext, workerParams, db, syncQueueDao, auditLogDao, carDao, conflictResolver);
  }
}
