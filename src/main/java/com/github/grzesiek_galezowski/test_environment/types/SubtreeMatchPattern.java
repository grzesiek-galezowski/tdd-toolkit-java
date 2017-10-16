package com.github.grzesiek_galezowski.test_environment.types;

import java.util.List;

public class SubtreeMatchPattern implements MatchPattern {
  @Override
  public boolean matches(final ObjectNode objectNode) {
    return false;
  }

  @Override
  public void matchFound(final String fieldName) {

  }

  @Override
  public boolean isMatchedCompletely() {
    return false;
  }

  @Override
  public String getPatternString() {
    return null;
  }

  @Override
  public String getBestMatchesString() {
    return null;
  }

  @Override
  public void rewindOneMatch() {

  }

  @Override
  public boolean isMatchedByAnyOf(final List<ObjectGraphNode> childNodes) {
    return false;
  }
}
