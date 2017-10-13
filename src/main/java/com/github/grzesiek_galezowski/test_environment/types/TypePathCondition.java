package com.github.grzesiek_galezowski.test_environment.types;

import lombok.val;
import org.assertj.core.api.Condition;

public class TypePathCondition<T> extends Condition<T> {

  private Class<?>[] typePath;
  private LinearMatchPattern matchPattern;

  //todo finish from typePath to matchPattern
  public TypePathCondition(final Class<?>[] typePath) {
    this.typePath = typePath;
    matchPattern = new LinearMatchPattern(typePath);
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
    describedAs("the following type path: "
      + matchPattern.getString()
      + " but the closest matches were : "
        + System.lineSeparator()
        + matchPattern.getMatchedAsString());
  }

  public static TypePathCondition typePath(
      final Class<?>... typePath) {
    return new TypePathCondition(typePath);
  }
}
