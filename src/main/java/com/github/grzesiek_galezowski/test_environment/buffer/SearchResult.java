package com.github.grzesiek_galezowski.test_environment.buffer;

import org.assertj.core.api.Condition;

import java.util.ArrayList;
import java.util.List;

public class SearchResult<T> {
  private final List<Boolean> matchingResult = new ArrayList<>();
  private final List<String> matchingDescriptions = new ArrayList<>();
  private final ExpectedMatchCount expectedMatchCount;
  private final Condition<T> condition;
  private final BufferObserver<T> observer;

  public SearchResult(
      final ExpectedMatchCount expectedMatchCount,
      final Condition<T> condition,
      final BufferObserver<T> observer) {

    this.expectedMatchCount = expectedMatchCount;
    this.condition = condition;
    this.observer = observer;
  }

  void add(final T receivedObject, final boolean isMatch, final String matchDescription) {
    matchingResult.add(isMatch);
    matchingDescriptions.add(matchDescription);
    if(isMatch) {
      observer.matchAttemptSuccessful(
          receivedObject,
          this.condition,
          matchDescription);
    } else {
      observer.matchAttemptFailed(
          receivedObject,
          this.condition,
          matchDescription
      );
    }
  }

  public void conclude() {
    this.observer.searchingFinished(this.matchingResult.stream().filter(c -> c).count());
  }

  public void assertFoundAccordingToSpecification() {
    if (!foundAccordingToSpecification()) {
      throw mismatchException();
    }
  }

  public MismatchException mismatchException() {
    return new MismatchException(
        this.condition, matchingDescriptions, matchingResult);
  }

  public boolean foundAccordingToSpecification() {
    return this.expectedMatchCount.matchFound(matchingResult);
  }
}