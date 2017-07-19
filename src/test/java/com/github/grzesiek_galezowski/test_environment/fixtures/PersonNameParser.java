package com.github.grzesiek_galezowski.test_environment.fixtures;

/**
 * Created by grzes on 17.07.2017.
 */
public class PersonNameParser implements MyParser {
  private final MyParser firstnameParser;
  private final MyParser surnameParser;

  public PersonNameParser(final MyParser firstnameParser, final MyParser surnameParser) {
    this.firstnameParser = firstnameParser;
    this.surnameParser = surnameParser;
  }
}
