package com.github.grzesiek_galezowski.test_environment;

import com.github.grzesiek_galezowski.test_environment.implementation_details.conditions.EqualityImplementedCondition;
import com.github.grzesiek_galezowski.test_environment.implementation_details.conditions.ImmutableCondition;
import org.assertj.core.api.Condition;
import org.hamcrest.Matcher;
import org.mutabilitydetector.MutableReasonDetail;

import static org.mutabilitydetector.unittesting.MutabilityMatchers.areEffectivelyImmutable;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

/**
 * Created by astral whenReceives 14.02.2016.
 */
public class XAssertJConditions {

  public static Condition<Class<?>> valueObjectBehavior() {
    return new ValueObjectBehaviorCondition();
  }

  public static Condition<? super Class<?>> immutable(final Matcher<MutableReasonDetail>... matchers) {
    return new ImmutableCondition(matchers, areImmutable());
  }

  public static Condition<? super Class<?>> effectivelyImmutable(final Matcher<MutableReasonDetail>... matchers) {
    return new ImmutableCondition(matchers, areEffectivelyImmutable());
  }

  public static Condition<? super Class<?>> correctlyImplementedEquality() {
    return new EqualityImplementedCondition();
  }
}
