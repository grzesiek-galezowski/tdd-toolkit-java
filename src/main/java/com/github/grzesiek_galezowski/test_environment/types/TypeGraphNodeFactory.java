package com.github.grzesiek_galezowski.test_environment.types;

import com.google.common.primitives.Primitives;
import lombok.val;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TypeGraphNodeFactory {
  public static TypeNode create(final String fieldName, final Object fieldValue, final List<TypeGraphNode> fieldNodes) throws IllegalAccessException {
    return new TypeNode(
        fieldValue.getClass(),
        fieldName,
        fieldValue,
        fieldNodes);
  }

  public static TypeNode create(
      final String fieldName,
      final Object fieldValue) {
    try {
      return create(fieldName, fieldValue,
          extractFieldsNodesFrom(fieldValue));
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public static List<TypeGraphNode> extractFieldsNodesFrom(final Object o) {
    List<TypeGraphNode> nodes = new ArrayList<>();
    Field[] declaredFields = o.getClass().getDeclaredFields();

    for (val field : declaredFields) {
      nodes.add(getForFieldOf(o, field));
    }
    return nodes;
  }

  private static TypeGraphNode getForFieldOf(final Object o, final Field field) {
    try {
      field.setAccessible(true);
      Object fieldValue = field.get(o);
      if (fieldValue != null) {
        return create(field.getName(), fieldValue, createNodesFromFieldsOf(fieldValue));
      } else {
        return NullNode.create(field);
      }
    } catch(IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  private static List<TypeGraphNode> createNodesFromFieldsOf(final Object fieldValue) throws IllegalAccessException {
    if (!Primitives.isWrapperType(fieldValue.getClass()) && !fieldValue.getClass().isPrimitive()) {
      return extractFieldsNodesFrom(fieldValue);
    }
    return new ArrayList<>();
  }

  public static <T> TypeNode create(final T value) {
    return create("root", value);
  }
}
