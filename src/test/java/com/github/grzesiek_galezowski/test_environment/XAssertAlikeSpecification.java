package com.github.grzesiek_galezowski.test_environment;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.github.grzesiek_galezowski.test_environment.XJAssertConditions.like;
import static com.github.grzesiek_galezowski.test_environment.XJAssertConditions.notLike;
import static org.assertj.core.api.Assertions.assertThat;

public class XAssertAlikeSpecification {

  public static final int AGE = 12;

  @Test
  public void shouldCorrectlyAssertOnObjectsLikeness() {
    XAssert.assertThatNotThrownBy(() ->
        XAssert.assertThatAreAlike(lolek12(), lolek12()));

    Assertions.assertThatThrownBy(() ->
        XAssert.assertThatAreAlike(lolek13(), lolek12()));
  }

  @Test
  public void shouldCorrectlyAssertOnObjectsUnlikeness() {
    Assertions.assertThatThrownBy(() ->
        XAssert.assertThatAreNotAlike(lolek12(), lolek12()));

    XAssert.assertThatNotThrownBy(() ->
        XAssert.assertThatAreNotAlike(lolek13(), lolek12()));
  }

  @Test
  public void shouldCorrectlyAssertOnObjectsLikenessUsingConditions() {
    XAssert.assertThatNotThrownBy(() ->
        assertThat(lolek12()).is(like(lolek12())));

    Assertions.assertThatThrownBy(() ->
        assertThat(lolek13()).is(like(lolek12()))).hasMessageContaining(
        "<like {\"age\":[12,12,12],\"name\":\"Lolek\"}" +
            " but was {\"age\":[13,13,13],\"name\":\"Lolek\"}>");
  }

  @Test
  public void shouldCorrectlyAssertOnObjectsUnlikenessUsingConditions() {

    Assertions.assertThatThrownBy(() ->
        assertThat(lolek12()).is(notLike(lolek12())))
        .hasMessageContaining(
            "<not like {\"age\":[12,12,12],\"name\":\"Lolek\"}" +
                " but was {\"age\":[12,12,12],\"name\":\"Lolek\"}>");

    XAssert.assertThatNotThrownBy(() ->
        assertThat(lolek13()).is(notLike(lolek12())));
  }

  private User lolek13() {
    return new User(AGE+1, "Lolek");
  }

  private User lolek12() {
    return new User(AGE, "Lolek");
  }

  public class User {
    private final List<Integer> age = new ArrayList<>();

    private final String name;

    public User(final int age, final String name) {
      this.age.add(age);
      this.age.add(age);
      this.age.add(age);
      this.name = name;
    }
  }
}