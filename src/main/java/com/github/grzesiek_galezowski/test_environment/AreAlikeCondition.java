package com.github.grzesiek_galezowski.test_environment;

import com.google.gson.Gson;
import org.assertj.core.api.Condition;


/**
 * Created by astral on 14.02.2016.
 */
public class AreAlikeCondition<T> extends Condition<T> {
  private static final Gson gson = new Gson();
  private final T other;

  public AreAlikeCondition(T other) {
    this.other = other;
  }

  @Override
  public boolean matches(T t) {
    String expected = gson.toJson(other);
    String actual = gson.toJson(t);
    describedAs("like " + expected + " but was " + actual);
    return expected.equals(actual);
  }


}