package com.github.grzesiek_galezowski.test_environment;

import autofixture.publicinterface.Any;
import com.github.grzesiek_galezowski.test_environment.fixtures.*;
import com.github.grzesiek_galezowski.test_environment.types.TypeTreeCondition;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import static com.github.grzesiek_galezowski.test_environment.types.TypeTreeCondition.type;
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

  @Test(enabled = false)
  @SuppressFBWarnings
  public void shouldAllowQueryingTypeTrees() {


    //assertThat(new SurnameParser()).is(type(SurnameParser.class));

    TypeTreeCondition condition = type(PersonNameParser.class, type(Integer.class));
    boolean is = condition.matches(new PersonNameParser(null, null));

//    assertThat(new PersonNameParser(null, null))
//        .is(type(PersonNameParser.class, type(Integer.class)));

/*
    assertThat(new PersonNameParser(
        new FirstnameParser(),
        new SurnameParser()
    )).has(
        type(PersonNameParser.class,
          type(FirstnameParser.class),
          type(Integer.class),
          type(SurnameParser.class)));
*/
    assertThat(
        new PersonParser(
          new PersonAddressParser(),
          new PersonNameParser(
            new FirstnameParser(),
            new SurnameParser()
        )
    )).is(
        type(PersonParser.class,
            type(PersonAddressParser.class),
             type(PersonNameParser.class,
               type(Integer.class, type(String.class)),

               type(FirstnameParser.class),
               type(SurnameParser.class))));

  }


}