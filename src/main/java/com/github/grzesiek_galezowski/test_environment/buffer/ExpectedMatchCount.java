package com.github.grzesiek_galezowski.test_environment.buffer;

import java.util.List;

/**
 * Created by grzes on 09.07.2017.
 */
public interface ExpectedMatchCount  {
  static ExpectedMatchCount atLeast(int times) {
    return new LambdaBasedExpectedMatchCount(m -> countAtLeast(times, m), "at least " + times + " item(s)");
  }

  static ExpectedMatchCount exactly(int times) {
    return new LambdaBasedExpectedMatchCount(m -> countEquals(times, m), "exactly " + times + " item(s)");
  }

  static ExpectedMatchCount atLeastOne() {
    return atLeast(1);
  }

  static ExpectedMatchCount exactlyOne() {
    return exactly(1);
  }

  static boolean countEquals(final int times, final List<Boolean> m) {
    return getCount(m) == times;
  }

  static boolean countAtLeast(int times, List<Boolean> m) {
    return getCount(m) >= times;
  }

  static long getCount(final List<Boolean> m) {
    return m.stream().filter(i -> i.equals(true)).count();
  }

  static ExpectedMatchCount no() {
    return exactly(0);
  }

  boolean matchFound(List<Boolean> matchingResult);
}

