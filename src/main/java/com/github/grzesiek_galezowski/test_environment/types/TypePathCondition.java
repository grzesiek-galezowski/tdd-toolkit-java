package com.github.grzesiek_galezowski.test_environment.types;

import lombok.val;
import org.assertj.core.api.Condition;

import java.util.Arrays;

import static java.util.stream.Collectors.toList;

public class TypePathCondition<T> extends Condition<T> {

  private MatchPattern matchPattern;

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
    describedAs(matchPattern.mismatchDescription());
  }

  public static <T> Condition<T> subtree(final TreePattern patternElement) {
    return new TypePathCondition<>(new SubtreeMatchPatternAdapter(patternElement));
  }

  public static TypePathCondition typePath(
      final Class<?>... typePath) {
    return new TypePathCondition(
        LinearMatchPattern.forTypes(typePath));
  }

  public static Condition<Object> valuePath(final Object... args) {
    return new TypePathCondition<>(new LinearMatchPattern(
        Arrays.stream(args).map(o -> new PatternValueElement(o))
            .collect(toList()), MatchResult.createMatchResult()));

  }
}
