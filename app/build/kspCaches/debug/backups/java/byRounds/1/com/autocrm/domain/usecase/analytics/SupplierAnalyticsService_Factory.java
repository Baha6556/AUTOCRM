package com.autocrm.domain.usecase.analytics;

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
public final class SupplierAnalyticsService_Factory implements Factory<SupplierAnalyticsService> {
  private final Provider<CarRepository> carRepositoryProvider;

  public SupplierAnalyticsService_Factory(Provider<CarRepository> carRepositoryProvider) {
    this.carRepositoryProvider = carRepositoryProvider;
  }

  @Override
  public SupplierAnalyticsService get() {
    return newInstance(carRepositoryProvider.get());
  }

  public static SupplierAnalyticsService_Factory create(
      Provider<CarRepository> carRepositoryProvider) {
    return new SupplierAnalyticsService_Factory(carRepositoryProvider);
  }

  public static SupplierAnalyticsService newInstance(CarRepository carRepository) {
    return new SupplierAnalyticsService(carRepository);
  }
}
