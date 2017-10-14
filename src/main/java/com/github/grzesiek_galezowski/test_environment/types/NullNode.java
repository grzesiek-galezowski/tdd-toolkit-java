package com.github.grzesiek_galezowski.test_environment.types;

import java.lang.reflect.Field;

public class NullNode implements TypeGraphNode {
  private final Field field;
  private final Class<?> type;

  public NullNode(final Field field, final Class<?> type) {

    this.field = field;
    this.type = type;
  }

  public static NullNode create(final Field field) {
    return new NullNode(field, field.getType());
  }

  @Override
  public boolean matches(final LinearMatchPattern matchPattern) {
    return false;
  }

  @Override
  public boolean hasType(final Class<?> aClass) {
    return false;
  }
}
