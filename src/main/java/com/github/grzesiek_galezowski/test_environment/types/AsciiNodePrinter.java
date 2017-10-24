package com.github.grzesiek_galezowski.test_environment.types;

import com.google.common.base.Joiner;

import java.util.Arrays;

import static com.google.common.base.Strings.repeat;
import static java.lang.System.lineSeparator;

public class AsciiNodePrinter implements NodePrinter {

  @Override
  public String asString(final int nestingLevel, final TreePattern node, final TreePattern[] children) {
    String result = node.currentNodeString(this, nestingLevel);
    if (children.length > 0) {
      result += lineSeparator();
    }
    result += Joiner.on(lineSeparator())
        .join(
            Arrays.stream(children)
                .map(d -> d.subtreeString(this, nestingLevel))
                .toArray());
    return result;
  }

  @Override
  public String typeString(final Class<?> c, final boolean matched, final int i) {
    return repeat("   ", i) + "|->" + c.getSimpleName() + AsciiNodePrinter.matchMark(matched);
  }

  @Override
  public String asString(final TreePattern node, final TreePattern[] children) {
    return lineSeparator() + asString(0, node, children);
  }

  private static String matchMark(final boolean matched) {
    if (matched) {
      return "*";
    } else {
      return "";
    }
  }
}
