package com.github.grzesiek_galezowski.test_environment;

import com.rits.cloning.Cloner;

/**
 * Created by grzes on 06.04.2017.
 */
public class Clone {
  private static final Cloner CLONER = new Cloner();

  public static <T> T of(final T instance) {
    return CLONER.deepClone(instance);
  }
}
