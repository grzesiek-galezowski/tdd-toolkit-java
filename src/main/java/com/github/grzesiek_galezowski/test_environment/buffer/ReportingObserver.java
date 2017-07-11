package com.github.grzesiek_galezowski.test_environment.buffer;

import org.assertj.core.api.Condition;

import java.util.List;

/**
 * Created by grzes on 08.07.2017.
 */
public class ReportingObserver<T> implements BufferObserver<T> {
  @Override
  public void searchingStartedWithin(
      final List<T> receivedObjects,
      final Condition<T> condition) {
    System.out.println("Searching started among " + receivedObjects.size() + " received objects for " + condition);
  }

  @Override
  public void tryingToMatch(
      final T receivedObject,
      final Condition<T> condition) {
    System.out.println("Trying to match " + receivedObject + " to " + condition);
  }

  @Override
  public void matchAttemptSuccessful(
      final T receivedObject,
      final Condition<T> condition, final String matchDescription) {
    System.out.println("Match attempt successful: " + matchDescription);
  }

  @Override
  public void searchingFinishedWith(
      final ExceptionRaisedByConditionException exception,
      final T receivedObjectThatCausedException) {
    System.out.println("Match attempt failed with: " + exception);
  }

  @Override
  public void searchingFinished(
      final long matchesCount) {
    System.out.println("Search operation finished");
  }

  @Override
  public void matchAttemptFailed(
      final T receivedObject,
      final Condition<T> condition,
      final String matchDescription) {
    System.out.println("Match attempt failed: " + matchDescription);
  }

  @Override
  public void periodicPollingStarted() {
    System.out.println("Started periodic polling");
  }

  @Override
  public void singlePollStarted() {
    System.out.println("Single poll");
  }

  @Override
  public void singlePollFinishedWith(final boolean pollResult) {
    System.out.println("Single poll finished");
  }

  @Override
  public void exceptionWhileNotifyingSubscriberAboutStoredItem(
      final ItemSubscriber<T> subscriber, final T object) {
    System.out.println("subscriber notification caused exception");
  }
}
