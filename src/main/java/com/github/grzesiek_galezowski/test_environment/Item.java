package com.github.grzesiek_galezowski.test_environment;

import org.assertj.core.api.Condition;

/**
 * Created by grzes on 26.06.2017.
 */
public class Item {
  public static <T> Condition<T> equalTo(final T expected) {
    return new Condition<T>() {
      @Override
      public boolean matches(final T item) {
        return item.equals(expected);
      }
    };
  }
}
