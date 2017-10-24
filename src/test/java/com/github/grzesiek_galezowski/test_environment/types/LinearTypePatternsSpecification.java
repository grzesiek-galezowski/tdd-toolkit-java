package com.github.grzesiek_galezowski.test_environment.types;

import com.github.grzesiek_galezowski.test_environment.fixtures.*;
import org.testng.annotations.Test;

import static com.github.grzesiek_galezowski.test_environment.types.ExpectedErrorMessages.*;
import static com.github.grzesiek_galezowski.test_environment.types.TypePathCondition.typePath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LinearTypePatternsSpecification {
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

    assertThat(personParser).has(typePath(
        PersonParser.class,
        PersonNameParser.class,
        SurnameParser.class));

    assertThat(personParser).has(typePath(
        PersonParser.class,
        PersonNameParser.class));

    assertThat(personParser).has(typePath(PersonParser.class));
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
        expected("PersonParser->PersonNameParser->Integer")
            + partiallyFound("root->parser2"));
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

  //todo null on path

}
