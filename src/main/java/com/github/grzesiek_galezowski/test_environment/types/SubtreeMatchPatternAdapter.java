package com.github.grzesiek_galezowski.test_environment.types;

import java.util.List;

public class SubtreeMatchPatternAdapter implements MatchPattern {
  private TreePattern tree;

  public SubtreeMatchPatternAdapter(final TreePattern root) {
    this.tree = root;
  }

  @Override
  public boolean matches(final ObjectGraphNode objectNode) {
    return tree.isMatchedBy(objectNode);
  }

  @Override
  public void matchFound(final String fieldName) {
    tree.markAsMatched();
    tree.markAsVisited();
  }

  @Override
  public boolean isMatchedByAnyOf(final List<ObjectGraphNode> childNodes) {
    return tree.isMatchedByAnyOf(childNodes);
  }

  @Override
  public String mismatchDescription() {
    return tree.asString();
  }
}
