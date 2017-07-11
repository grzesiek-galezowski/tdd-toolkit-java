package com.github.grzesiek_galezowski.test_environment.buffer;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by grzes on 10.07.2017.
 */
public class LambdaBasedExpectedMatchCount implements ExpectedMatchCount {
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
}
