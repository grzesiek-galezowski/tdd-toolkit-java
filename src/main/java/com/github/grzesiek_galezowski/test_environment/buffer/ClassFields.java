package com.github.grzesiek_galezowski.test_environment.buffer;

import com.github.grzesiek_galezowski.test_environment.ErrorLog;
import com.github.grzesiek_galezowski.test_environment.SingleConditionResult;
import com.github.grzesiek_galezowski.test_environment.TypeTreeCondition;
import lombok.val;

import java.lang.reflect.Field;

public class ClassFields {
  private final Field[] value;
  private ErrorLog errorLog;

  public ClassFields(final Field[] value, final ErrorLog errorLog) {
    this.value = value;
    this.errorLog = errorLog;
  }

  public static ClassFields of(final Class clazz, final ErrorLog errorLog) {
    Field[] declaredFields = clazz.getDeclaredFields();

    for (val field : declaredFields) {
      field.setAccessible(true);
    }
    return new ClassFields(declaredFields, errorLog);
  }

  public <T> void searchForMatch(
      final T sourceObject,
      final TypeTreeCondition condition,
      final SingleConditionResult result) {
    for (val field : getValue()) {
      try {
        Object fieldValue = field.get(sourceObject);
        if (!condition.matches(fieldValue)) {
          result.conditionMismatch(errorLog, sourceObject, condition, field);
        } else {
          result.success();
          return;
        }
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public boolean dontExist() {
    return getValue().length == 0;
  }

  public Field[] getValue() {
    return value;
  }
}
