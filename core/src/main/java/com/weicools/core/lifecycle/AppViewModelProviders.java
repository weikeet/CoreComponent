package com.weicools.core.lifecycle;

import android.app.Activity;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import com.weicools.core.app.BaseApplication;
import com.weicools.core.global.AppGlobal;

/**
 * @author weicools
 * @date 2021.07.05
 */
public final class AppViewModelProviders {
  public static ViewModelProvider of() {
    BaseApplication application = AppGlobal.get().getApplication();
    return new ViewModelProvider(application, ViewModelProvider.AndroidViewModelFactory.getInstance(application));
  }

  public static ViewModelProvider of(@NonNull Activity activity) {
    Application application = activity.getApplication();
    if (application == null) {
      throw new IllegalStateException("Your activity/fragment is not yet attached to Application. You can't request ViewModel before onCreate call.");
    }

    if (!(application instanceof ViewModelStoreOwner)) {
      throw new IllegalStateException("Your application is not implements ViewModelStoreOwner");
    }
    ViewModelStoreOwner storeOwner = (ViewModelStoreOwner) application;
    return new ViewModelProvider(storeOwner, ViewModelProvider.AndroidViewModelFactory.getInstance(application));
  }

}
