package com.github.grzesiek_galezowski.test_environment.buffer;

import lombok.val;
import org.assertj.core.api.Condition;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class BufferItemNotificationSubscribers<T> {
  private final BufferObserver<T> observer;
  private final List<Entry<ItemSubscriber<T>, Condition<T>>>
      subscriberEntries = new ArrayList<>();

  public BufferItemNotificationSubscribers(
      final BufferObserver<T> observer) {
    this.observer = observer;
  }

  public void notifySubscribersAbout(final T object) {
    for (val subscriberEntry : subscriberEntries) {
      val subscriber = subscriberEntry.getKey();
      val condition = subscriberEntry.getValue();
      try {
        if(condition.matches(object)) {
          subscriber.itemStored(object);
        }
      } catch (Throwable t) {
        observer.exceptionWhileNotifyingSubscriberAboutStoredItem(
            subscriber, object);
      }
    }
  }

  public void add(final ItemSubscriber<T> subscriber) {
    subscriberEntries.add(new SimpleImmutableEntry<>(
        subscriber, trueCondition()));
  }

  public void add(final ItemSubscriber<T> subscriber, final Condition<T> condition) {
    subscriberEntries.add(new SimpleImmutableEntry<>(
        subscriber, condition
    ));
  }

  public Condition<T> trueCondition() {
    return new Condition<>(t -> true, "");
  }
}