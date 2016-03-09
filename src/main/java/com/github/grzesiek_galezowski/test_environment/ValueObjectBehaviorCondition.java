package com.github.grzesiek_galezowski.test_environment;

import org.assertj.core.api.Condition;

/**
 * Created by astral on 22.02.2016.
 */
public class ValueObjectBehaviorCondition extends Condition<Class<?>> {

  @Override
  public boolean matches(final Class<?> clazz) {
    try {
      XAssert.assertValueObject(clazz);
    } catch (final Throwable e) {
      describedAs(e.toString());
      return false;
    }
    return true;
  }
}
