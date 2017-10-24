package com.github.grzesiek_galezowski.test_environment.types;

import com.google.common.primitives.Primitives;
import lombok.val;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TypeGraphNodeFactory {
  public static ObjectNode create(
      final String fieldName,
      final Object fieldValue,
      final List<ObjectGraphNode> fieldNodes) throws IllegalAccessException {
    return new ObjectNode(
        fieldValue.getClass(),
        fieldName,
        fieldValue,
        fieldNodes);
  }

  public static ObjectNode create(
      final String fieldName,
      final Object fieldValue, final int nestingLevel) {
    try {
      return create(fieldName, fieldValue,
          extractFieldsNodesFrom(fieldValue, nestingLevel+1));
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public static List<ObjectGraphNode> extractFieldsNodesFrom(final Object o, final int nestingLevel) {
    List<ObjectGraphNode> nodes = new ArrayList<>();
    Field[] declaredFields = o.getClass().getDeclaredFields();

    for (val field : declaredFields) {
      nodes.add(createNodeForFieldOf(o, field, nestingLevel));
    }
    return nodes;
  }

  private static ObjectGraphNode createNodeForFieldOf(final Object o, final Field field, final int nestingLevel) {
    try {
      field.setAccessible(true);
      Object fieldValue = field.get(o);
      if (fieldValue != null) {
        return create(field.getName(), fieldValue, createNodesFromFieldsOf(fieldValue, nestingLevel + 1));
      } else {
        return NullNode.create(field, nestingLevel);
      }
    } catch(IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  private static List<ObjectGraphNode> createNodesFromFieldsOf(final Object fieldValue, final int nestingLevel) throws IllegalAccessException {
    if (!Primitives.isWrapperType(fieldValue.getClass()) && !fieldValue.getClass().isPrimitive()) {
      return extractFieldsNodesFrom(fieldValue, nestingLevel);
    }
    return new ArrayList<>();
  }

  public static <T> ObjectNode create(final T value) {
    return create("root", value, 0);
  }
}
