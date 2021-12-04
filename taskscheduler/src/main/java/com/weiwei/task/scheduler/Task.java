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

import android.util.Log;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author weicools
 * @date 2021.04.02
 */
public abstract class Task<R> implements Runnable {

  private static final String TAG = "Task";

  private final AtomicReference<Thread> taskThread = new AtomicReference<>();
  private final AtomicBoolean canceledAtomic = new AtomicBoolean(false);

  /**
   * 异步线程处理任务，在非主线程执行
   *
   * @return 处理后的结果
   * @throws InterruptedException 获取 InterruptedException 异常，来判断任务是否被取消
   */
  public abstract R doInBackground() throws InterruptedException;

  /**
   * 异步线程处理后返回的结果，在主线程执行
   *
   * @param result 结果
   */
  public abstract void onSuccess(R result);

  /**
   * 异步线程处理出现异常的回调，按需处理，主线程执行
   *
   * @param t 异常
   */
  public void onFail(Throwable t) {
  }

  /**
   * 任务被取消的回调，主线程执行
   */
  public void onCancel() {
  }

  /**
   * 将任务标记为取消，没法真正取消正在执行的任务，只是结果不在 onSuccess 里回调，cancel 不一定能让任务停止，和 AsyncTask 同样道理
   **/
  void cancel() {
    this.canceledAtomic.set(true);

    Thread t = taskThread.get();
    if (t != null) {
      Log.d(TAG, "Task cancel: " + t.getName());
      t.interrupt();
    }

    TaskScheduler.runOnUIThread(new Runnable() {
      @Override
      public void run() {
        onCancel();
      }
    });
  }

  /**
   * 任务是已取消
   *
   * @return 任务是否已被取消
   */
  public boolean isCanceled() {
    return canceledAtomic.get();
  }

  @Override
  public void run() {
    try {
      Log.d(TAG, "Task : " + Thread.currentThread().getName());
      taskThread.compareAndSet(null, Thread.currentThread());
      canceledAtomic.set(false);

      final R result = doInBackground();

      TaskScheduler.runOnUIThread(new Runnable() {
        @Override
        public void run() {
          if (!isCanceled()) {
            onSuccess(result);
          }
        }
      });
    } catch (final Throwable throwable) {
      Log.e(TAG, "handle background Task  error " + throwable);
      TaskScheduler.runOnUIThread(new Runnable() {
        @Override
        public void run() {
          if (!isCanceled()) {
            onFail(throwable);
          }
        }
      });
    }
  }
}
