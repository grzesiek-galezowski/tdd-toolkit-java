package com.github.grzesiek_galezowski.test_environment.types;

public interface NodePrinter {
  String asString(TreePattern node, TreePattern[] children);

  String asString(int nestingLevel, TreePattern node, TreePattern[] children);

  String typeString(Class<?> c, boolean matched, int i);

}
