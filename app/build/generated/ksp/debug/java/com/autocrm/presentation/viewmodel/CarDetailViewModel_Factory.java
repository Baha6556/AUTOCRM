package com.autocrm.presentation.viewmodel;

import androidx.lifecycle.SavedStateHandle;
import com.autocrm.domain.repository.CarRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class CarDetailViewModel_Factory implements Factory<CarDetailViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<CarRepository> carRepositoryProvider;

  public CarDetailViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<CarRepository> carRepositoryProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.carRepositoryProvider = carRepositoryProvider;
  }

  @Override
  public CarDetailViewModel get() {
    return newInstance(savedStateHandleProvider.get(), carRepositoryProvider.get());
  }

  public static CarDetailViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<CarRepository> carRepositoryProvider) {
    return new CarDetailViewModel_Factory(savedStateHandleProvider, carRepositoryProvider);
  }

  public static CarDetailViewModel newInstance(SavedStateHandle savedStateHandle,
      CarRepository carRepository) {
    return new CarDetailViewModel(savedStateHandle, carRepository);
  }
}
