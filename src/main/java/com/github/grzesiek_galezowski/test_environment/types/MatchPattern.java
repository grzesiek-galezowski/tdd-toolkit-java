package com.github.grzesiek_galezowski.test_environment.types;

import java.util.List;

public interface MatchPattern {
  String mismatchDescription();

  boolean matches(ObjectGraphNode objectNode);

  void matchFound(String fieldName);

  boolean isMatchedByAnyOf(List<ObjectGraphNode> childNodes);

}
