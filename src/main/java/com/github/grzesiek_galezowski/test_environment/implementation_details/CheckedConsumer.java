package com.github.grzesiek_galezowski.test_environment.implementation_details;

@FunctionalInterface
public interface CheckedConsumer<T> {
  void accept(T t) throws Throwable;
}
