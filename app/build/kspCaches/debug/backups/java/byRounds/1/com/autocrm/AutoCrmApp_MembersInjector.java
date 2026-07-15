package com.autocrm;

import androidx.hilt.work.HiltWorkerFactory;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class AutoCrmApp_MembersInjector implements MembersInjector<AutoCrmApp> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public AutoCrmApp_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<AutoCrmApp> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new AutoCrmApp_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(AutoCrmApp instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.autocrm.AutoCrmApp.workerFactory")
  public static void injectWorkerFactory(AutoCrmApp instance, HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
