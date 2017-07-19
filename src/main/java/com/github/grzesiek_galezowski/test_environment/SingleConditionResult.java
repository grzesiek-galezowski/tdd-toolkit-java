package com.github.grzesiek_galezowski.test_environment;

import java.lang.reflect.Field;

public class SingleConditionResult {
  private boolean isMatch = false;
  private StringBuilder content = new StringBuilder();

  public <T> void conditionMismatch(final ErrorLog errorLog, final T sourceObject, final TypeTreeCondition condition, final Field field) {
    content.append(
        "\nfield " + sourceObject.getClass().getSimpleName() + "." + field.getName() + ": " + condition.reasonOfFailure() + "\n");
  }

  public boolean isMismatch() {
    return !this.isMatch;
  }

  public void success() {
    isMatch = true;
  }

  public void addTo(final ErrorLog errorLog) {
    errorLog.append(content);
  }

}
