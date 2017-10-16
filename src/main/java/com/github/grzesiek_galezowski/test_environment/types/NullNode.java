package com.github.grzesiek_galezowski.test_environment.types;

import java.lang.reflect.Field;

public class NullNode implements ObjectGraphNode {
  private final Field field;
  private final Class<?> type;
  private int nestingLevel;

  public NullNode(final Field field, final Class<?> type, final int nestingLevel) {

    this.field = field;
    this.type = type;
    this.nestingLevel = nestingLevel;
  }

  public static NullNode create(final Field field, final int nestingLevel) {
    return new NullNode(field, field.getType(), nestingLevel);
  }

  @Override
  public boolean matches(final MatchPattern pattern) {
    return false;
  }

  @Override
  public boolean hasType(final Class<?> aClass) {
    return false;
  }

  @Override
  public boolean hasValue(final Object o) {
    return o == null;
  }
}
