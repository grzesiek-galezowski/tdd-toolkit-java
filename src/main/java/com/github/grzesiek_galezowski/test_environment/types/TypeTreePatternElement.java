package com.github.grzesiek_galezowski.test_environment.types;

public class TypeTreePatternElement implements TreePatternElement {
  private final Class<?> value;

  public TypeTreePatternElement(final Class<?> value) {
    this.value = value;
  }

  public String toStringUsing(final NodePrinter nodePrinter, final boolean matched, final int nestingLevel) {
    return nodePrinter.typeString(getValue(), matched, nestingLevel);
  }

  public boolean isMatchedBy(final ObjectGraphNode objectNode) {
    return objectNode.hasType(getValue());
  }

  public Class<?> getValue() {
    return value;
  }
}
