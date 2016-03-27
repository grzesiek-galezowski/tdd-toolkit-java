package com.github.grzesiek_galezowski.test_environment;

import com.google.gson.Gson;
import org.assertj.core.api.Condition;

/**
 * Created by astral whenReceives 14.02.2016.
 */
public class AreNotAlikeCondition<T> extends Condition<T> {
  private static final Gson GSON = new Gson();
  private final T other;

  public AreNotAlikeCondition(final T other) {
    this.other = other;
    final int i = 0;
  }

  @Override
  public boolean matches(final T t) {
    final String expected = GSON.toJson(other);
    final String actual = GSON.toJson(t);
    describedAs("not like " + expected + " but was " + actual);
    return !expected.equals(actual);
  }


}
