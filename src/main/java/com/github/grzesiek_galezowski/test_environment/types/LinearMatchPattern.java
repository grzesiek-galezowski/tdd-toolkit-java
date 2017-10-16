package com.github.grzesiek_galezowski.test_environment.types;

import com.google.common.base.Joiner;

import java.util.Arrays;
import java.util.List;

public class LinearMatchPattern implements MatchPattern {
  private final MatchResult matchResult = new MatchResult();
  private PatternElement[] patternElements;
  private int currentIndex = 0;

  public LinearMatchPattern(final PatternElement[] patternElements) {
    this.patternElements = patternElements;
    matchResult.newBranchAttempt();
  }

  @Override
  public boolean isMatchedByAnyOf(final List<ObjectGraphNode> fieldNodes) {

    return fieldNodes
        .stream()
        .anyMatch(n -> n.matches(this));
  }

  @Override
  public boolean matches(final ObjectNode objectNode) {
    PatternElement patternElement = patternElements[currentIndex];
    return patternElement.isMatchedBy(objectNode);
  }

  @Override
  public void matchFound(final String fieldName) {
    matchResult.revertBranchIfInvalidTo(currentIndex);
    matchResult.addMatched(fieldName);
    currentIndex++;
  }

  @Override
  public boolean isMatchedCompletely() {
    return currentIndex == patternElements.length;
  }

  @Override
  public String getPatternString() {
    return Joiner.on("->").join(Arrays.stream(patternElements).map(t -> t.getName()).toArray());
  }

  @Override
  public String getBestMatchesString() {
    return matchResult.getBestBranchesString();
  }

  @Override
  public void rewindOneMatch() {
    currentIndex--;
    matchResult.invalidateCurrentBranch();
  }

  public static LinearMatchPattern forTypes(final Class<?>[] typePath) {
    return new LinearMatchPattern(Arrays.stream(typePath).map(t -> new PatternTypeElement(t)).toArray(PatternElement[]::new));
  }
}
