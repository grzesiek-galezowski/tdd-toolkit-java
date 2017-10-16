package com.github.grzesiek_galezowski.test_environment.buffer.implementation;

import com.github.grzesiek_galezowski.test_environment.buffer.interfaces.MatchCountCondition;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by grzes on 10.07.2017.
 */
public class LambdaBasedExpectedMatchCount
    implements MatchCountCondition {
  private final Predicate<List<Boolean>> predicate;
  private String description;

  public LambdaBasedExpectedMatchCount(
      final Predicate<List<Boolean>> predicate,
      final String description) {
    this.predicate = predicate;
    this.description = description;
  }

  @Override
  public boolean matchFound(final List<Boolean> matchingResult) {
    return predicate.test(matchingResult);
  }

  @Override
  public String toString() {
    return description;
  }

  public static MatchCountCondition atLeast(final int times) {
    return new LambdaBasedExpectedMatchCount(m -> countAtLeast(times, m), "at least " + times + " item(s)");
  }

  public static MatchCountCondition exactly(final int times) {
    return new LambdaBasedExpectedMatchCount(m -> countEquals(times, m), "exactly " + times + " item(s)");
  }

  public static MatchCountCondition atLeastOne() {
    return atLeast(1);
  }

  public static MatchCountCondition exactlyOne() {
    return exactly(1);
  }

  public static boolean countEquals(final int times, final List<Boolean> m) {
    return getCount(m) == times;
  }

  public static boolean countAtLeast(final int times, final List<Boolean> m) {
    return getCount(m) >= times;
  }

  public static long getCount(final List<Boolean> m) {
    return m.stream().filter(i -> i.equals(true)).count();
  }

  public static MatchCountCondition no() {
    return exactly(0);
  }

}
