package com.github.grzesiek_galezowski.test_environment.types;

import lombok.val;

public class ExpectedErrorMessages {
  //todo
  public static String expected(final String pathString) {
    return "<the following dependency chain: " + pathString + " but the closest matches were : ";
  }

  public static String partiallyFound(final String... matchStrings) {
    val arr = matchStrings;
    String result = "";
    for(int i = 0; i < arr.length; ++i) {
      result += System.lineSeparator();
      result += " " + (i+1) + ". ";
      result += arr[i];
    }
    result += ">";
    return result;
  }
}
