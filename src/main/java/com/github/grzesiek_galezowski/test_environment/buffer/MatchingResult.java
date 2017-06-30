package com.github.grzesiek_galezowski.test_environment.buffer;

import org.assertj.core.api.Condition;

import java.util.ArrayList;
import java.util.List;

public class MatchingResult {
  private final MatchSearchingStrategy matchSearchingStrategy = new MatchSearchingStrategy();
  private List<Boolean> matchingResult = new ArrayList<>();
  private final List<String> matchingDescriptions = new ArrayList<>();

  public static MatchingResult empty() {
    return new MatchingResult();
  }

  <T> void match(
      final List<T> receivedObjects,
      final Condition<T> expected) {

    for (T receivedObject : receivedObjects) {
      try {
        matchingResult.add(expected.matches(receivedObject));
        matchingDescriptions.add(expected.description().toString());
      } catch (Throwable t) {
        throw new ExceptionRaisedByConditionException(expected, t);
      }
    }
  }

  <T> void assertMatchFound(
      final Condition<T> expected) {
    if (!matchSearchingStrategy.matchFound(matchingResult)) {
      throw new ObjectNotFoundInBufferException(expected, matchingDescriptions, matchingResult);
    }
  }

}
