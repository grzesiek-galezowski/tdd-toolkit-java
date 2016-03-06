package com.github.grzesiek_galezowski.test_environment;

import autofixture.publicinterface.Any;
import autofixture.publicinterface.InstanceOf;
import com.github.grzesiek_galezowski.test_environment.fixtures.InterfaceToBeSynchronized;
import com.github.grzesiek_galezowski.test_environment.fixtures.SynchronizedWrapperOverInterfaceToBeSynchronized;
import com.github.grzesiek_galezowski.test_environment.fixtures.ValueObjectWithoutFinalFields;
import org.testng.annotations.Test;

import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.github.grzesiek_galezowski.test_environment.XAssert.assertThatNotThrownBy;
import static com.github.grzesiek_galezowski.test_environment.XAssert.assertValueObject;
import static com.github.grzesiek_galezowski.test_environment.XJAssertConditions.valueObjectBehavior;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

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

  @Test
  public void shouldLockBlaBlaBla() {
    //GIVEN
    final InterfaceToBeSynchronized mock = mock(InterfaceToBeSynchronized.class);
    final InterfaceToBeSynchronized realThing = new SynchronizedWrapperOverInterfaceToBeSynchronized(mock);
    final Integer a = Any.intValue();
    final Integer b = Any.intValue();

    XAssert.assertSynchronized(mock, realThing, instance -> {
      instance.voidMethod(a, b);
    });
  }
  //TODO tests for:
  //1. not calling the method
  //2. not synchronizing the method
  //3. calling the method and synchronizing, but in the wrong order
  //4. release on throwing exception

  @Test
  public void shouldLockBlaBlaBla2() {
    //GIVEN
    final InterfaceToBeSynchronized mock = mock(InterfaceToBeSynchronized.class);
    final InterfaceToBeSynchronized realThing = new SynchronizedWrapperOverInterfaceToBeSynchronized(mock);
    final Integer a = Any.intValue();
    final Integer b = Any.intValue();

    XAssert.assertSynchronized(mock, realThing,
        instance -> instance.methodWithReturn(a, b),
        Integer.class);
  }

  //TODO tests for:
  //1. not calling the method
  //2. not synchronizing the method
  //3. calling the method and synchronizing, but in the wrong order
  //4. release on throwing exception

  @Test
  public void shouldLockBlaBlaBla3() {
    //GIVEN
    final InterfaceToBeSynchronized mock = mock(InterfaceToBeSynchronized.class);
    final InterfaceToBeSynchronized realThing = new SynchronizedWrapperOverInterfaceToBeSynchronized(mock);
    final Integer a = Any.intValue();
    final Integer b = Any.intValue();

    XAssert.assertSynchronized(mock, realThing,
        instance -> instance.methodWithGenericReturn(a, b),
        new InstanceOf<List<Integer>>() {});
  }

  //TODO tests for:
  //1. not calling the method
  //2. not synchronizing the method
  //3. calling the method and synchronizing, but in the wrong order
  //4. release on throwing exception

}
