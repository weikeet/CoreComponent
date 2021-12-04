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

package com.weiwei.task.scheduler

import android.os.Handler
import android.os.Looper
import java.util.concurrent.ExecutorService

/**
 * @author weicools
 * @date 2021.05.24
 */

inline val parallelExecutor: ExecutorService get() = TaskScheduler.parallelExecutor()

inline val timeoutExecutor: ExecutorService get() = TaskScheduler.timeoutExecutor()

inline val mainHandler: Handler get() = TaskScheduler.mainHandler()

inline val asyncHandler: Handler get() = TaskScheduler.asyncHandler()

inline val isMainThread: Boolean get() = (Looper.getMainLooper().thread === Thread.currentThread())
