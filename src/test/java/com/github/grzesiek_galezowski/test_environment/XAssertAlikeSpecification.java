package com.github.grzesiek_galezowski.test_environment;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.github.grzesiek_galezowski.test_environment.XJAssertConditions.like;
import static com.github.grzesiek_galezowski.test_environment.XJAssertConditions.notLike;
import static org.assertj.core.api.Assertions.assertThat;

public class XAssertAlikeSpecification {

  @Test
  public void shouldCorrectlyAssertOnObjectsLikeness() {
    XAssert.assertThatNotThrownBy(() ->
        XAssert.assertThatAreAlike(new User(12, "Lolek"), new User(12, "Lolek")));

    Assertions.assertThatThrownBy(() ->
        XAssert.assertThatAreAlike(new User(13, "Lolek"), new User(12, "Lolek")));
  }

  @Test
  public void shouldCorrectlyAssertOnObjectsUnlikeness() {
    Assertions.assertThatThrownBy(() ->
        XAssert.assertThatAreNotAlike(new User(12, "Lolek"), new User(12, "Lolek")));

    XAssert.assertThatNotThrownBy(() ->
        XAssert.assertThatAreNotAlike(new User(13, "Lolek"), new User(12, "Lolek")));
  }

  @Test
  public void shouldCorrectlyAssertOnObjectsLikenessUsingConditions() {
    XAssert.assertThatNotThrownBy(() ->
        assertThat(new User(12, "Lolek")).is(like(new User(12, "Lolek"))));

    Assertions.assertThatThrownBy(() ->
        assertThat(new User(13, "Lolek"))
            .is(like(new User(12, "Lolek"))))
    .hasMessageContaining(
        "<like {\"age\":[12,12,12],\"name\":\"Lolek\"} but was {\"age\":[13,13,13],\"name\":\"Lolek\"}>")
    ;
  }

  @Test
  public void shouldCorrectlyAssertOnObjectsUnlikenessUsingConditions() {

    Assertions.assertThatThrownBy(() ->
        assertThat(new User(12, "Lolek")).is(notLike(new User(12, "Lolek"))))
        .hasMessageContaining(
            "<not like {\"age\":[12,12,12],\"name\":\"Lolek\"} but was {\"age\":[12,12,12],\"name\":\"Lolek\"}>");

    XAssert.assertThatNotThrownBy(() ->
        assertThat(new User(13, "Lolek")).is(notLike(new User(12, "Lolek"))));
  }

  class User {
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