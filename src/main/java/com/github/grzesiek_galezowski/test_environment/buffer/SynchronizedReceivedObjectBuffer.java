package com.github.grzesiek_galezowski.test_environment.buffer;

import lombok.Synchronized;
import org.assertj.core.api.Condition;

import java.time.Duration;

/**
 * Created by grzes on 09.07.2017.
 */
public class SynchronizedReceivedObjectBuffer<T> implements ReceivedObjectBuffer<T> {
  private ReceivedObjectBuffer<T> innerBuffer;
  private BufferObserver observer;

  public SynchronizedReceivedObjectBuffer(
      final ReceivedObjectBuffer<T> innerBuffer,
      final BufferObserver observer) {
    this.innerBuffer = innerBuffer;
    this.observer = observer;
  }

  @Override
  @Synchronized
  public void store(final T object) {
    innerBuffer.store(object);
  }

  @Override
  @Synchronized
  public void store(final T[] objects) {
    innerBuffer.store(objects);
  }

  @Override
  @Synchronized
  public void store(final Iterable<T> objects) {
    innerBuffer.store(objects);
  }

  @Override
  @Synchronized()
  public void assertContains(final ExpectedMatchCount expectedMatchCount, final Condition<T> condition) {
    innerBuffer.assertContains(expectedMatchCount, condition);
  }

  @Override
  @Synchronized
  public boolean contains(final ExpectedMatchCount expectedMatchCount, final Condition<T> condition) {
    return innerBuffer.contains(expectedMatchCount, condition);
  }

  @Override
  @Synchronized
  public boolean isEmpty() {
    return innerBuffer.isEmpty();
  }

  @Override
  @Synchronized
  public void clearItems() {
    innerBuffer.clearItems();
  }

  @Override
  @Synchronized
  public void subscribeForItems(final ItemSubscriber<T> subscriber) {
    innerBuffer.subscribeForItems(subscriber);
  }

  @Override
  @Synchronized
  public void subscribeFor(final Condition<T> condition, final ItemSubscriber<T> subscriber) {
    innerBuffer.subscribeFor(condition, subscriber);
  }

  @Override
  @Synchronized
  public Poll<T> poll() {
    return Poll.on(this, observer);
  }

  @Override
  @Synchronized
  public Poll<T> pollFor(final Duration duration) {
    return Poll.on(this, duration, observer);
  }
}
