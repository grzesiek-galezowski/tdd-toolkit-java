package com.github.grzesiek_galezowski.test_environment.fixtures;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Created by grzes on 17.07.2017.
 */
public class PersonNameParser implements MyParser {
  private final MyParser firstnameParser;
  private final MyParser surnameParser;


  @Override
  public String toString() {
    return "PersonNameParser{" +
        "firstnameParser=" + firstnameParser +
        ", surnameParser=" + surnameParser +
        '}';
  }

  public PersonNameParser(
      @SuppressFBWarnings
      final MyParser firstnameParser,
      @SuppressFBWarnings
      final MyParser surnameParser) {
    this.firstnameParser = firstnameParser;
    this.surnameParser = surnameParser;
  }
}
