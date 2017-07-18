package com.github.grzesiek_galezowski.test_environment;

import lombok.val;
import org.assertj.core.api.Condition;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Created by grzes on 17.07.2017.
 */
public class TypeTreeCondition<T> extends Condition<T> {
  private final Class clazz;
  private final Condition[] nested;

  public TypeTreeCondition(final Class clazz, final Condition[] nested) {

    this.clazz = clazz;
    this.nested = nested;
  }

  @Override
  public boolean matches(final T sourceObject) {
    if (clazz.equals(sourceObject.getClass())) {
      val message = new StringBuilder();
      for (val condition : nested) {
        boolean conditionMatchedAnyField = doesConditionMatchAnyField(
            sourceObject,
            condition,
            clazz,
            message
        );

        if (!conditionMatchedAnyField) {
          describedAs(message.toString());
          return false;
        }
      }
      return true;
    } else {
      describedAs(clazz + " is not equal to " + sourceObject.getClass());
      return false;
    }
  }

  public boolean doesConditionMatchAnyField(
      final T sourceObject,
      final Condition condition,
      final Class clazz, final StringBuilder message) {
    Field[] declaredFields = clazz.getDeclaredFields();

    boolean conditionMatchedAnyField = false;
    if(declaredFields.length == 0) {
      conditionMatchedAnyField = true;
    } else {
      for (val field : declaredFields) {
        try {
          field.setAccessible(true);
          Object fieldValue = field.get(sourceObject);
          if(condition.matches(fieldValue)) {
            conditionMatchedAnyField = true;
          } else {
            message.append(field.getName() + ": " + condition.description() + "\n");
          }
        } catch (IllegalAccessException e) {
          describedAs(e.toString());
          throw new RuntimeException(e);
        }
      }
    }
    return conditionMatchedAnyField;
  }

}
