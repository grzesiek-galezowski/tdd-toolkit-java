package com.github.grzesiek_galezowski.test_environment.types;

import lombok.val;

import java.util.List;

public class TreePattern implements MatchableByNode {
  private final TreePatternElement patternElement;
  private TreePattern[] dependencies;
  private boolean matched = false;
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
