package com.github.grzesiek_galezowski.test_environment.buffer.interfaces;

public interface ObjectStorage<T> {
  void store(T object);

  void store(T[] objects);

  void store(Iterable<T> objects);

  boolean isEmpty();

  void clearItems();
}
