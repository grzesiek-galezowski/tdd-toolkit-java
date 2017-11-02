package com.github.grzesiek_galezowski.test_environment.types;

import com.github.grzesiek_galezowski.test_environment.fixtures.FirstnameParser;
import com.github.grzesiek_galezowski.test_environment.fixtures.PersonAddressParser;
import com.github.grzesiek_galezowski.test_environment.fixtures.PersonParser;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.testng.annotations.Test;

import static com.github.grzesiek_galezowski.test_environment.types.AnyObjectOfType.*;
import static com.github.grzesiek_galezowski.test_environment.types.ObjectGraphContainsDependencyCondition.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DependencyPatternSpecification {
  @Test
  @SuppressFBWarnings
  public void shouldThrowWhenExpectedNodeMatchesOnMoreThanOnePath() {

    FirstnameParser parser1 = new FirstnameParser();
    PersonAddressParser parser2 = new PersonAddressParser();
    PersonParser personParser = new PersonParser(
        new PersonParser(
            parser1,
            parser2),
        new PersonParser(
            new FirstnameParser(),
            null));

    assertThat(personParser).has(dependencyOn(personParser));
    assertThat(personParser).has(dependencyOn(parser1));
    assertThat(personParser).has(dependencyOn(parser2));
    assertThat(personParser).has(dependencyOn(objectOfType(FirstnameParser.class)));


    assertThatThrownBy(
        () -> assertThat(personParser).has(dependencyOn("trolololo"))
    ).hasMessageContaining("<String: trolololo>");

    assertThatThrownBy(
        () -> assertThat(personParser).has(dependencyOn(objectOfType(SecurityManager.class)))
    ).hasMessageContaining("<AnyObjectOfType: Any object of type SecurityManager>");

  }

}
