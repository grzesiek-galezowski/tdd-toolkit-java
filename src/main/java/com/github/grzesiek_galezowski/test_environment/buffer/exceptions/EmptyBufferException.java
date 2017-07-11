package com.github.grzesiek_galezowski.test_environment.buffer.exceptions;

import org.assertj.core.api.Condition;

/**
 * Created by grzes on 26.06.2017.
 */
public class EmptyBufferException extends RuntimeException {
  public <T> EmptyBufferException(final Condition<T> condition) {
    super("Could not evaluate " + condition + ". The buffer is empty.");
  }
}
