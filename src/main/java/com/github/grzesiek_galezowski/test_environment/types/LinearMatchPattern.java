package com.github.grzesiek_galezowski.test_environment.types;

import com.google.common.base.Joiner;

import java.util.Arrays;
import java.util.List;

import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.toList;

public class LinearMatchPattern implements MatchPattern {
  private final MatchResult matchResult;
  private List<PatternElement> patternElements;

  public LinearMatchPattern(final List<PatternElement> patternElements, final MatchResult matchResult) {
    this.matchResult = matchResult;
    this.patternElements = patternElements;
  }

  @Override
  public boolean isMatchedByAnyOf(final List<ObjectGraphNode> fieldNodes) {
    if(patternElements.size() == 1) {
      return true;
    }

    boolean result = fieldNodes
        .stream()
        .anyMatch(n -> n.matches(
            LinearMatchPattern.with(
                remaining(patternElements),
                matchResult.newBranch())));
    return result;
  }

  public static LinearMatchPattern with(final List<PatternElement> remaining, final MatchResult matchResult) {
    return new LinearMatchPattern(
        remaining, matchResult);
  }

  private static List<PatternElement> remaining(final List<PatternElement> patternElements) {
    return patternElements.subList(
        1, patternElements.size());
  }

  @Override
  public boolean matches(final ObjectGraphNode objectNode) {
    PatternElement patternElement = patternElements.get(0);
    return patternElement.isMatchedBy(objectNode);
  }

  @Override
  public void matchFound(final String fieldName) {
//    matchResult.revertBranchIfInvalidTo(currentIndex);
    matchResult.addMatched(fieldName);
  }

  public String getPatternString() {
    return Joiner.on("->").join(
        patternElements.stream().map(t -> t.getName()).toArray());
  }

  public String getBestMatchesString() {
    return matchResult.getBestBranchesString();
  }

  public static LinearMatchPattern forTypes(final Class<?>[] typePath) {
    return new LinearMatchPattern(
        Arrays.stream(typePath)
            .map(t -> new PatternTypeElement(t))
            .collect(toList()), MatchResult.createMatchResult());
  }

  @Override
  public String mismatchDescription() {
    return "the following dependency chain: "
      + lineSeparator()
      + getPatternString()
      + " but the closest matches were : "
        + lineSeparator()
        + getBestMatchesString();
  }
}


/*
public class LinearMatchPattern implements MatchPattern {
  private final MatchResult matchResult = new MatchResult();
  private List<PatternElement> patternElements;
  private int currentIndex = 0;

  public LinearMatchPattern(final List<PatternElement> patternElements) {
    this.patternElements = patternElements;
    matchResult.newBranchAttempt();
  }

  @Override
  public boolean isMatchedByAnyOf(final List<ObjectGraphNode> fieldNodes) {
    if(patternElements.size() == 0) {
      return true;
    }

    if(currentIndex >= patternElements.size()) {
      return true;
    }

    return fieldNodes
        .stream()
        .anyMatch(n -> n.matches(new LinearMatchPattern(patternElements.subList(1, patternElements.size() - 1))));
  }

  @Override
  public boolean matches(final ObjectGraphNode objectNode) {
    PatternElement patternElement = patternElements.get(currentIndex);
    return patternElement.isMatchedBy(objectNode);
  }

  @Override
  public void matchFound(final String fieldName) {
    matchResult.revertBranchIfInvalidTo(currentIndex);
    matchResult.addMatched(fieldName);
    currentIndex++;
  }

  public String getPatternString() {
    return Joiner.on("->").join(
        patternElements.stream().map(t -> t.getName()).toArray());
  }

  public String getBestMatchesString() {
    return matchResult.getBestBranchesString();
  }

  @Override
  public void rewindOneMatch() {
    currentIndex--;
    matchResult.invalidateCurrentBranch();
  }

  public static LinearMatchPattern forTypes(final Class<?>[] typePath) {
    return new LinearMatchPattern(
        Arrays.stream(typePath)
            .map(t -> new PatternTypeElement(t))
            .collect(toList()));
  }

  @Override
  public String mismatchDescription() {
    return "the following dependency chain: "
      + lineSeparator()
      + getPatternString()
      + " but the closest matches were : "
        + lineSeparator()
        + getBestMatchesString();
  }
}

 */