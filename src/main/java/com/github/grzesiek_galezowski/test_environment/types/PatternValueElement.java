package com.github.grzesiek_galezowski.test_environment.types;

public class PatternValueElement implements PatternElement {
  private Object o;

  public PatternValueElement(final Object o) {
    this.o = o;
  }

  @Override
  public String getName() {
    return o.toString();
  }

  @Override
  public boolean isMatchedBy(final ObjectNode objectNode) {
    return objectNode.hasValue(o);
  }
}
