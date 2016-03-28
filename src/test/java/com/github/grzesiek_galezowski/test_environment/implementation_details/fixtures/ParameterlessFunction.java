package com.github.grzesiek_galezowski.test_environment.implementation_details.fixtures;

@FunctionalInterface
public interface ParameterlessFunction<T> {

  T create();
}
