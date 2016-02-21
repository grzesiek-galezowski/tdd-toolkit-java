package com.github.grzesiek_galezowski.test_environment;

import org.assertj.core.api.Condition;

/**
 * Created by astral on 22.02.2016.
 */
public class ValueObjectBehaviorCondition<T extends Class<?>> extends Condition<T> {

  @Override
  public boolean matches(final T clazz) {
    try {
      XAssert.assertValueObject((Class<?>) clazz);
    } catch (Throwable e) {
      describedAs(e.toString());
      return false;
    }
    return true;
  }
}
