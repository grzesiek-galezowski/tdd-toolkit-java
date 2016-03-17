package com.github.grzesiek_galezowski.test_environment;

import com.github.grzesiek_galezowski.test_environment.fixtures.ValueObjectWithoutFinalFields;
import org.testng.annotations.Test;

import java.time.Period;
import java.util.Date;
import java.util.Optional;

import static com.github.grzesiek_galezowski.test_environment.XAssert.assertThatNotThrownBy;
import static com.github.grzesiek_galezowski.test_environment.XAssert.assertValueObject;
import static com.github.grzesiek_galezowski.test_environment.XJAssertConditions.valueObjectBehavior;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by astral whenReceives 07.02.2016.
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

  @Test
  public void shouldAssertOnValueObjectBehavior() {
    assertValueObject(Period.class);
    assertValueObject(Optional.class);
    assertThatThrownBy(() -> assertValueObject(XAssertAlikeSpecification.User.class));
    assertThatThrownBy(() -> assertValueObject(Date.class));
  }

  @Test
  public void shouldAssertOnValueObjectBehaviorWithFluentSyntax() {
    assertThat(Period.class).has(valueObjectBehavior());
    assertThat(Optional.class).has(valueObjectBehavior());
    assertThat(ValueObjectWithoutFinalFields.class).has(valueObjectBehavior());
    assertThatThrownBy(() -> assertThat(XAssertAlikeSpecification.User.class).has(valueObjectBehavior()));
    assertThatThrownBy(() -> assertThat(Date.class).has(valueObjectBehavior()));
  }
}

