package com.github.grzesiek_galezowski.test_environment.types;

import com.github.grzesiek_galezowski.test_environment.fixtures.FirstnameParser;
import com.github.grzesiek_galezowski.test_environment.fixtures.PersonAddressParser;
import com.github.grzesiek_galezowski.test_environment.fixtures.PersonParser;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.testng.annotations.Test;

import static com.github.grzesiek_galezowski.test_environment.types.TreePattern.type;
import static com.github.grzesiek_galezowski.test_environment.types.TypePathCondition.subtree;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

//todo finish
public class GraphTypePatternSpecification {

  @Test
  @SuppressFBWarnings
  public void shouldNotThrowWhenSingleItemGraphIsFound() {

    PersonParser personParser = new PersonParser(null, null);

    assertThat(personParser).has(subtree(
        type(PersonParser.class)));
  }

  @Test
  @SuppressFBWarnings
  public void shouldNotThrowWhenOneGraphWithOneNestedTypeIsFound() {

    PersonParser personParser = new PersonParser(
        new FirstnameParser(),
        null);

    assertThat(personParser).has(subtree(
        type(PersonParser.class, type(FirstnameParser.class))));
  }

  @Test
  @SuppressFBWarnings
  public void shouldNotThrowWhenOneGraphWithTwoNestedTypesIsFound() {

    PersonParser personParser = new PersonParser(
        new FirstnameParser(),
        new PersonAddressParser());

    assertThat(personParser).has(subtree(
        type(PersonParser.class,
            type(FirstnameParser.class),
            type(PersonAddressParser.class))));
  }

  @Test
  @SuppressFBWarnings
  public void shouldThrowWhenExpectedNodeMatchesOnMoreThanOnePath() {

    PersonParser personParser = new PersonParser(
        new PersonParser(
            new FirstnameParser(),
            new PersonAddressParser()),
        new PersonParser(
            new FirstnameParser(),
            null));

    assertThatThrownBy(() ->
      assertThat(personParser).has(subtree(
          type(PersonParser.class,
              type(PersonParser.class,
                  type(Integer.class)))))
    ).isInstanceOf(Throwable.class); //todo better exception
  }

  //TODO expected chain is longer than actual (e.g. actual is abc and expected is abcd)
  //TODO only one nested field
  //todo more nested fields at one level
  //todo more matching fields at one level
  //todo error reporting
}
