package com.github.grzesiek_galezowski.test_environment;

import org.assertj.core.api.Condition;

/**
 * Created by astral on 14.02.2016.
 */
public class XJAssertConditions {

  public static <T> Condition<T> like(T other) {
    return new AreAlikeCondition<T>(other);
  }

  public static <T> Condition<T> notLike(T other) {

    return new AreNotAlikeCondition<T>(other);
  }


}
