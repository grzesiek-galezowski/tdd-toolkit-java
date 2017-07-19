package com.github.grzesiek_galezowski.test_environment;

import lombok.val;
import org.assertj.core.api.Condition;

import java.lang.reflect.Field;

/**
 * Created by grzes on 17.07.2017.
 */

//Tests required:
  //1. expected field of type, but no fields
  //2. expected field of type, but not found
  //3. trail
  //4. multiple mismatches
  //5. exception throughout processing
  //...
public class TypeTreeCondition<T> extends Condition<T> {
  private final Class clazz;
  private final TypeTreeCondition<Object>[] nestedConditions;
  private StringBuilder errorLog = new StringBuilder();

  public TypeTreeCondition(final Class clazz, final TypeTreeCondition<Object>[] nestedConditions) {
    this.clazz = clazz;
    this.nestedConditions = nestedConditions;
    describedAs(clazz.toString());
  }

  @Override
  public boolean matches(final T sourceObject) {

    if(sourceObject == null) {
      describedAs("is set to null, which has no type");
      return false;
    }

    if (clazz.equals(sourceObject.getClass())) {
      for (val condition : nestedConditions) {
        boolean conditionMatchedAnyField = doesConditionMatchAnyField(
            sourceObject,
            Clone.of(condition),
            clazz,
            errorLog
        );

        if (!conditionMatchedAnyField) {
          return false;
        }
      }
      return true;
    } else {
      errorLog.append(clazz.getSimpleName() + " expected but got " + sourceObject.getClass().getSimpleName());
      return false;
    }
  }

  public boolean doesConditionMatchAnyField(
      final T sourceObject,
      final TypeTreeCondition condition,
      final Class clazz, final StringBuilder message) {
    Field[] declaredFields = clazz.getDeclaredFields();

    boolean conditionMatchedAnyField = false;
    if(declaredFields.length == 0) {
      message.append("the class contains zero fields");
      return false;
    } else {
      for (val field : declaredFields) {
        try {
          field.setAccessible(true);
          Object fieldValue = field.get(sourceObject);
          if(condition.matches(fieldValue)) {
            conditionMatchedAnyField = true;
          } else {
            this.errorLog.append(
                "\nfield " + sourceObject.getClass().getSimpleName() + "." + field.getName() + ": " + condition.reasonOfFailure() + "\n");
          }
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      }
    }
    return conditionMatchedAnyField;
  }

  private String reasonOfFailure() {
    return errorLog.toString();
  }

  @Override
  public String toString() {
    return getExpectedTypeSubtree(1)
        + "\nBut could not find matches.\n"
        + "Tried the following matches before failing:\n"
        + reasonOfFailure();
  }

  public String getExpectedTypeSubtree(final int indent) {
    String result = clazz.getSimpleName();
    if(nestedConditions.length > 0) {
      result += " with: (\n";
      for (val condition : nestedConditions) {
        result += spaces(indent) + condition.getExpectedTypeSubtree(indent +1);
      }
      result += spaces(indent -1) + ")";
    }

    result += ",\n";

    return result;
  }

  private String spaces(final int indent) {
    String result = "";
    for(int i = 0; i < indent; ++i) {
      result += "  ";
    }
    return result;
  }
}
