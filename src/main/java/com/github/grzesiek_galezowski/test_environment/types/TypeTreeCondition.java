package com.github.grzesiek_galezowski.test_environment.types;

import com.github.grzesiek_galezowski.test_environment.Clone;
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
  private ClassFields classFields;

  public TypeTreeCondition(final Class clazz, final TypeTreeCondition<Object>[] nestedConditions, final ClassFields classFields) {
    this.clazz = clazz;
    this.nestedConditions = nestedConditions;
    this.classFields = classFields;
  }

  public static TypeTreeCondition type(final Class clazz, final TypeTreeCondition... nested) {
    return new TypeTreeCondition(clazz, nested, ClassFields.of(clazz));
  }

  @Override
  public boolean matches(final T actualObject) {
    ErrorLog errorLog = new ErrorLog();
    boolean matches = matches(actualObject, BreadCrumbs.initial(), errorLog);
    describedAs("\n" + expectedTypeTree(1) + "\n" + errorLog.toString());
    return matches;
  }

  public boolean matches(final T actualObject, final BreadCrumbs breadCrumbs, final ErrorLog errorLog) {
    if (actualObject == null) {
      errorLog.nullObjectFound(breadCrumbs);
      return false;
    }

    if (!expectedTypeIsTheSameAsTypeOf(actualObject)) {
      errorLog.addError(actualObject, breadCrumbs, clazz);
      return false;
    }

    if (nestedConditionsExistsButClassHasNoFieldsToMatchThem()) {
      errorLog.classContainsZeroFields(breadCrumbs);
      return false;
    }

    errorLog.clear();
    for (val condition : nestedConditions) {
      val result = SingleConditionResult.of();

      classFields.searchForMatch(
          actualObject,
          Clone.of(condition),
          result,
          errorLog,
          breadCrumbs);
      if (result.isMismatch()) {
        return false;
      }
    }
    return true;
  }

  public boolean nestedConditionsExistsButClassHasNoFieldsToMatchThem() {
    return nestedConditions.length > 0 && classFields.dontExist();
  }

  public boolean expectedTypeIsTheSameAsTypeOf(final T sourceObject) {
    return clazz.equals(sourceObject.getClass());
  }

  public String expectedTypeTree(final int indent) {
    String result = clazz.getSimpleName();
    if(nestedConditions.length > 0) {
      result += " with: (\n";
      for (val condition : nestedConditions) {
        result += spaces(indent) + condition.expectedTypeTree(indent +1);
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
