package com.github.grzesiek_galezowski.test_environment.buffer;

import org.assertj.core.api.Condition;

import java.util.List;

/**
 * Created by grzes on 08.07.2017.
 */
public interface BufferObserver<T> {
  static <T> BufferObserver<T> createDefault() {
    return new ReportingObserver<T>();
  }

  static <T> BufferObserver<T> none() {
    return new NullObserver<T>();
  }

  void searchingStartedWithin(List<T> receivedObjects, Condition<T> condition);

  void tryingToMatch(T receivedObject, Condition<T> condition);

  void matchAttemptSuccessful(
      T receivedObject,
      Condition<T> condition,
      String matchDescription);

  void searchingFinishedWith(
      ExceptionRaisedByConditionException exception,
      T receivedObjectThatCausedException);

  void searchingFinished(long matchesCount);

  void matchAttemptFailed(
      T receivedObject,
      Condition<T> condition,
      String matchDescription);

  void periodicPollingStarted();

  void singlePollStarted();

  void singlePollFinishedWith(boolean pollResult);
}
