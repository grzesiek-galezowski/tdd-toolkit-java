package com.github.grzesiek_galezowski.test_environment.buffer;

import java.util.List;

/**
 * Created by grzes on 09.07.2017.
 */
public interface ExpectedMatchCount {
  static ExpectedMatchCount atLeastOne() {
    return atLeast(1);
  }

  static ExpectedMatchCount atLeast(int times) {
    return m -> getCount(m) == times;
  }

  static ExpectedMatchCount exactlyOne() {
    return exactly(1);
  }

  static ExpectedMatchCount exactly(int times) {
    return m -> getCount(m) == times;
  }

  static long getCount(final List<Boolean> m) {
    return m.stream().filter(i -> i.equals(true)).count();
  }

  boolean matchFound(List<Boolean> matchingResult);
}
