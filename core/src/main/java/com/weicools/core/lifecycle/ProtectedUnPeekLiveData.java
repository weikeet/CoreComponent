package com.weicools.core.lifecycle;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author weicools
 * @date 2021.06.28
 */
public class ProtectedUnPeekLiveData<T> extends LiveData<T> {

  private final class ObserverProxy implements Observer<T> {

    private final Observer<? super T> target;

    public ObserverProxy(Observer<? super T> target) {
      this.target = target;
    }

    public Observer<? super T> getTarget() {
      return target;
    }

    @Override
    public void onChanged(T t) {
      if (observerStateMap.get(target) != null && observerStateMap.get(target)) {
        observerStateMap.put(target, false);
        if (t != null || isAllowNullValue) {
          target.onChanged(t);
        }
      }
    }
  }

  private final static String TAG = "ProtectedUnPeekLiveData";

  protected boolean isAllowNullValue;

  private final Map<Observer<? super T>, Boolean> observerStateMap = new ConcurrentHashMap<>();

  private final Map<Observer<? super T>, Observer<? super T>> observerProxyMap = new ConcurrentHashMap<>();

  private Observer<? super T> getObserverProxy(Observer<? super T> observer) {
    if (observerStateMap.containsKey(observer)) {
      Log.d(TAG, "observe repeatedly, observer has been attached to owner");
      return null;
    } else {
      observerStateMap.put(observer, false);
      ObserverProxy proxy = new ObserverProxy(observer);
      observerProxyMap.put(observer, proxy);
      return proxy;
    }
  }

  /**
   * TODO 当 liveData 用作 event 用途时，可使用该方法来观察 "生命周期敏感" 的非粘性消息
   *
   * state 是可变且私用的，event 是只读且公用的，
   * state 的倒灌是应景的，event 倒灌是不符预期的，
   *
   * 如果这样说还不理解，详见《LiveData 唯一可信源 读写分离设计》的解析：
   * https://xiaozhuanlan.com/topic/2049857631
   */
  @Override
  public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
    Observer<? super T> observerProxy = getObserverProxy(observer);
    if (observerProxy != null) {
      super.observe(owner, observerProxy);
    }
  }

  /**
   * TODO 当 liveData 用作 event 用途时，可使用该方法来观察 "生命周期不敏感" 的非粘性消息
   *
   * state 是可变且私用的，event 是只读且公用的，
   * state 的倒灌是应景的，event 倒灌是不符预期的，
   *
   * 如果这样说还不理解，详见《LiveData 唯一可信源 读写分离设计》的解析：
   * https://xiaozhuanlan.com/topic/2049857631
   */
  @Override
  public void observeForever(@NonNull Observer<? super T> observer) {
    Observer<? super T> observerProxy = getObserverProxy(observer);
    if (observerProxy != null) {
      super.observeForever(observerProxy);
    }
  }

  /**
   * 当 liveData 用作 state 用途时，可使用该方法来观察 "生命周期敏感" 的粘性消息
   *
   * state 是可变且私用的，event 是只读且公用的
   * state 的倒灌是应景的，event 倒灌是不符预期的
   */
  public void observeSticky(LifecycleOwner owner, Observer<T> observer) {
    super.observe(owner, observer);
  }

  /**
   * 当 liveData 用作 state 用途时，可使用该方法来观察 "生命周期不敏感" 的粘性消息
   *
   * state 是可变且私用的，event 是只读且公用的
   * state 的倒灌是应景的，event 倒灌是不符预期的
   */
  public void observeStickyForever(Observer<T> observer) {
    super.observeForever(observer);
  }

  @Override
  protected void setValue(T value) {
    if (value != null || isAllowNullValue) {
      for (Map.Entry<Observer<? super T>, Boolean> entry : observerStateMap.entrySet()) {
        entry.setValue(true);
      }
      super.setValue(value);
    }
  }

  @Override
  public void removeObserver(@NonNull Observer<? super T> observer) {
    Observer<? super T> proxy;
    Observer<? super T> target;
    if (observer instanceof ProtectedUnPeekLiveData.ObserverProxy) {
      proxy = observer;
      target = ((ObserverProxy) observer).getTarget();
    } else {
      proxy = observerProxyMap.get(observer);
      target = (proxy != null) ? observer : null;
    }
    if (proxy != null && target != null) {
      observerProxyMap.remove(target);
      observerStateMap.remove(target);
      super.removeObserver(proxy);
    }
  }

  /**
   * 手动将消息从内存中清空，
   * 以免无用消息随着 SharedViewModel 的长时间驻留而导致内存溢出的发生。
   */
  public void clear() {
    super.setValue(null);
  }
}
