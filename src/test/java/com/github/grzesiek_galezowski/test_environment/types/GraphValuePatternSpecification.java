package com.github.grzesiek_galezowski.test_environment.types;

import autofixture.publicinterface.Any;
import com.github.grzesiek_galezowski.test_environment.fixtures.FirstnameParser;
import com.github.grzesiek_galezowski.test_environment.fixtures.PersonAddressParser;
import com.github.grzesiek_galezowski.test_environment.fixtures.PersonParser;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.testng.annotations.Test;

import static com.github.grzesiek_galezowski.test_environment.types.TreePattern.object;
import static com.github.grzesiek_galezowski.test_environment.types.TypePathCondition.subgraphContaining;
import static org.assertj.core.api.Assertions.*;

//todo finish
public class GraphValuePatternSpecification {

  @Test
  @SuppressFBWarnings
  public void shouldNotThrowWhenSingleItemGraphIsFound() {

    PersonParser personParser = new PersonParser(null, null);

    assertThat(personParser).has(subgraphContaining(
        object(personParser)));
  }

  @Test
  @SuppressFBWarnings
  public void shouldNotThrowWhenOneGraphWithOneNestedTypeIsFound() {

    FirstnameParser firstnameParser = new FirstnameParser();
    PersonParser personParser = new PersonParser(
        firstnameParser,
        null);

    assertThat(personParser).has(subgraphContaining(
        object(personParser, object(firstnameParser))));
  }

  @Test
  @SuppressFBWarnings
  public void shouldNotThrowWhenOneGraphWithTwoNestedTypesIsFound() {

    FirstnameParser firstnameParser = new FirstnameParser();
    PersonAddressParser personAddressParser = new PersonAddressParser();
    PersonParser personParser = new PersonParser(
        firstnameParser,
        personAddressParser);

    assertThat(personParser).has(subgraphContaining(
        object(personParser,
            object(firstnameParser),
            object(personAddressParser))));
  }

  @Test
  @SuppressFBWarnings
  public void shouldThrowWhenExpectedNodeMatchesOnMoreThanOnePath() {
    FirstnameParser firstnameParser1 = new FirstnameParser();
    PersonAddressParser personAddressParser = new PersonAddressParser();
    PersonParser personParser2 = new PersonParser(
        firstnameParser1,
        personAddressParser);
    FirstnameParser firstnameParser3 = new FirstnameParser();
    PersonParser personParser3 = new PersonParser(
        firstnameParser3,
        null);
    PersonParser personParser1 = new PersonParser(
        personParser2,
        personParser3);

    assertThatThrownBy(() ->
      assertThat(personParser1).has(subgraphContaining(
          object(personParser1,
              object(personParser1,
                  object(Any.intValue())))))
    ).isInstanceOf(Throwable.class); //todo better exception
  }

  //TODO expected chain is longer than actual (e.g. actual is abc and expected is abcd)
  //TODO only one nested field
  //todo more nested fields at one level
  //todo more matching fields at one level
  //todo error reporting
}
