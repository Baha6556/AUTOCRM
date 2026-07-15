package com.autocrm.di;

import com.autocrm.data.local.AppDatabase;
import com.autocrm.data.local.dao.CarDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideCarDaoFactory implements Factory<CarDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideCarDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public CarDao get() {
    return provideCarDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideCarDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideCarDaoFactory(dbProvider);
  }

  public static CarDao provideCarDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideCarDao(db));
  }
}
