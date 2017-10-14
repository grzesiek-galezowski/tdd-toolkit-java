package com.github.grzesiek_galezowski.test_environment.types;

import java.util.List;

public class TypeNode implements TypeGraphNode {
  private final Object fieldValue;
  private final String fieldName;
  private final Class<?> type;
  private final List<TypeGraphNode> fieldNodes;

  public TypeNode(
      final Class<?> type,
      final String fieldName,
      final Object fieldValue,
      final List<TypeGraphNode> fieldNodes) {
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
    this.type = type;
    this.fieldNodes = fieldNodes;
  }

  @Override
  public boolean matches(final LinearMatchPattern matchPattern) {

    if (matchPattern.nextItemIs(type)) {
      matchPattern.matchFound(fieldName);

      if (matchPattern.isMatchedCompletely()) {
        return true;
      }

      if (matchPattern.isMatchedByAnyOf(fieldNodes)) {
        return true;
      } else {
        matchPattern.revertOneMatch();
      }
    }
    return false;
  }

}
