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
public final class ExpenseAnalyticsService_Factory implements Factory<ExpenseAnalyticsService> {
  @Override
  public ExpenseAnalyticsService get() {
    return newInstance();
  }

  public static ExpenseAnalyticsService_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static ExpenseAnalyticsService newInstance() {
    return new ExpenseAnalyticsService();
  }

  private static final class InstanceHolder {
    private static final ExpenseAnalyticsService_Factory INSTANCE = new ExpenseAnalyticsService_Factory();
  }
}
