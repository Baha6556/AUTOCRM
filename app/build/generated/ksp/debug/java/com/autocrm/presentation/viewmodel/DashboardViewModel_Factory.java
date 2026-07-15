package com.autocrm.presentation.viewmodel;

import com.autocrm.domain.repository.CarRepository;
import com.autocrm.domain.usecase.analytics.ProfitCalculator;
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<CarRepository> carRepositoryProvider;

  private final Provider<ProfitCalculator> profitCalculatorProvider;

  public DashboardViewModel_Factory(Provider<CarRepository> carRepositoryProvider,
      Provider<ProfitCalculator> profitCalculatorProvider) {
    this.carRepositoryProvider = carRepositoryProvider;
    this.profitCalculatorProvider = profitCalculatorProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(carRepositoryProvider.get(), profitCalculatorProvider.get());
  }

  public static DashboardViewModel_Factory create(Provider<CarRepository> carRepositoryProvider,
      Provider<ProfitCalculator> profitCalculatorProvider) {
    return new DashboardViewModel_Factory(carRepositoryProvider, profitCalculatorProvider);
  }

  public static DashboardViewModel newInstance(CarRepository carRepository,
      ProfitCalculator profitCalculator) {
    return new DashboardViewModel(carRepository, profitCalculator);
  }
}
