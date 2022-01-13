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

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author weicools
 * @date 2021.04.02
 */
public abstract class SchedulerTask implements Runnable {

  long startDelayMillisecond;
  long periodMillisecond;

  boolean mainThread = true;

  AtomicBoolean canceled = new AtomicBoolean(false);

  protected SchedulerTask(long periodMillisecond) {
    this.periodMillisecond = periodMillisecond;
  }

  protected SchedulerTask(long periodMillisecond, boolean mainThread) {
    this.periodMillisecond = periodMillisecond;
    this.mainThread = mainThread;
  }

  protected SchedulerTask(long periodMillisecond, boolean mainThread, long startDelayMillisecond) {
    this.periodMillisecond = periodMillisecond;
    this.mainThread = mainThread;
    this.startDelayMillisecond = startDelayMillisecond;
  }

  public abstract void onSchedule();

  @Override
  public void run() {
    if (!canceled.get()) {
      onSchedule();
    }
  }
}
