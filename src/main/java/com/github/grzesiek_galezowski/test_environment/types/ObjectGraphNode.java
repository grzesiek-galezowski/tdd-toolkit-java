package com.github.grzesiek_galezowski.test_environment.types;

public interface ObjectGraphNode {
  boolean matches(MatchPattern pattern);

  boolean hasType(Class<?> aClass);

  boolean hasValue(Object o);
}
