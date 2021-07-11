package com.weicools.core.lifecycle;

/**
 * @author weicools
 * @date 2021.06.28
 */
public class MutableUnPeekLiveData<T> extends UnPeekLiveData<T> {
  @Override
  public void setValue(T value) {
    super.setValue(value);
  }

  @Override
  public void postValue(T value) {
    super.postValue(value);
  }

  public static class Builder<T> {

    /**
     * 是否允许传入 null value
     */
    private boolean isAllowNullValue;

    public Builder<T> setAllowNullValue(boolean allowNullValue) {
      this.isAllowNullValue = allowNullValue;
      return this;
    }

    public UnPeekLiveData<T> create() {
      UnPeekLiveData<T> liveData = new UnPeekLiveData<>();
      liveData.isAllowNullValue = this.isAllowNullValue;
      return liveData;
    }
  }
}
