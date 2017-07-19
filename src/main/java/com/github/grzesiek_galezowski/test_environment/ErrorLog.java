package com.github.grzesiek_galezowski.test_environment;

/**
 * Created by grzes on 17.07.2017.
 */

public class ErrorLog {
  private StringBuilder value = new StringBuilder();
  private Class clazz;

  public ErrorLog(final Class clazz) {

    this.clazz = clazz;
  }

  @Override
  public String toString() {
    return this.value.toString();
  }

  public void append(final StringBuilder content) {
    this.value.append(content.toString());
  }

  public void classContainsZeroFields() {
    this.value.append("the class contains zero fields");
  }

  public void nullObjectFound() {
    this.value.append("is set to null, which has no type");
  }

  public <T> void addError(final T sourceObject) {
    this.value.append(this.clazz.getSimpleName() + " expected but got " + sourceObject.getClass().getSimpleName());
  }
}
