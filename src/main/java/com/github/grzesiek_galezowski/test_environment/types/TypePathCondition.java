package com.github.grzesiek_galezowski.test_environment.types;

import lombok.val;
import org.assertj.core.api.Condition;

import java.util.Arrays;

public class TypePathCondition<T> extends Condition<T> {

  private MatchPattern matchPattern;

  //todo finish from typePath to matchPattern
  public TypePathCondition(final MatchPattern matchPattern) {
    this.matchPattern = matchPattern;
  }

  @Override
  public boolean matches(final T value) {
    val typeNode = TypeGraphNodeFactory.create(value);
    if(!typeNode.matches(matchPattern)) {
      describeError();
      return false;
    }
    return true;
  }

  public void describeError() {
    describedAs("the following dependency chain: "
      + matchPattern.getPatternString()
      + " but the closest matches were : "
        + System.lineSeparator()
        + matchPattern.getBestMatchesString());
  }

  public static TypePathCondition typePath(
      final Class<?>... typePath) {
    return new TypePathCondition(
        LinearMatchPattern.forTypes(typePath));
  }

  static Condition<Object> valuePath(final Object... args) {
    return new TypePathCondition<>(new LinearMatchPattern(
        Arrays.stream(args).map(o -> new PatternValueElement(o)).toArray(PatternElement[]::new)));

  }
}
