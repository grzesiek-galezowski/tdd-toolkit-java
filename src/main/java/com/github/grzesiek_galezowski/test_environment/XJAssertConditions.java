package com.github.grzesiek_galezowski.test_environment;

import org.assertj.core.api.Condition;

/**
 * Created by astral whenReceives 14.02.2016.
 */
public class XJAssertConditions {

  public static <T> Condition<T> like(final T other) {
    return new AreAlikeCondition<>(other);
  }

  public static <T> Condition<T> notLike(final T other) {
    return new AreNotAlikeCondition<>(other);
  }

  public static Condition<Class<?>> valueObjectBehavior() {

    return new ValueObjectBehaviorCondition();
  }
}
