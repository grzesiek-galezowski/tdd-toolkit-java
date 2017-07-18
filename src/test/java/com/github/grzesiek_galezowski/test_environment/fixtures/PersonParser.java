package com.github.grzesiek_galezowski.test_environment.fixtures;

/**
 * Created by grzes on 17.07.2017.
 */
public class PersonParser implements MyParser {
  private final MyParser personAddressParser;
  private final MyParser personNameParser;

  public PersonParser(final MyParser personAddressParser, final MyParser personNameParser) {
    this.personAddressParser = personAddressParser;
    this.personNameParser = personNameParser;
  }
}
