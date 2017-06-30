package com.github.grzesiek_galezowski.test_environment.implementation_details;

import org.assertj.core.api.Condition;

/**
 * Created by grzes on 26.06.2017.
 */
public class FakeCondition {
  public static <T> Condition<T> failingWith(final String message) {
    return new Condition<T>() {
      @Override
      public boolean matches(final T item) {
        describedAs(message);
        return false;
      }

      @Override
      public String toString() {
        return "FalseCondition";
      }
    };
  }

  public static <T> Condition<T> alwaysTrue() {
    return new Condition<T>() {
      @Override
      public boolean matches(final T item) {
        return true;
      }

      @Override
      public String toString() {
        return "TrueCondition";
      }
    };
  }

  public static <T> Condition<T> throwing(final RuntimeException exception) {
    return new Condition<T>() {
      @Override
      public boolean matches(final T item) {
        throw exception;
      }

      @Override
      public String toString() {
        return "ThrowingCondition";
      }
    };

  }
}