package com.github.grzesiek_galezowski.test_environment.types;

import com.github.grzesiek_galezowski.test_environment.fixtures.*;
import lombok.val;
import org.assertj.core.api.Condition;
import org.testng.annotations.Test;

import static com.github.grzesiek_galezowski.test_environment.types.TypePathCondition.typePath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LinearValuePatternsSpecification {

  @Test
  public void shouldAllowQueryingTypeTrees() {

    FirstnameParser firstnameParser = new FirstnameParser();
    PersonNameParser personNameParser = new PersonNameParser(
        firstnameParser,
        new SurnameParser());
    PersonParser personParser = new PersonParser(
        new PersonAddressParser(),
        personNameParser);

    assertThat(personParser).has(valuePath(
        personParser,
        personNameParser,
        firstnameParser));

  }

  private Condition<Object> valuePath(Object... args) {
    return new Condition<Object>() {
      @Override
      public boolean matches(final Object value) {
        throw new RuntimeException("not implemented");
      }
    };
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
    ).hasMessageContaining(
        expected("PersonParser->PersonNameParser->Integer") +
            partiallyFound("root->parser2"));
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
        expected("PersonParser->PersonAddressParser->Integer")
            + partiallyFound(
            "root->parser1",
            "root->parser2"
        )
    );
  }



  //todo when more than one path matches partially
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
        expected("PersonParser->PersonNameParser->Integer")
        + partiallyFound("root->parser2"));
  }

  //todo
  public String expected(final String pathString) {
    return "<the following type path: " + pathString + " but the closest matches were : ";
  }

  private String partiallyFound(final String... matchStrings) {
    val arr = matchStrings;
    String result = "";
    for(int i = 0 ; i < arr.length ; ++i) {
      result += System.lineSeparator();
      result += " " + (i+1) + ". ";
      result += arr[i];
    }
    result += ">";
    return result;
  }

  //todo null on path

}
