package com.github.grzesiek_galezowski.test_environment.types;

import java.util.List;

public interface MatchPattern {
  boolean matches(ObjectNode objectNode);

  void matchFound(String fieldName);

  boolean isMatchedCompletely();

  String getPatternString();

  String getBestMatchesString();

  void rewindOneMatch();

  boolean isMatchedByAnyOf(List<ObjectGraphNode> childNodes);
}
