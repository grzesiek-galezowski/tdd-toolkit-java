package com.github.grzesiek_galezowski.test_environment.types;

import java.util.List;

public class ObjectNode implements ObjectGraphNode {
  private final Object fieldValue;
  private final String fieldName;
  private final Class<?> type;
  private int nestingLevel;
  private final List<ObjectGraphNode> childNodes;

  public ObjectNode(
      final Class<?> type,
      final String fieldName,
      final Object fieldValue,
      final int nestingLevel,
      final List<ObjectGraphNode> childNodes) {
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
    this.type = type;
    this.nestingLevel = nestingLevel;
    this.childNodes = childNodes;
  }

  @Override
  public boolean matches(final MatchPattern pattern) {

    if (pattern.matches(this)) {
      pattern.matchFound(fieldName);

      if (pattern.isMatchedCompletely()) {
        return true;
      }

      if (pattern.isMatchedByAnyOf(childNodes)) {
        return true;
      } else {
        pattern.rewindOneMatch();
      }
    }
    return false;
  }

  @Override
  public boolean hasType(final Class<?> aClass) {
    return type.equals(aClass);
  }

  @Override
  public boolean hasValue(final Object o) {
    return fieldValue.equals(o);
  }

}
