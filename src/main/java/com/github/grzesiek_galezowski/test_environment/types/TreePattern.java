package com.github.grzesiek_galezowski.test_environment.types;

import lombok.val;

import java.util.Arrays;
import java.util.List;

import static java.lang.System.out;

public class TreePattern implements MatchableByNode {
  private final TreePatternElement patternElement;
  private TreePattern[] dependencies;
  private boolean matched = false;
  private boolean visited = false;
  private NodePrinter nodePrinter;

  public TreePattern(
      final NodePrinter nodePrinter,
      final TreePatternElement patternElement,
      final TreePattern... dependencies) {
    this.dependencies = dependencies;
    this.nodePrinter = nodePrinter;
    this.patternElement = patternElement;
  }

  public static TreePattern type(
      final Class<?> c,
      final TreePattern... dependencies) {
    return new TreePattern(
        new AsciiNodePrinter(),
        new TypeTreePatternElement(c),
        dependencies);
  }

  public static TreePattern object(final Object expected, final TreePattern... dependencies) {
    return new TreePattern(
        new AsciiNodePrinter(),
        new ValueTreePatternElement(expected),
        dependencies);
  }

  public boolean isMatchedByAnyOf(final List<ObjectGraphNode> childNodes) {
    System.out.println("Current children size: " + children().length);
    for(val child : children()) {
      val matches =
          childNodes.stream().anyMatch(
              actual -> actual.matches(new SubtreeMatchPatternAdapter(child)));
      if(!matches) {
        return false;
      }
    }
    return true;
  }

  public boolean isMatchedCompletely() {
    return hasReachedMatchesCountOf(treeElementsCount());
  }

  public String subtreeString(final NodePrinter nodePrinter, final int i) {
    return nodePrinter.asString(i+1, this, dependencies);
  }

  @Override
  public boolean isMatchedBy(final ObjectGraphNode objectNode) {
    return patternElement.isMatchedBy(objectNode);
  }

  public void markAsMatched() {
    matched = true;
  }

  public void markAsVisited() {
    visited = true;
  }

  private int treeElementsCount() {
    return 1 + Arrays.stream(dependencies).mapToInt(d -> d.treeElementsCount()).sum();
  }

  private boolean hasReachedMatchesCountOf(final int numberOfElementsToMatch) {
    int matchedNodesCount = matchedNodeCount();
    out.println("matched nodes: " + matchedNodesCount + ", numberOfElementsToMatch: " + numberOfElementsToMatch);
    return matchedNodesCount == numberOfElementsToMatch;
  }

  private int matchedNodeCount() {
    if(matched) {
      return 1 + Arrays.stream(children()).mapToInt(c -> c.matchedNodeCount()).sum();
    } else {
      return 0;
    }

  }

  public TreePattern[] children() {
    return dependencies;
  }

  public String asString() {
    return nodePrinter.asString(this, this.dependencies);
  }

  public String currentNodeString(final NodePrinter nodePrinter, final int nestingLevel) {
    return patternElement.toStringUsing(nodePrinter, matched, nestingLevel);
  }

}
