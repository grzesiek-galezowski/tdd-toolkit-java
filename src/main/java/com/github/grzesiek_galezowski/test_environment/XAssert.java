package com.github.grzesiek_galezowski.test_environment;

import com.google.gson.Gson;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.ThrowableAssert;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * Created by astral on 09.02.2016.
 */
public class XAssert {

  private static final Gson gson = new Gson();

  public static void assertAll(Consumer<SoftAssertions> assertions) {
    SoftAssertions softAssertions = new SoftAssertions();
    assertions.accept(softAssertions);
    softAssertions.assertAll();
  }

  public static void assertThatNotThrownBy(ThrowableAssert.ThrowingCallable callable) {
    Throwable exception = catchThrowable(callable);
    assertThat(exception).isEqualTo(null);
  }

  public static <T> void assertThatAreAlike(T obj1, T obj2) {
    assertThat(gson.toJson(obj1)).isEqualTo(gson.toJson(obj2));
  }

  public static <T> void assertThatAreNotAlike(T obj1, T obj2) {
    assertThat(gson.toJson(obj1)).isNotEqualTo(gson.toJson(obj2));
  }

}
