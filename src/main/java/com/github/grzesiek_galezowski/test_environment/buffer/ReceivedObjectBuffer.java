package com.github.grzesiek_galezowski.test_environment.buffer;

import com.github.grzesiek_galezowski.test_environment.buffer.implementation.BufferItemNotificationSubscribers;
import com.github.grzesiek_galezowski.test_environment.buffer.interfaces.*;

/**
 * Created by grzes on 09.07.2017.
 */
public interface ReceivedObjectBuffer<T>
    extends
    ObjectStorage<T>,
    Subscribable<T>,
    Checkable<T>,
    Pollable<T> {
  static <T> ReceivedObjectBuffer<T> observedBy(
      BufferObserver<T> observer) {
    return new SynchronizedReceivedObjectBuffer<>(
        new DefaultReceivedObjectBuffer<>(
            observer,
            new BufferItemNotificationSubscribers<>(observer)),
        observer);
  }

  static <T> ReceivedObjectBuffer<T> createDefault() {
    return observedBy(BufferObservers.none());
  }

}
