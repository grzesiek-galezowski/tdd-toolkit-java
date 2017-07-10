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
  public void assertHasAtLeastOne(final Condition<T> condition) {
    innerBuffer.assertHasAtLeastOne(condition);
  }

  @Override
  @Synchronized()
  public void assertHas(final ExpectedMatchCount expectedMatchCount, final Condition<T> condition) {
    innerBuffer.assertHas(expectedMatchCount, condition);
  }

  @Override
  @Synchronized
  public boolean has(final ExpectedMatchCount expectedMatchCount, final Condition<T> condition) {
    return innerBuffer.has(expectedMatchCount, condition);
  }

  @Override
  @Synchronized
  public boolean isEmpty() {
    return innerBuffer.isEmpty();
  }

  @Override
  @Synchronized
  public Poll<T> poll() {
    return Poll.on(this, observer);
  }

  @Override
  @Synchronized
  public Poll<T> poll(final Duration duration) {
    return Poll.on(this, duration, observer);
  }
}
