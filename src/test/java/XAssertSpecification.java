import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.github.grzesiek_galezowski.test_environment.XAssert.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Created by astral on 07.02.2016.
 */
public class XAssertSpecification {
    @Test
    public void shouldAllowMakingSoftAssertionsAndThrowAtTheEnd() {

      assertThatThrownBy(() ->
          assertAll(softly -> {
            softly.assertThat(12).isEqualTo(13);
            softly.assertThat(true).isEqualTo(false);
            softly.assertThat("Johnny").isEqualTo("Johnny");
          }))
      .hasMessageContaining("The following 2 assertions failed:");
    }

  @Test
  public void shouldAllowMakingSoftAssertionsAndNotThrowAtTheEndWhenNoAssertionsFail() {

    Throwable exception = catchThrowable(() ->
        assertAll(softly -> {
          softly.assertThat(12).isEqualTo(12);
          softly.assertThat(true).isEqualTo(true);
          softly.assertThat("Johnny").isEqualTo("Johnny");
        }));

    assertThat(exception).isEqualTo(null);

  }

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

  @Test
  public void shouldCorrectlyAssertOnObjectsLikeness() {
    assertThatNotThrownBy(() ->
      assertThatAreAlike(new User(12, "Lolek"), new User(12, "Lolek")));

    assertThatThrownBy(() ->
      assertThatAreAlike(new User(13, "Lolek"), new User(12, "Lolek")));
  }

  @Test
  public void shouldCorrectlyAssertOnObjectsUnlikeness() {
    assertThatThrownBy(() ->
        assertThatAreNotAlike(new User(12, "Lolek"), new User(12, "Lolek")));

    assertThatNotThrownBy(() ->
        assertThatAreNotAlike(new User(13, "Lolek"), new User(12, "Lolek")));
  }


  public class User
  {
    private List<Integer> age = new ArrayList<>();

    private String name;

    public User(int age, String name) {
      this.age.add(age);
      this.age.add(age);
      this.age.add(age);
      this.name = name;
    }
  }

}
