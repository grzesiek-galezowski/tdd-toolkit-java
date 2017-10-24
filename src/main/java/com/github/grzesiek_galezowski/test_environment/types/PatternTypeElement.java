package com.github.grzesiek_galezowski.test_environment.types;

public class PatternTypeElement implements PatternElement {
  private Class<?> expectedType;

  public PatternTypeElement(final Class<?> expectedType) {
    this.expectedType = expectedType;
  }

  @Override
  public String getName() {
    return expectedType.getSimpleName();
  }

  @Override
  public boolean isMatchedBy(final ObjectGraphNode objectNode) {
    return objectNode.hasType(expectedType);
  }

}
