package com.github.grzesiek_galezowski.test_environment.buffer.interfaces;


import org.assertj.core.api.Condition;

/**
 * Created by grzes on 26.06.2017.
 */
public class ExceptionRaisedByConditionException extends RuntimeException {

  public <T> ExceptionRaisedByConditionException(
      final Condition<T> condition,
      final Throwable exception) {
    super(condition.toString()
        + " raised an exception for one of the items: "
        + exception.toString()
    );
  }
}
