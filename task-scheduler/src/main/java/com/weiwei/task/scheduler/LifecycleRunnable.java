/*
 * Copyright (c) 2021 Weiwei. https://github.com/lecymeng
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.weiwei.task.scheduler;

import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * @author weicools
 * @date 2021.04.02
 */
public class LifecycleRunnable implements Runnable {
  private Runnable originRunnable;
  private LifecycleOwner lifecycleOwner;
  private LifecycleEventObserver lifecycleObserver;

  public LifecycleRunnable(LifecycleOwner lifecycleOwner, Handler handler, Runnable originRunnable) {
    init(lifecycleOwner, handler, Lifecycle.Event.ON_DESTROY, originRunnable);
  }

  public LifecycleRunnable(LifecycleOwner lifecycleOwner, Handler handler, Lifecycle.Event targetEvent, Runnable originRunnable) {
    init(lifecycleOwner, handler, targetEvent, originRunnable);
  }

  private void init(final LifecycleOwner lifecycleOwner, final Handler handler, final Lifecycle.Event targetEvent, final Runnable originRunnable) {
    if (originRunnable == null || lifecycleOwner == null) {
      return;
    }
    this.lifecycleOwner = lifecycleOwner;
    this.originRunnable = originRunnable;

    lifecycleObserver = new LifecycleEventObserver() {
      @Override
      public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if (event == targetEvent) {
          if (LifecycleRunnable.this.lifecycleOwner != null) {
            LifecycleRunnable.this.lifecycleOwner.getLifecycle().removeObserver(this);
          }
          handler.removeCallbacks(LifecycleRunnable.this);
        }
      }
    };

    if (TaskScheduler.isMainThread()) {
      this.lifecycleOwner.getLifecycle().addObserver(lifecycleObserver);
    } else {
      TaskScheduler.runOnUIThread(new Runnable() {
        @Override
        public void run() {
          LifecycleRunnable.this.lifecycleOwner.getLifecycle().addObserver(lifecycleObserver);
        }
      });
    }
  }

  @Override
  public void run() {
    if (originRunnable != null && lifecycleOwner != null) {
      originRunnable.run();
      lifecycleOwner.getLifecycle().removeObserver(lifecycleObserver);
    }
  }
}
