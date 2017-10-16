package com.github.grzesiek_galezowski.test_environment.buffer.interfaces;

import org.assertj.core.api.Condition;

public interface Subscribable<T> {
  void subscribeForItems(ItemSubscriber<T> subscriber);

  void subscribeFor(Condition<T> condition, ItemSubscriber<T> subscriber);
}
