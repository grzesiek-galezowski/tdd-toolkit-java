package com.github.grzesiek_galezowski.test_environment.buffer.interfaces;

import org.assertj.core.api.Condition;

import java.util.List;

/**
 * Created by grzes on 08.07.2017.
 */
public interface BufferObserver<T> {

  void searchingStartedWithin(List<T> receivedObjects, Condition<T> condition);

  void tryingToMatch(T receivedObject, Condition<T> condition);

  void matchAttemptSuccessful(
      T receivedObject,
      Condition<T> condition,
      String matchDescription);

  void searchingFinishedWith(
      Throwable exception,
      T receivedObjectThatCausedException);

  void searchingFinished(long matchesCount);

  void matchAttemptFailed(
      T receivedObject,
      Condition<T> condition,
      String matchDescription);

  void periodicPollingStarted();

  void singlePollStarted();

  void singlePollFinishedWith(boolean pollResult);

  void exceptionWhileNotifyingSubscriberAboutStoredItem(ItemSubscriber<T> subscriber, T object);
}
