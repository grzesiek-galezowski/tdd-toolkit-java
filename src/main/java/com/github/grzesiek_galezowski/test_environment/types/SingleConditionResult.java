package com.github.grzesiek_galezowski.test_environment.types;

public class SingleConditionResult {
  private boolean isMatch = false;

  public SingleConditionResult() {
  }

  public static SingleConditionResult of() {
    return new SingleConditionResult();
  }

  public boolean isMismatch() {
    return !this.isMatch;
  }

  public void success() {
    isMatch = true;
  }

}
