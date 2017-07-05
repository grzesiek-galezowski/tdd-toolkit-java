package integration;

import autofixture.publicinterface.Any;
import autofixture.publicinterface.InstanceOf;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by grzes on 05.07.2017.
 */
public class AutoFixtureIntegrationSpecification {
  @Test
  public void shouldNotThrowExceptionWhenGeneratingObjects() {
      assertThat(Any.anonymous(String.class)).isNotNull();
      assertThat(Any.anonymous(new InstanceOf<List<Integer>>(){})).isNotNull();
      assertThat(Any.anonymous(ObjectWithListInConstructor.class)).isNotNull();
  }
}
