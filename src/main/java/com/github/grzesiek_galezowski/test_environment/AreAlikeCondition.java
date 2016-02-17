package com.github.grzesiek_galezowski.test_environment;

import com.google.gson.Gson;
import org.assertj.core.api.Condition;


/**
 * Created by astral on 14.02.2016.
 */
public class AreAlikeCondition<T> extends Condition<T> {
  private static final Gson GSON = new Gson();
  private final T other;

  public AreAlikeCondition(final T other) {
    this.other = other;
  }

  @Override
  public boolean matches(final T t) {
    String expected = GSON.toJson(other);
    String actual = GSON.toJson(t);
    describedAs("like " + expected + " but was " + actual);
    return expected.equals(actual);
  }


}
