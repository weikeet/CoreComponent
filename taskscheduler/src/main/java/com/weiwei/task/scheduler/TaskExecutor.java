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
import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author weicools
 * @date 2021.04.02
 */
final class TaskExecutor {
    private static final class Holder {
        private static final TaskExecutor INSTANCE = new TaskExecutor();
    }

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT + 1, 4));
    private static final int MAXIMUM_POOL_SIZE = CORE_POOL_SIZE * 2 + 1;
    private static final long KEEP_ALIVE = 60L;

    private final ExecutorService parallelExecutor;
    private final ExecutorService timeOutExecutor;

    private final Handler asyncHandler;
    private final Handler mainHandler;

    static TaskExecutor get() {
        return Holder.INSTANCE;
    }

    private TaskExecutor() {
        mainHandler = new Handler(Looper.getMainLooper());
        asyncHandler = HandlerCompat.createAsync(Looper.getMainLooper());

        parallelExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(256), createThreadFactory("TaskExecutor #"), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                timeOutExecutor.execute(r);
            }
        });

        timeOutExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, KEEP_ALIVE, TimeUnit.SECONDS,
            new SynchronousQueue<>(), createThreadFactory("TaskExecutor timeoutThread #"));

        Log.d("TaskExecutor", "cpuCount=" + CPU_COUNT + ", corePoolSize=" + CORE_POOL_SIZE + ", maximumPoolSize=" + MAXIMUM_POOL_SIZE);
    }

    private ThreadFactory createThreadFactory(final String name) {
        return new ThreadFactory() {
            private final AtomicInteger count = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(new BackgroundRunnable(r), name + count.getAndIncrement());
            }
        };
    }

    @NonNull
    ScheduledExecutorService createScheduledExecutor() {
        return new ScheduledThreadPoolExecutor(1, createThreadFactory("TaskExecutor scheduler #"));
    }

    @NonNull
    ExecutorService getParallelExecutor() {
        return parallelExecutor;
    }

    @NonNull
    ExecutorService getTimeOutExecutor() {
        return timeOutExecutor;
    }

    @NonNull
    Handler getMainHandler() {
        return mainHandler;
    }

    @NonNull
    Handler getAsyncHandler() {
        return asyncHandler;
    }
}
