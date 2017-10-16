package com.github.grzesiek_galezowski.test_environment.types;

import com.github.grzesiek_galezowski.test_environment.fixtures.*;
import org.assertj.core.api.Condition;
import org.testng.annotations.Test;

import static com.github.grzesiek_galezowski.test_environment.types.ExpectedErrorMessages.expected;
import static com.github.grzesiek_galezowski.test_environment.types.ExpectedErrorMessages.partiallyFound;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


//todo finish
public class GraphTypePatternSpecification {
  @Test
  public void shouldNotThrowWhenSubGraphIsFound() {

    PersonParser personParser = new PersonParser(null, null);

    assertThat(personParser).has(subgraphContaining(
        type(PersonParser.class)));
  }

  private Class<?> type(final Class<?> c) {
    return c;
  }

  private <T> Condition<T> subgraphContaining(final Class<?> type) {
    return new TypePathCondition<T>(new SubtreeMatchPattern());
  }

  //TODO only one nested field
  //todo more nested fields at one level
  //todo more matching fields at one level
  //todo error reporting


}
