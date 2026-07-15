package com.autocrm.data.repository;

import com.autocrm.data.local.AppDatabase;
import com.autocrm.data.local.dao.AuditLogDao;
import com.autocrm.data.local.dao.CarDao;
import com.autocrm.data.local.dao.ExpenseDao;
import com.autocrm.data.local.dao.PhotoDao;
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
public final class CarRepositoryImpl_Factory implements Factory<CarRepositoryImpl> {
  private final Provider<AppDatabase> dbProvider;

  private final Provider<CarDao> carDaoProvider;

  private final Provider<ExpenseDao> expenseDaoProvider;

  private final Provider<PhotoDao> photoDaoProvider;

  private final Provider<SyncQueueDao> syncQueueDaoProvider;

  private final Provider<AuditLogDao> auditLogDaoProvider;

  public CarRepositoryImpl_Factory(Provider<AppDatabase> dbProvider,
      Provider<CarDao> carDaoProvider, Provider<ExpenseDao> expenseDaoProvider,
      Provider<PhotoDao> photoDaoProvider, Provider<SyncQueueDao> syncQueueDaoProvider,
      Provider<AuditLogDao> auditLogDaoProvider) {
    this.dbProvider = dbProvider;
    this.carDaoProvider = carDaoProvider;
    this.expenseDaoProvider = expenseDaoProvider;
    this.photoDaoProvider = photoDaoProvider;
    this.syncQueueDaoProvider = syncQueueDaoProvider;
    this.auditLogDaoProvider = auditLogDaoProvider;
  }

  @Override
  public CarRepositoryImpl get() {
    return newInstance(dbProvider.get(), carDaoProvider.get(), expenseDaoProvider.get(), photoDaoProvider.get(), syncQueueDaoProvider.get(), auditLogDaoProvider.get());
  }

  public static CarRepositoryImpl_Factory create(Provider<AppDatabase> dbProvider,
      Provider<CarDao> carDaoProvider, Provider<ExpenseDao> expenseDaoProvider,
      Provider<PhotoDao> photoDaoProvider, Provider<SyncQueueDao> syncQueueDaoProvider,
      Provider<AuditLogDao> auditLogDaoProvider) {
    return new CarRepositoryImpl_Factory(dbProvider, carDaoProvider, expenseDaoProvider, photoDaoProvider, syncQueueDaoProvider, auditLogDaoProvider);
  }

  public static CarRepositoryImpl newInstance(AppDatabase db, CarDao carDao, ExpenseDao expenseDao,
      PhotoDao photoDao, SyncQueueDao syncQueueDao, AuditLogDao auditLogDao) {
    return new CarRepositoryImpl(db, carDao, expenseDao, photoDao, syncQueueDao, auditLogDao);
  }
}
