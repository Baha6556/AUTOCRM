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
public final class ROIService_Factory implements Factory<ROIService> {
  @Override
  public ROIService get() {
    return newInstance();
  }

  public static ROIService_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static ROIService newInstance() {
    return new ROIService();
  }

  private static final class InstanceHolder {
    private static final ROIService_Factory INSTANCE = new ROIService_Factory();
  }
}
