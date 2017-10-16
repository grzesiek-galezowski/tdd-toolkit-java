package com.github.grzesiek_galezowski.test_environment.buffer.interfaces;

import org.assertj.core.api.Condition;

public interface Poll<T> {

  void awaiting(
      MatchCountCondition matchCountCondition,
      Condition<T> condition);

  void toEnsureThereIsNo(Condition<T> condition);
}
