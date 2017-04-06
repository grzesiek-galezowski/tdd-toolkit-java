package com.github.grzesiek_galezowski.test_environment.implementation_details.conditions;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.assertj.core.api.Condition;

/**
 * Created by grzes on 06.04.2017.
 */
public class EqualityImplementedCondition<T> extends Condition<Class<T>> {

  @Override
  public final boolean matches(final Class<T> t) {
    try {
      EqualsVerifier.forClass(t).verify();
      return true;
    } catch(final Throwable e) {
      describedAs(e.getMessage());
      return false;
    }
  }
}
