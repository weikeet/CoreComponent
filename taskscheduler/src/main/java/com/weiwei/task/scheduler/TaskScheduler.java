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
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author weicools
 * @date 2021.04.02
 */
public final class TaskScheduler {
  /**
   * 提供一个并行线程池，适合执行 CPU 密集型任务
   */
  public static ExecutorService parallelExecutor() {
    return TaskExecutor.get().getParallelExecutor();
  }

  /**
   * 提供一个超时线程池，适合执行 IO 等超时任务
   */
  public static ExecutorService timeoutExecutor() {
    return TaskExecutor.get().getTimeOutExecutor();
  }

  /**
   * 提供一个全局的主线程 handler
   */
  public static Handler mainHandler() {
    return TaskExecutor.get().getMainHandler();
  }

  /**
   * 提供一个全局的主线程的 async handler
   * handler 消息依然是在主线程执行，无需等待同步消息屏障
   */
  public static Handler asyncHandler() {
    return TaskExecutor.get().getAsyncHandler();
  }

  public static Handler provideHandler(String handlerName) {
    return provideHandler(handlerName, Process.THREAD_PRIORITY_BACKGROUND);
  }

  /**
   * 获取回调到 handlerName 线程的 handler. 一般用于在一个后台线程执行同一种任务，避免线程安全问题。
   *
   * @param handlerName 线程名
   * @param priority 线程优先级
   * @return 异步任务 handler
   */
  public static Handler provideHandler(String handlerName, int priority) {
    HandlerThread handlerThread = new HandlerThread(handlerName, priority);
    handlerThread.start();
    return new Handler(handlerThread.getLooper());
  }

  //region 周期性执行任务
  /**
   * 主线程周期性执行任务，默认立刻执行，之后间隔period执行，不需要时注意取消,每次执行时如果有相同的任务，默认会先取消
   *
   * @param task 执行的任务
   */
  public static void scheduleTask(final SchedulerTask task) {
    task.canceled.compareAndSet(true, false);

    final ScheduledExecutorService service = TaskExecutor.get().createScheduledExecutor();

    service.scheduleAtFixedRate(new Runnable() {
      @Override
      public void run() {
        if (task.canceled.get()) {
          service.shutdownNow();
        } else {
          if (task.mainThread) {
            runOnUIThread(task);
          } else {
            task.run();
          }
        }
      }
    }, task.startDelayMillisecond, task.periodMillisecond, TimeUnit.MILLISECONDS);
  }

  /**
   * 取消周期性任务
   *
   * @param schedulerTask 任务对象
   */
  public static void stopScheduleTask(final SchedulerTask schedulerTask) {
    schedulerTask.canceled.compareAndSet(false, true);
  }
  //endregion

  /**
   * 执行一个后台任务，无回调
   **/
  public static void executeTask(Runnable task) {
    parallelExecutor().execute(task);
  }

  /**
   * 执行一个后台任务，有回调
   **/
  public static <R> void executeTask(Task<R> task) {
    parallelExecutor().execute(task);
  }

  /**
   * 取消一个任务
   *
   * @param task 被取消的任务
   */
  public static void cancelTask(Task<?> task) {
    if (task != null) {
      task.cancel();
    }
  }

  /**
   * 执行一个后台IO任务，无回调
   **/
  public static void executeIOTask(Runnable task) {
    timeoutExecutor().execute(task);
  }

  /**
   * 执行一个后台IO任务，有回调
   **/
  public static <R> void executeIOTask(Task<R> task) {
    timeoutExecutor().execute(task);
  }

