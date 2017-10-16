package com.github.grzesiek_galezowski.test_environment.buffer.interfaces;

import org.assertj.core.api.Condition;

public interface Checkable<T> {
  boolean contains(MatchCountCondition matchCountCondition, Condition<T> condition);
  void assertContains(MatchCountCondition matchCountCondition, Condition<T> condition);
}
