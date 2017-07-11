package com.github.grzesiek_galezowski.test_environment.buffer;

import org.assertj.core.api.Condition;

import java.util.List;

/**
 * Created by grzes on 08.07.2017.
 */
public class NullObserver<T> implements BufferObserver<T> {

  @Override
  public void searchingStartedWithin(
      final List<T> receivedObjects,
      final Condition<T> condition) {

  }

  @Override
  public void tryingToMatch(
      final T receivedObject,
      final Condition<T> condition) {

  }

  @Override
  public void matchAttemptSuccessful(
      final T receivedObject,
      final Condition<T> condition,
      final String matchDescription) {

  }

  @Override
  public void searchingFinishedWith(
      final ExceptionRaisedByConditionException exception,
      final T receivedObjectThatCausedException) {

  }

  @Override
  public void searchingFinished(
      final long matchesCount) {

  }

  @Override
  public void matchAttemptFailed(final T receivedObject, final Condition<T> condition, final String matchDescription) {

  }

  @Override
  public void periodicPollingStarted() {

  }

  @Override
  public void singlePollStarted() {

  }

  @Override
  public void singlePollFinishedWith(final boolean pollResult) {

  }

  @Override
  public void exceptionWhileNotifyingSubscriberAboutStoredItem(final ItemSubscriber<T> subscriber, final T object) {

  }
}
