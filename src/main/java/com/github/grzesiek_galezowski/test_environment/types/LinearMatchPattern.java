package com.github.grzesiek_galezowski.test_environment.types;

import com.google.common.base.Joiner;
import org.assertj.core.util.Lists;

import java.util.Arrays;
import java.util.List;

public class LinearMatchPattern {
  private Class<?>[] typePath;
  private int currentIndex = 0;
  private List<String> currentMatchedFields = Lists.newArrayList();
  private List<List<String>> matchPaths = Lists.newArrayList();
  private boolean reverted = false;

  public LinearMatchPattern(final Class<?>[] typePath) {
    this.typePath = typePath;
    matchPaths.add(currentMatchedFields);
  }

  boolean isMatchedByAnyOf(
      final List<TypeGraphNode> fieldNodes) {

    return fieldNodes
        .stream()
        .anyMatch(n -> n.matches(this));
  }

  public boolean nextItemIs(final Class<?> type) {
    return type.equals(typePath[currentIndex]);
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
    System.out.println("current index is " + currentIndex);
    return currentIndex == typePath.length;
  }

  public String getString() {
    return Joiner.on("->").join(Arrays.stream(typePath).map(t -> t.getSimpleName()).toArray());
  }

  public String getMatchedAsString() {
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
}
