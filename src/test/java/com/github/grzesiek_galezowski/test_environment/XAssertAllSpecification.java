package com.github.grzesiek_galezowski.test_environment;

import autofixture.publicinterface.Any;
import com.github.grzesiek_galezowski.test_environment.fixtures.*;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class XAssertAllSpecification {

  public static final int ARBITRARY_NUMBER = Any.intValue();

  @Test
  public void shouldAllowMakingSoftAssertionsAndThrowAtTheEnd() {

    Assertions.assertThatThrownBy(() ->
        XAssert.assertAll(softly -> {
          softly.assertThat(ARBITRARY_NUMBER).isEqualTo(ARBITRARY_NUMBER + 1);
          softly.assertThat(true).isEqualTo(false);
          softly.assertThat("Johnny").isEqualTo("Johnny");
        }))
        .hasMessageContaining("The following 2 assertions failed:");
  }

  @Test
  public void shouldAllowMakingSoftAssertionsAndNotThrowAtTheEndWhenNoAssertionsFail() {

    final Throwable exception = Assertions.catchThrowable(() ->
        XAssert.assertAll(softly -> {
          softly.assertThat(ARBITRARY_NUMBER).isEqualTo(ARBITRARY_NUMBER);
          softly.assertThat(true).isEqualTo(true);
          softly.assertThat("Johnny").isEqualTo("Johnny");
        }));

    assertThat(exception).isEqualTo(null);
  }

  @Test
  public void shouldAllowQueryingTypeTrees() {
    val typeTree = new PersonParser(
        new PersonAddressParser(),
        new PersonNameParser(
            new FirstnameParser(),
            new SurnameParser()
        )
    );


/*
    assertThat(new SurnameParser()).is(type(SurnameParser.class));

    assertThat(new PersonNameParser(null, null))
        .is(type(PersonNameParser.class, type(Integer.class)));

    assertThat(new PersonNameParser(
        new FirstnameParser(),
        new SurnameParser()
    )).has(
        type(PersonNameParser.class,
          type(FirstnameParser.class),
          type(SurnameParser.class)));
*/
    assertThat(typeTree).is(
        type(PersonParser.class,
            type(PersonAddressParser.class),
             type(PersonNameParser.class,
               type(Integer.class, type(String.class)),

               type(FirstnameParser.class),
               type(SurnameParser.class))));

  }

  private TypeTreeCondition type(final Class clazz, TypeTreeCondition... nested) {
    return new TypeTreeCondition(clazz, nested);
  }


}