package com.github.grzesiek_galezowski.test_environment.buffer;

import com.github.grzesiek_galezowski.test_environment.types.BreadCrumbs;
import com.github.grzesiek_galezowski.test_environment.types.ErrorLog;
import com.github.grzesiek_galezowski.test_environment.types.SingleConditionResult;
import com.github.grzesiek_galezowski.test_environment.types.TypeTreeCondition;
import lombok.val;

import java.lang.reflect.Field;

public class ClassFields {
  private final Field[] value;

  public ClassFields(final Field[] value) {
    this.value = value;
  }

  public static ClassFields of(final Class clazz) {
    Field[] declaredFields = clazz.getDeclaredFields();

    for (val field : declaredFields) {
      field.setAccessible(true);
    }
    return new ClassFields(declaredFields);
  }

  public <T> void searchForMatch(
      final T sourceObject,
      final TypeTreeCondition condition,
      final SingleConditionResult result,
      final ErrorLog errorLog,
      final BreadCrumbs breadCrumbs) {
    for (val field : getValue()) {
      try {
        Object fieldValue = field.get(sourceObject);
        if (condition.matches(fieldValue, breadCrumbs.add(sourceObject.getClass()), errorLog)) {
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
