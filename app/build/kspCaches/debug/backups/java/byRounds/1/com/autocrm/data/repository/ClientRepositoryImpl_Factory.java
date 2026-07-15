package com.autocrm.data.repository;

import com.autocrm.data.local.AppDatabase;
import com.autocrm.data.local.dao.AuditLogDao;
import com.autocrm.data.local.dao.ClientDao;
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
public final class ClientRepositoryImpl_Factory implements Factory<ClientRepositoryImpl> {
  private final Provider<AppDatabase> dbProvider;

  private final Provider<ClientDao> clientDaoProvider;

  private final Provider<SyncQueueDao> syncQueueDaoProvider;

  private final Provider<AuditLogDao> auditLogDaoProvider;

  public ClientRepositoryImpl_Factory(Provider<AppDatabase> dbProvider,
      Provider<ClientDao> clientDaoProvider, Provider<SyncQueueDao> syncQueueDaoProvider,
      Provider<AuditLogDao> auditLogDaoProvider) {
    this.dbProvider = dbProvider;
    this.clientDaoProvider = clientDaoProvider;
    this.syncQueueDaoProvider = syncQueueDaoProvider;
    this.auditLogDaoProvider = auditLogDaoProvider;
  }

  @Override
  public ClientRepositoryImpl get() {
    return newInstance(dbProvider.get(), clientDaoProvider.get(), syncQueueDaoProvider.get(), auditLogDaoProvider.get());
  }

  public static ClientRepositoryImpl_Factory create(Provider<AppDatabase> dbProvider,
      Provider<ClientDao> clientDaoProvider, Provider<SyncQueueDao> syncQueueDaoProvider,
      Provider<AuditLogDao> auditLogDaoProvider) {
    return new ClientRepositoryImpl_Factory(dbProvider, clientDaoProvider, syncQueueDaoProvider, auditLogDaoProvider);
  }

  public static ClientRepositoryImpl newInstance(AppDatabase db, ClientDao clientDao,
      SyncQueueDao syncQueueDao, AuditLogDao auditLogDao) {
    return new ClientRepositoryImpl(db, clientDao, syncQueueDao, auditLogDao);
  }
}
