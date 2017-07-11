package com.github.grzesiek_galezowski.test_environment.buffer;

import lombok.val;

import java.util.ArrayList;
import java.util.List;

public class BufferItemNotificationSubscribers<T> {
  private final BufferObserver<T> observer;
  private final List<ItemSubscriber<T>> subscribers = new ArrayList<>();

  public BufferItemNotificationSubscribers(
      final BufferObserver<T> observer) {
    this.observer = observer;
  }

  public void notifySubscribersAbout(final T object) {
    for (val subscriber : subscribers) {
      try {
        subscriber.itemStored(object);
      } catch (Throwable t) {
        observer.exceptionWhileNotifyingSubscriberAboutStoredItem(
            subscriber, object);
      }
    }
  }

  public void add(final ItemSubscriber<T> subscriber) {
    subscribers.add(subscriber);
  }
}