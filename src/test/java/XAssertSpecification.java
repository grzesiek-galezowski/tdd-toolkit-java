import org.testng.annotations.Test;

import static com.github.grzesiek_galezowski.test_environment.XAssert.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Created by astral on 07.02.2016.
 */
public class XAssertSpecification {

  @Test
  public void shouldFailWhenAssertingThatNoExceptionShouldBeThrownButThereIs() {
    assertThatThrownBy(() ->
      assertThatNotThrownBy(() -> {
        throw new RuntimeException("grzesiek");
      })
    ).hasMessageContaining("grzesiek");

  }

  @Test
  public void shouldNotFailWhenAssertingThatNoExceptionShouldBeThrownAndThereIsNot() {
      assertThatNotThrownBy(() -> {

      });
  }


}
