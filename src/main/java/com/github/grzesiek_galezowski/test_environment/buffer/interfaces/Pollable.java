package com.github.grzesiek_galezowski.test_environment.buffer.interfaces;

import java.time.Duration;

public interface Pollable<T> {
  Poll<T> poll();

  Poll<T> pollFor(Duration duration);
}
