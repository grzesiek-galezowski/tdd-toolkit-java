package com.github.grzesiek_galezowski.test_environment.types;

import com.github.grzesiek_galezowski.test_environment.fixtures.*;
import lombok.val;
import org.testng.annotations.Test;

import static com.github.grzesiek_galezowski.test_environment.types.ExpectedErrorMessages.expected;
import static com.github.grzesiek_galezowski.test_environment.types.ExpectedErrorMessages.partiallyFound;
import static com.github.grzesiek_galezowski.test_environment.types.TypePathCondition.*;
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
            Integer.valueOf(123)))
    ).hasMessageContaining(
        expected("PersonParser{parser1=PersonAddressParser{}, parser2=PersonNameParser{firstnameParser=FirstnameParser{hs=null, hm={}}, surnameParser=SurnameParser{}}}->PersonNameParser{firstnameParser=FirstnameParser{hs=null, hm={}}, surnameParser=SurnameParser{}}->FirstnameParser{hs=null, hm={}}->123") +
            partiallyFound("root->parser2->firstnameParser"));
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
            Integer.valueOf(123)))
    ).hasMessageContaining(
        expected("PersonParser{parser1=PersonAddressParser{}, parser2=PersonAddressParser{}}->PersonAddressParser{}->123")
            + partiallyFound(
            "root->parser1",
            "root->parser2"
        )
    );
  }






  //todo null on path

}
