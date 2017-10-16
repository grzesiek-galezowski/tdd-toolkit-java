package com.github.grzesiek_galezowski.test_environment.buffer;

import com.github.grzesiek_galezowski.test_environment.buffer.interfaces.BufferObserver;

public class BufferObservers {
  public static <T> BufferObserver<T> createDefault() {
    return new ReportingObserver<T>();
  }

  public static <T> BufferObserver<T> none() {
    return new NullObserver<T>();
  }
}
