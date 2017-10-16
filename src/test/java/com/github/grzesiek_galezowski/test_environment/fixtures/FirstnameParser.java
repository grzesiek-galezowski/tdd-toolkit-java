package com.github.grzesiek_galezowski.test_environment.fixtures;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by grzes on 17.07.2017.
 */
public class FirstnameParser implements MyParser {
  private HashSet<Integer> hs;
  private HashMap<String, String> hm = new HashMap<>();

  @Override
  public String toString() {
    return "FirstnameParser{" +
        "hs=" + hs +
        ", hm=" + hm +
        '}';
  }
}
