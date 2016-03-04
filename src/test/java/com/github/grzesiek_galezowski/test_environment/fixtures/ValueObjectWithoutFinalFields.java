package com.github.grzesiek_galezowski.test_environment.fixtures;

/**
 * Created by astral on 28.02.2016.
 */
public class ValueObjectWithoutFinalFields {

  private final int x;

  public ValueObjectWithoutFinalFields(int x) {
    this.x = x;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ValueObjectWithoutFinalFields)) return false;

    ValueObjectWithoutFinalFields that = (ValueObjectWithoutFinalFields) o;

    return this.x == that.x;

  }

  @Override
  public final int hashCode() {
    return this.x;
  }
}
