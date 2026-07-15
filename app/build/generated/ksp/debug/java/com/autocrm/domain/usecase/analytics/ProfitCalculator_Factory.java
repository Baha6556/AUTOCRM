package com.autocrm.domain.usecase.analytics;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class ProfitCalculator_Factory implements Factory<ProfitCalculator> {
  @Override
  public ProfitCalculator get() {
    return newInstance();
  }

  public static ProfitCalculator_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static ProfitCalculator newInstance() {
    return new ProfitCalculator();
  }

  private static final class InstanceHolder {
    private static final ProfitCalculator_Factory INSTANCE = new ProfitCalculator_Factory();
  }
}
