# TaskScheduler

一个简单高效处理异步任务、周期性任务、线程切换的库。

## Getting started

In your root `build.gradle`: add repositories

```groovy
allprojects {
	repositories {
		mavenCentral()
	}
}
```

In your `module/build.gradle`: add dependency

```groovy
dependencies {
	compile 'com.weicools:taskscheduler:1.0.0'
}
```


## Usage

### 绑定生命周期

**支持当通过 Handler 的方式在 Activity、Fragment 中执行或延迟执行匿名内部类的 runnable时，当 onDestroy 时，runnable 自动被移除，极大简化使用,避免内存泄漏**

在 Activity 里或者 Fragment 里使用时,在 onDestroy 不需要显示的移除匿名内部类

```java
TaskScheduler.runOnUIThread(this,new Runnable() {
    @Override
    public void run() {
    }
}, 5000L);
```

可指定在特定生命周期移除未执行任务，如onStop

```java
TaskScheduler.runOnUIThread(this, Lifecycle.Event.ON_STOP, new Runnable() {
    @Override
    public void run() {
        Log.i("LifeFragment","runTask with life on Stop");
    }
}, 5000L);
```

外部传入任意Handler

```java
TaskScheduler.runLifecycleRunnable(this,anyHandler,,new Runnable() {
    @Override
    public void run() {
    }
}, 5000L);
```


### 简单的不需要回调的异步任务

```java
TaskScheduler.execute(new Runnable() {
    @Override
    public void run() {
    // DO BACKGROUND WORK
    }
});
```

####  异步需要回调的任务

```java
Task task = new Task<String>() {

    @Override
    public String doInBackground()  {
        return "background task";
    }

    @Override
    public void onSuccess(String result) {
       //回调到主线程
    }

    @Override
    public void onFail(Throwable throwable) {
        super.onFail(throwable);
        //doInBackground 里发生错误时回调
    }

    @Override
    public void onCancel() {
        super.onCancel();
        //任务被取消时回调
    }
});
```

doInBackground 和 onSuccess(Object result) 时默认必须实现的接口，其他实现都是可选的。除了doInBackground 在异步线程中执行，其他的都会回调到主线程执行。

**执行任务**

```java
TaskScheduler.execute(task);

```

**取消任务**

将会回调到onCancel(),没法真正取消正在执行的任务，只是结果不在onSuccess里回调， 不一定能让任务停止，和AsyncTask同样道理，可参考之前写的一篇博客[为什么AsyncTask的cancel不能真正的让线程终止运行](http://silencedut.com/2016/07/08/基于最新版本的AsyncTask源码解读及AsyncTask的黑暗面/)

```java
TaskScheduler.cancel(task);
```

**超时任务**

如果任务超时，将会回调到onCancel()

```java
TaskScheduler.executeTimeOutTask(timeOutMillis,task);
```

**周期性任务**

如果任务超时，将会回调到onCancel()

```java
// 主线程,Io线程可选
TaskScheduler.scheduleUITask( SchedulerTask task);

// 取消任务
TaskScheduler.stopScheduleTask(SchedulerTask task)
```

**其他的一些常用方法**


```java
/**
*获取回调到handlerName线程的handler.一般用于在一个后台线程执行同一种任务，避免线程安全问题。如数据库，文件操作
*/
Handler provideHandler(String handlerName)

/**
 * 提供一个公用的异步handler
 */
Handler ioHandler()

/**
*常用的handler的操作
*/
runOnUIThread(@NonNull Runnable runnable)

runOnUIThread(Runnable runnable,long delayed)

removeUICallback(@NonNull Runnable runnable)
```
