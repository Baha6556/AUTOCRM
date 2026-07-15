package com.autocrm.presentation.viewmodel;

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
public final class CarsViewModel_Factory implements Factory<CarsViewModel> {
  private final Provider<CarRepository> carRepositoryProvider;

  public CarsViewModel_Factory(Provider<CarRepository> carRepositoryProvider) {
    this.carRepositoryProvider = carRepositoryProvider;
  }

  @Override
  public CarsViewModel get() {
    return newInstance(carRepositoryProvider.get());
  }

  public static CarsViewModel_Factory create(Provider<CarRepository> carRepositoryProvider) {
    return new CarsViewModel_Factory(carRepositoryProvider);
  }

  public static CarsViewModel newInstance(CarRepository carRepository) {
    return new CarsViewModel(carRepository);
  }
}
