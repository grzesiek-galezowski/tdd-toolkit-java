package com.github.grzesiek_galezowski.test_environment.types;

public interface PatternElement {
  String getName();

  boolean isMatchedBy(ObjectNode objectNode);
}
