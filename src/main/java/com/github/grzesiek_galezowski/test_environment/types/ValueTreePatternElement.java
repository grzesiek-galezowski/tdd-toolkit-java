package com.github.grzesiek_galezowski.test_environment.types;

public class ValueTreePatternElement implements TreePatternElement {
  private Object expected;

  public ValueTreePatternElement(final Object expected) {

    this.expected = expected;
  }

  @Override
  public boolean isMatchedBy(final ObjectGraphNode objectNode) {
    return objectNode.hasValue(expected);
  }

  @Override
  public String toStringUsing(final NodePrinter nodePrinter, final boolean matched, final int nestingLevel) {
    return nodePrinter.typeString(expected.getClass(), matched, nestingLevel);
  }
}
