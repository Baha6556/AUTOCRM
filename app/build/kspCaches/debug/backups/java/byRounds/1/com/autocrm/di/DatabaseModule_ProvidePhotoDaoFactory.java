package com.autocrm.di;

import com.autocrm.data.local.AppDatabase;
import com.autocrm.data.local.dao.PhotoDao;
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
public final class DatabaseModule_ProvidePhotoDaoFactory implements Factory<PhotoDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvidePhotoDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public PhotoDao get() {
    return providePhotoDao(dbProvider.get());
  }

  public static DatabaseModule_ProvidePhotoDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvidePhotoDaoFactory(dbProvider);
  }

  public static PhotoDao providePhotoDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.providePhotoDao(db));
  }
}
