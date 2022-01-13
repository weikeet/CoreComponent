package com.weiwei.core.lifecycle;

import android.app.Activity;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import com.weiwei.core.global.AppGlobal;

/**
 * @author weicools
 * @date 2021.07.05
 */
public final class AppViewModelProviders implements ViewModelStoreOwner {
  private static final class Holder {
    private static final AppViewModelProviders INSTANCE = new AppViewModelProviders();
  }

  private final ViewModelStore appViewModelStore;

  private AppViewModelProviders() {
    this.appViewModelStore = new ViewModelStore();
  }

  @NonNull
  @Override
  public ViewModelStore getViewModelStore() {
    return appViewModelStore;
  }

  public static AppViewModelProviders getAppStoreOwner() {
    return Holder.INSTANCE;
  }

  public static ViewModelProvider of() {
    Application application = AppGlobal.get().getApplication();
    return new ViewModelProvider(getAppStoreOwner(), ViewModelProvider.AndroidViewModelFactory.getInstance(application));
  }

  public static ViewModelProvider of(@NonNull Activity activity) {
    Application application = activity.getApplication();
    if (application == null) {
      throw new IllegalStateException("Your activity/fragment is not yet attached to Application. You can't request ViewModel before onCreate call.");
    }

    return new ViewModelProvider(getAppStoreOwner(), ViewModelProvider.AndroidViewModelFactory.getInstance(application));
  }
}
