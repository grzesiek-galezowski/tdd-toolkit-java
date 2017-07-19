package com.github.grzesiek_galezowski.test_environment;

import com.github.grzesiek_galezowski.test_environment.buffer.ClassFields;
import lombok.val;
import org.assertj.core.api.Condition;

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
  private ErrorLog errorLog;
  private ClassFields classFields;

  public TypeTreeCondition(final Class clazz, final TypeTreeCondition<Object>[] nestedConditions) {
    this.clazz = clazz;
    this.nestedConditions = nestedConditions;
    describedAs(clazz.toString());
    errorLog = new ErrorLog(clazz);
    classFields = ClassFields.of(clazz, errorLog);
  }

  @Override
  public boolean matches(final T sourceObject) {

    if(sourceObject == null) {
      errorLog.nullObjectFound();
      return false;
    }

    if (clazz.equals(sourceObject.getClass())) {
      for (val condition : nestedConditions) {
        SingleConditionResult result = new SingleConditionResult();

        if(classFields.dontExist()) {
          errorLog.classContainsZeroFields();
        } else {
          classFields.searchForMatch(sourceObject, Clone.of(condition), result);
        }

        if (result.isMismatch()) {
          result.addTo(errorLog);
          return false;
        }
      }
      return true;
    } else {
      errorLog.addError(sourceObject);
      return false;
    }
  }

  public String reasonOfFailure() {
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
