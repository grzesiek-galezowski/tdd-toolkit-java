package com.github.grzesiek_galezowski.test_environment;

import com.google.gson.Gson;
import org.assertj.core.api.Condition;

/**
 * Created by astral on 14.02.2016.
 */
public class AreNotAlikeCondition<T> extends Condition<T> {
  private static final Gson gson = new Gson();
  private final T other;

  public AreNotAlikeCondition(final T other) {
    this.other = other;
  }

  @Override
  public boolean matches(final T t) {
    String expected = gson.toJson(other);
    String actual = gson.toJson(t);
    describedAs("not like " + expected + " but was " + actual);
    return !expected.equals(actual);
  }


}
