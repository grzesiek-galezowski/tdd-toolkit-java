package com.github.grzesiek_galezowski.test_environment.types;

import com.github.grzesiek_galezowski.test_environment.fixtures.*;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.testng.annotations.Test;

import static com.github.grzesiek_galezowski.test_environment.types.TypePathCondition.typePath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TypePatternsSpecification {
  @Test
  public void shouldAllowQueryingTypeTrees() {

    PersonParser personParser = new PersonParser(
        new PersonAddressParser(),
        new PersonNameParser(
            new FirstnameParser(),
            new SurnameParser()));

    assertThat(personParser).has(typePath(
        PersonParser.class,
        PersonNameParser.class,
        FirstnameParser.class));

  }

  @Test
  public void shouldThrowErrorWhenPathIsPartiallyFoundOnce() {

    PersonParser personParser = new PersonParser(
        new PersonAddressParser(),
        new PersonNameParser(
            new FirstnameParser(),
            new SurnameParser()));

    assertThatThrownBy(() ->
      assertThat(personParser).has(typePath(
          PersonParser.class,
          PersonNameParser.class,
          Integer.class))
    ).hasMessageContaining("<the following type path: PersonParser->PersonNameParser->Integer but the closest matches were : " + System.lineSeparator() + " 1. root->parser2>");
  }

  @Test
  public void shouldThrowErrorWhenPathIsPartiallyFoundTwice() {

    PersonParser personParser = new PersonParser(
        new PersonAddressParser(),
        new PersonAddressParser());

    assertThatThrownBy(() ->
        assertThat(personParser).has(typePath(
            PersonParser.class,
            PersonAddressParser.class,
            Integer.class))
    ).hasMessageContaining(
        "<the following type path: PersonParser->PersonAddressParser->Integer but the closest matches were : "
            + System.lineSeparator() +
            " 1. root->parser1"
            + System.lineSeparator() +
            " 2. root->parser2>");
  }


  //todo when more than one path matches partially
  //todo null on path
  //todo

  @Test //todo subgraph matching
  public void shouldNotThrowWhenSubGraphIsFound() {

    PersonParser personParser = new PersonParser(
        new PersonAddressParser(),
        new PersonNameParser(
            new FirstnameParser(),
            new SurnameParser()));

    assertThatThrownBy(() ->
        assertThat(personParser).has(typePath(
            PersonParser.class,
            PersonNameParser.class,
            Integer.class))
    ).hasMessageContaining(
        "<the following type path: PersonParser->PersonNameParser->Integer but the closest matches were : "
        + System.lineSeparator()
        + " 1. root->parser2>");
  }


}
