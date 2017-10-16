package com.github.grzesiek_galezowski.test_environment.buffer;

import lombok.val;
import org.assertj.core.api.Condition;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

/**
 * Created by grzes on 26.06.2017.
 */
public final class DefaultReceivedObjectBuffer<T> implements ReceivedObjectBuffer<T> {
  private final BufferItemNotificationSubscribers<T>
      subscribers;
  private List<T> receivedObjects = new ArrayList<>();
  private BufferObserver<T> observer;
  private SearchCommandFactory<T> searchCommandFactory;


  public DefaultReceivedObjectBuffer(final BufferObserver<T> observer, final BufferItemNotificationSubscribers<T> subscribers) {
    this.observer = observer;
    this.searchCommandFactory = new SearchCommandFactory<>(observer, receivedObjects);
    this.subscribers = subscribers;
  }

  @Override
  public void store(final T object) {
    receivedObjects.add(object);
    subscribers.notifySubscribersAbout(object);
  }

  @Override
  public void store(final T[] objects) {
    Arrays.stream(objects).forEach(item -> store(item));
  }

  @Override
  public void store(final Iterable<T> objects) {
    StreamSupport.stream(objects.spliterator(), false)
        .forEach(item -> store(item));
  }

  @Override
  public void assertContains(final ExpectedMatchCount expectedMatchCount, final Condition<T> condition) {
    val searchResult = blankResultFor(condition, expectedMatchCount);
    val searchCommand = searchCommandFactory.searchFor(condition, searchResult);
    searchCommand.performSearch();
    searchResult.assertFoundAccordingToSpecification();
  }

  @Override
  public boolean contains(final ExpectedMatchCount expectedMatchCount, final Condition<T> condition) {
    val searchResult = blankResultFor(condition, expectedMatchCount);
    val searchCommand = searchCommandFactory.searchFor(condition, searchResult);
    searchCommand.performSearch();
    return searchResult.foundAccordingToSpecification();
  }

  @Override
  public boolean isEmpty() {
    return receivedObjects.isEmpty();
  }

  @Override
  public void clearItems() {
    receivedObjects.clear();
  }

  @Override
  public void subscribeForItems(final ItemSubscriber<T> subscriber) {
    subscribers.add(subscriber);
  }

  @Override
  public void subscribeFor(final Condition<T> condition, final ItemSubscriber<T> subscriber) {
    subscribers.add(subscriber, condition);
  }

  //todo add toString that prints current buffer
  @Override
  public Poll<T> poll() {
    return Poll.on(this, observer);
  }

  @Override
  public Poll<T> pollFor(final Duration duration) {
    return Poll.on(this, duration, observer);
  }

  private SearchResult<T> blankResultFor(final Condition<T> expected, final ExpectedMatchCount expectedMatchCount) {
    return new SearchResult<T>(expectedMatchCount, expected, observer);
  }
}
