import com.github.grzesiek_galezowski.test_environment.XAssert;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class XAssertAllSpecification {

  @Test
  public void shouldAllowMakingSoftAssertionsAndThrowAtTheEnd() {

    Assertions.assertThatThrownBy(() ->
        XAssert.assertAll(softly -> {
          softly.assertThat(12).isEqualTo(13);
          softly.assertThat(true).isEqualTo(false);
          softly.assertThat("Johnny").isEqualTo("Johnny");
        }))
        .hasMessageContaining("The following 2 assertions failed:");
  }

  @Test
  public void shouldAllowMakingSoftAssertionsAndNotThrowAtTheEndWhenNoAssertionsFail() {

    Throwable exception = Assertions.catchThrowable(() ->
        XAssert.assertAll(softly -> {
          softly.assertThat(12).isEqualTo(12);
          softly.assertThat(true).isEqualTo(true);
          softly.assertThat("Johnny").isEqualTo("Johnny");
        }));

    Assertions.assertThat(exception).isEqualTo(null);

  }
}