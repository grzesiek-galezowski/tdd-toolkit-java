package com.github.grzesiek_galezowski.test_environment.buffer;

import org.assertj.core.api.Condition;

import java.util.List;

/**
 * Created by grzes on 26.06.2017.
 */
public class MismatchException extends RuntimeException {

  public <T> MismatchException(
      final Condition<T> condition,
      final List<String> matchingDescriptions,
      final List<Boolean> matchingResult, final ExpectedMatchCount expectedMatchCount) {
    super(new MessageFormat<T>().getMessage(condition, matchingDescriptions, matchingResult, expectedMatchCount));
  }


}
