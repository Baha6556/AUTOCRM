package com.autocrm.sync;

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
public final class EntityConflictResolver_Factory implements Factory<EntityConflictResolver> {
  @Override
  public EntityConflictResolver get() {
    return newInstance();
  }

  public static EntityConflictResolver_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static EntityConflictResolver newInstance() {
    return new EntityConflictResolver();
  }

  private static final class InstanceHolder {
    private static final EntityConflictResolver_Factory INSTANCE = new EntityConflictResolver_Factory();
  }
}
