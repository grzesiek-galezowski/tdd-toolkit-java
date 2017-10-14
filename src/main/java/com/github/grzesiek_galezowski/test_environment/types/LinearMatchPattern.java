package com.github.grzesiek_galezowski.test_environment.types;

import com.google.common.base.Joiner;
import org.assertj.core.util.Lists;

import java.util.Arrays;
import java.util.List;

public class LinearMatchPattern {
  private PatternElement[] patternElements;
  private int currentIndex = 0;
  private List<String> currentMatchedFields = Lists.newArrayList();
  private List<List<String>> matchPaths = Lists.newArrayList();
  private boolean reverted = false;

  public  LinearMatchPattern(final PatternElement[] patternElements) {
    this.patternElements = patternElements;
    matchPaths.add(currentMatchedFields);
  }

  boolean isMatchedByAnyOf(
      final List<TypeGraphNode> fieldNodes) {

    return fieldNodes
        .stream()
        .anyMatch(n -> n.matches(this));
  }

  public boolean matches(final TypeNode typeNode) {
    PatternElement patternElement = patternElements[currentIndex];
    return patternElement.isMatchedBy(typeNode);
  }

  public void matchFound(final String fieldName) {
    if(reverted) {
      revertMatchesTo(currentIndex);
      reverted = false;
    }
    currentMatchedFields.add(fieldName);
    currentIndex++;
  }

  public void revertMatchesTo(final int currentIndex) {
    currentMatchedFields = Lists.newArrayList(currentMatchedFields.subList(0, currentIndex));
    matchPaths.add(currentMatchedFields);
  }

  public boolean isMatchedCompletely() {
    return currentIndex == patternElements.length;
  }

  public String getPatternString() {
    return Joiner.on("->").join(Arrays.stream(patternElements).map(t -> t.getName()).toArray());
  }

  public String getBestMatchesString() {
    List<String> strings = Lists.newArrayList();

    for(int i = 0; i < matchPaths.size(); i++) {
      strings.add(" " + (i+1) + ". " + Joiner.on("->").join(matchPaths.get(i)));
    }
    return Joiner.on(System.lineSeparator()).join(strings);

  }

  public void revertOneMatch() {
    currentIndex--;
    reverted = true;
  }

  public static LinearMatchPattern forTypes(final Class<?>[] typePath) {
    return new LinearMatchPattern(Arrays.stream(typePath).map(t -> new PatternTypeElement(t)).toArray(PatternElement[]::new));
  }
}
