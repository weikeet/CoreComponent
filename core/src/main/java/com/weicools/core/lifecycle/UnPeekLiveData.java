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
public class UnPeekLiveData<T> extends LiveData<T> {

  private final static String TAG = "V6Test";

  protected boolean isAllowNullValue;

  private final ConcurrentHashMap<Observer<? super T>, Boolean> observerStateMap = new ConcurrentHashMap<>();

  private final ConcurrentHashMap<Observer<? super T>, Observer<? super T>> observerProxyMap = new ConcurrentHashMap<>();

  /**
   * 观察 "生命周期敏感" 的非粘性消息
   */
  @Override
  public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
    Observer<? super T> observer1 = getObserverProxy(observer);
    if (observer1 != null) {
      super.observe(owner, observer1);
    }
  }

  /**
   * 观察 "生命周期不敏感" 的非粘性消息
   */
  @Override
  public void observeForever(@NonNull Observer<? super T> observer) {
    Observer<? super T> observer1 = getObserverProxy(observer);
    if (observer1 != null) {
      super.observeForever(observer1);
    }
  }

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

  private class ObserverProxy implements Observer<T> {

    private final Observer<? super T> target;

    public ObserverProxy(Observer<? super T> target) {
      this.target = target;
    }

    public Observer<? super T> getTarget() {
      return target;
    }

    @Override
    public void onChanged(T t) {
      Boolean state = observerStateMap.get(target);
      if (state != null && state) {
        observerStateMap.put(target, false);
        if (t != null || isAllowNullValue) {
          target.onChanged(t);
        }
      }
    }
  }

  /**
   * 观察 "生命周期敏感" 的粘性消息
   */
  public void observeSticky(LifecycleOwner owner, Observer<T> observer) {
    super.observe(owner, observer);
  }

  /**
   * 观察 "生命周期不敏感" 的粘性消息
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
    if (observer instanceof UnPeekLiveData.ObserverProxy) {
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
