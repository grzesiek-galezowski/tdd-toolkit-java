package com.github.grzesiek_galezowski.test_environment.types;

import com.google.common.base.Joiner;
import lombok.val;
import org.assertj.core.api.Condition;

import java.util.Arrays;

public class TypePathCondition<T> extends Condition<T> {

  private Class<?>[] typePath;
  private MatchPattern matchPattern;

  //todo finish from typePath to matchPattern
  public TypePathCondition(final Class<?>[] typePath) {
    this.typePath = typePath;
    matchPattern = new MatchPattern(typePath);
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
    describedAs("the folowing type path: "
      + matchPattern.getString()
      + " but the closest matches were : " + matchPattern.getMatchedAsString());
  }

  public static <T> TypePathCondition typePath(
      final Class<?>... typePath) {
    return new TypePathCondition(typePath);
  }
}
