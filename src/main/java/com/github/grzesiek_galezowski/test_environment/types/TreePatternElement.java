package com.github.grzesiek_galezowski.test_environment.types;

public interface TreePatternElement extends MatchableByNode {

  String toStringUsing(NodePrinter nodePrinter, boolean matched, int nestingLevel);
}
