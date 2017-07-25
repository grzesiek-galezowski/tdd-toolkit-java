package com.github.grzesiek_galezowski.test_environment.types;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class BreadCrumbs {

  private String path = "";

  public BreadCrumbs(final String path) {
    this.path = path;
  }

  public BreadCrumbs add(final Class clazz) {
    return new BreadCrumbs(path + "/" + clazz.getSimpleName());
  }

  @Override
  public String toString() {
    return path;
  }

  public static BreadCrumbs initial() {
    return new BreadCrumbs("");
  }
}
