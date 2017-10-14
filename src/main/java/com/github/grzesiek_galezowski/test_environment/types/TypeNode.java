package com.github.grzesiek_galezowski.test_environment.types;

import java.util.List;

public class TypeNode implements TypeGraphNode {
  private final Object fieldValue;
  private final String fieldName;
  private final Class<?> type;
  private final List<TypeGraphNode> childNodes;

  public TypeNode(
      final Class<?> type,
      final String fieldName,
      final Object fieldValue,
      final List<TypeGraphNode> childNodes) {
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
    this.type = type;
    this.childNodes = childNodes;
  }

  @Override
  public boolean matches(final LinearMatchPattern pattern) {

    if (pattern.matches(this)) {
      pattern.matchFound(fieldName);

      if (pattern.isMatchedCompletely()) {
        return true;
      }

      if (pattern.isMatchedByAnyOf(childNodes)) {
        return true;
      } else {
        pattern.revertOneMatch();
      }
    }
    return false;
  }

  @Override
  public boolean hasType(final Class<?> aClass) {
    return type.equals(aClass);
  }

}
