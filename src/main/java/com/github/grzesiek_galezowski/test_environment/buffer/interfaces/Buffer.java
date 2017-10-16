package com.github.grzesiek_galezowski.test_environment.buffer.interfaces;

public interface Buffer<T> extends
    Subscribable<T>,
    Checkable<T>,
    Pollable<T>,
    ObjectStorage<T> {
}
