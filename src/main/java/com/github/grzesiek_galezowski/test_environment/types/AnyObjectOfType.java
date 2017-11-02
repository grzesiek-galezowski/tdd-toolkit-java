package com.github.grzesiek_galezowski.test_environment.types;

public class AnyObjectOfType {
  private Class clazz;

  public AnyObjectOfType(final Class clazz) {
    this.clazz = clazz;
  }

  public static Object objectOfType(final Class clazz) {
    return new AnyObjectOfType(clazz);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if(o == null) {
      return false;
    }
    return clazz.equals(o.getClass());
  }

  @Override
  public int hashCode() {
    return clazz != null ? clazz.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "Any object of type " + clazz.getSimpleName();
  }
}
