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
        boolean result = item.equals(expected);
        describedAs(
            item.toString()
                + " is"
                + okOrNot(result)
                + " equal to expected "
                + expected.toString());
        return result;
      }

      public String okOrNot(final boolean result) {
        if (result) {
          return "";
        } else {
          return " not";
        }
      }

      @Override
      public String toString() {
        return "equal to " + expected;
      }
    };
  }
}
