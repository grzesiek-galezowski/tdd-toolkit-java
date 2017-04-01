package com.github.grzesiek_galezowski.test_environment.implementation_details;

@FunctionalInterface
public interface CheckedFunction<T, R> {
  R apply(T t) throws Throwable;
}

