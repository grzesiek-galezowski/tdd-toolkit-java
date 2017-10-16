package com.github.grzesiek_galezowski.test_environment.buffer.interfaces;

/**
 * Created by grzes on 11.07.2017.
 */
public interface ItemSubscriber<T> {
  void itemStored(T value);
}