  /**
   * 使用一个单独的线程池来执行超时任务，避免引起他线程不够用导致超时
   * 通过实现error(Exception) 判断是否为 TimeoutException 来判断是否超时,
   * 不能100%保证实际的超时时间就是timeOutMillis，但一般没必要那么精确
   *
   * @param timeOutMillis 超时时间，单位毫秒
   */
  public static <R> void executeTimeOutTask(final long timeOutMillis, final Task<R> timeOutTask) {
    final Future<?> future = timeoutExecutor().submit(timeOutTask);

    timeoutExecutor().execute(new Runnable() {
      @Override
      public void run() {
        try {
          future.get(timeOutMillis, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
          runOnUIThread(new Runnable() {
            @Override
            public void run() {
              if (!timeOutTask.isCanceled()) {
                timeOutTask.cancel();
              }
            }
          });
        }
      }
    });
  }

  public static void removeUICallback(Runnable runnable) {
    mainHandler().removeCallbacks(runnable);
  }

  public static void runOnUIThread(Runnable runnable) {
    mainHandler().post(runnable);
  }

  public static void runOnUIThread(Runnable runnable, long delayed) {
    mainHandler().postDelayed(runnable, delayed);
  }

  /**
   * 执行有生命周期的任务, Lifecycle.Event.ON_DESTROY 移除任务
   */
  public static Runnable runOnUIThread(LifecycleOwner lifecycleOwner, Runnable runnable) {
    return runOnUIThread(lifecycleOwner, runnable, 0L);
  }

  public static Runnable runOnUIThread(LifecycleOwner lifecycleOwner, Runnable runnable, long delayed) {
    LifecycleRunnable lifecycleRunnableDelegate = new LifecycleRunnable(lifecycleOwner, mainHandler(), Lifecycle.Event.ON_DESTROY, runnable);
    mainHandler().postDelayed(lifecycleRunnableDelegate, delayed);
    return lifecycleRunnableDelegate;
  }

  /**
   * 执行有生命周期的任务, 指定 Lifecycle.Event 移除任务
   */
  public static Runnable runOnUIThread(LifecycleOwner lifecycleOwner, Lifecycle.Event targetEvent, Runnable runnable) {
    return runOnUIThread(lifecycleOwner, targetEvent, runnable, 0L);
  }

  public static Runnable runOnUIThread(LifecycleOwner lifecycleOwner, Lifecycle.Event targetEvent, Runnable runnable, long delayed) {
    LifecycleRunnable lifecycleRunnableDelegate = new LifecycleRunnable(lifecycleOwner, mainHandler(), targetEvent, runnable);
    mainHandler().postDelayed(lifecycleRunnableDelegate, delayed);
    return lifecycleRunnableDelegate;
  }

  /**
   * 外部提供执行任务的 Handler, Lifecycle.Event.ON_DESTROY 移除任务
   */
  public static Runnable runLifecycleTask(LifecycleOwner lifecycleOwner, Handler handler, Runnable runnable) {
    return runLifecycleTask(lifecycleOwner, handler, runnable, 0L);
  }

  public static Runnable runLifecycleTask(LifecycleOwner lifecycleOwner, Handler handler, Runnable runnable, long delayed) {
    LifecycleRunnable lifecycleRunnableDelegate = new LifecycleRunnable(lifecycleOwner, handler, Lifecycle.Event.ON_DESTROY, runnable);
    handler.postDelayed(lifecycleRunnableDelegate, delayed);
    return lifecycleRunnableDelegate;
  }

  /**
   * 外部提供执行任务的 Handler, 指定 Lifecycle.Event 移除任务
   */
  public static Runnable runLifecycleTask(LifecycleOwner lifecycleOwner, Handler handler, Lifecycle.Event targetEvent, Runnable runnable) {
    return runLifecycleTask(lifecycleOwner, handler, targetEvent, runnable, 0L);
  }

  public static Runnable runLifecycleTask(LifecycleOwner lifecycleOwner, Handler handler, Lifecycle.Event targetEvent, Runnable runnable, long delayed) {
    LifecycleRunnable lifecycleRunnableDelegate = new LifecycleRunnable(lifecycleOwner, handler, targetEvent, runnable);
    handler.postDelayed(lifecycleRunnableDelegate, delayed);
    return lifecycleRunnableDelegate;
  }

  public static boolean isMainThread() {
    return Looper.getMainLooper().getThread() == Thread.currentThread();
  }
}
