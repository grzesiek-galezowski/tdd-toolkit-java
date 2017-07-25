package com.github.grzesiek_galezowski.test_environment.buffer;

import org.assertj.core.api.Condition;

import java.time.Duration;

/**
 * Created by grzes on 09.07.2017.
 */
public interface ReceivedObjectBuffer<T> {
  static <T> ReceivedObjectBuffer<T> observedBy(
      BufferObserver<T> observer) {
    return new SynchronizedReceivedObjectBuffer<>(
        new DefaultReceivedObjectBuffer<>(
            observer,
            new BufferItemNotificationSubscribers<>(observer)),
        observer);
  }

  static <T> ReceivedObjectBuffer<T> createDefault() {
    return observedBy(BufferObserver.none());
  }

  void store(T object);
  void store(T[] objects);
  void store(Iterable<T> objects);

  boolean isEmpty();

  void clearItems();

  boolean contains(ExpectedMatchCount expectedMatchCount, Condition<T> condition);

  void assertContains(ExpectedMatchCount expectedMatchCount, Condition<T> condition);

  Poll<T> poll();

  Poll<T> pollFor(Duration duration);

  void subscribeForItems(ItemSubscriber<T> subscriber);

  void subscribeFor(Condition<T> condition, ItemSubscriber<T> subscriber);
}
