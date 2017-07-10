package com.github.grzesiek_galezowski.test_environment.buffer;

import org.assertj.core.api.Condition;

import java.time.Duration;

/**
 * Created by grzes on 09.07.2017.
 */
public interface ReceivedObjectBuffer<T> {
  //todo assert has none
  //todo assert has exactly X matches
  static <T> ReceivedObjectBuffer<T> observedBy(
      BufferObserver observer) {
    return new SynchronizedReceivedObjectBuffer<>(
        new DefaultReceivedObjectBuffer<T>(observer), observer);
  }

  static <T> ReceivedObjectBuffer<T> createDefault() {
    return observedBy(BufferObserver.none());
  }

  Poll<T> poll();

  Poll<T> poll(Duration duration);

  void store(T object);

  void assertHasAtLeastOne(Condition<T> condition);

  void assertHas(ExpectedMatchCount expectedMatchCount, Condition<T> condition);

  boolean has(ExpectedMatchCount expectedMatchCount, Condition<T> condition);

  boolean isEmpty();
}
