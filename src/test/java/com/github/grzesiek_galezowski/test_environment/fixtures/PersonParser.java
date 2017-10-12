package com.github.grzesiek_galezowski.test_environment.fixtures;

/**
 * Created by grzes on 17.07.2017.
 */
public class PersonParser implements MyParser {
  private final MyParser parser1;
  private final MyParser parser2;

  public PersonParser(final MyParser parser1, final MyParser parser2) {
    this.parser1 = parser1;
    this.parser2 = parser2;
  }
}
