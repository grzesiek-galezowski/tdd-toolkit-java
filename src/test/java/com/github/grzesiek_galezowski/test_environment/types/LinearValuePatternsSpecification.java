package com.github.grzesiek_galezowski.test_environment.types;

import autofixture.publicinterface.Any;
import com.github.grzesiek_galezowski.test_environment.fixtures.*;
import org.testng.annotations.Test;

import static com.github.grzesiek_galezowski.test_environment.types.ExpectedErrorMessages.expected;
import static com.github.grzesiek_galezowski.test_environment.types.ExpectedErrorMessages.partiallyFound;
import static com.github.grzesiek_galezowski.test_environment.types.TypePathCondition.valuePath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LinearValuePatternsSpecification {

  private static final Integer ANY_INT = Any.intValue();

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

  @Test
  public void shouldThrowErrorWhenPathIsPartiallyFoundOnce() {

    FirstnameParser firstnameParser = new FirstnameParser();
    PersonNameParser personNameParser = new PersonNameParser(
        firstnameParser,
        new SurnameParser());
    PersonParser personParser = new PersonParser(
        new PersonAddressParser(),
        personNameParser);

    assertThatThrownBy(() ->
        assertThat(personParser).has(valuePath(
            personParser,
            personNameParser,
            firstnameParser,
            ANY_INT))
    ).hasMessageContaining(
        expected("PersonParser{parser1=PersonAddressParser{}, parser2=PersonNameParser{firstnameParser=FirstnameParser{hs=null, hm={}}, surnameParser=SurnameParser{}}}->PersonNameParser{firstnameParser=FirstnameParser{hs=null, hm={}}, surnameParser=SurnameParser{}}->FirstnameParser{hs=null, hm={}}->" + ANY_INT)
            + partiallyFound("root->parser2->firstnameParser"));
  }

  @Test
  public void shouldThrowErrorWhenPathIsPartiallyFoundTwice() {

    PersonAddressParser personAddressParser = new PersonAddressParser();
    PersonParser personParser = new PersonParser(
        personAddressParser,
        personAddressParser);

    assertThatThrownBy(() ->
        assertThat(personParser).has(valuePath(
            personParser,
            personAddressParser,
            ANY_INT))
    ).hasMessageContaining(
        expected("PersonParser{parser1=PersonAddressParser{}, parser2=PersonAddressParser{}}->PersonAddressParser{}->" + ANY_INT)
            + partiallyFound(
            "root->parser1",
            "root->parser2"
        )
    );
  }






  //todo null on path

}
