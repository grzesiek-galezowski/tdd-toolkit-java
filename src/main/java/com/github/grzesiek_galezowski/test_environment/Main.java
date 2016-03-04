/**
 * Created by astral on 07.02.2016.
 */
package com.github.grzesiek_galezowski.test_environment;

public class Main {

  private int lolek;
  private Iterable iterable;

  public Main(final int lolek, Iterable iterable) {
    this.lolek = lolek;
    this.iterable = iterable;
  }

    public int geLolek() {
      return iterable.hashCode();
    }
}
