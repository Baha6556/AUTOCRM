package com.autocrm.data.repository;

import com.autocrm.data.local.AppDatabase;
import com.autocrm.data.local.dao.AuditLogDao;
import com.autocrm.data.local.dao.SupplierDao;
import com.autocrm.data.local.dao.SyncQueueDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class SupplierRepositoryImpl_Factory implements Factory<SupplierRepositoryImpl> {
  private final Provider<AppDatabase> dbProvider;

  private final Provider<SupplierDao> supplierDaoProvider;

  private final Provider<SyncQueueDao> syncQueueDaoProvider;

  private final Provider<AuditLogDao> auditLogDaoProvider;

  public SupplierRepositoryImpl_Factory(Provider<AppDatabase> dbProvider,
      Provider<SupplierDao> supplierDaoProvider, Provider<SyncQueueDao> syncQueueDaoProvider,
      Provider<AuditLogDao> auditLogDaoProvider) {
    this.dbProvider = dbProvider;
    this.supplierDaoProvider = supplierDaoProvider;
    this.syncQueueDaoProvider = syncQueueDaoProvider;
    this.auditLogDaoProvider = auditLogDaoProvider;
  }

  @Override
  public SupplierRepositoryImpl get() {
    return newInstance(dbProvider.get(), supplierDaoProvider.get(), syncQueueDaoProvider.get(), auditLogDaoProvider.get());
  }

  public static SupplierRepositoryImpl_Factory create(Provider<AppDatabase> dbProvider,
      Provider<SupplierDao> supplierDaoProvider, Provider<SyncQueueDao> syncQueueDaoProvider,
      Provider<AuditLogDao> auditLogDaoProvider) {
    return new SupplierRepositoryImpl_Factory(dbProvider, supplierDaoProvider, syncQueueDaoProvider, auditLogDaoProvider);
  }

  public static SupplierRepositoryImpl newInstance(AppDatabase db, SupplierDao supplierDao,
      SyncQueueDao syncQueueDao, AuditLogDao auditLogDao) {
    return new SupplierRepositoryImpl(db, supplierDao, syncQueueDao, auditLogDao);
  }
}
