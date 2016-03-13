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

  public static class LockingVoidMethodAssertionsSpecification {

    private final InterfaceToBeSynchronized mock = mock(InterfaceToBeSynchronized.class);
    private final InterfaceToBeSynchronized realThing = new SynchronizedWrapperOverInterfaceToBeSynchronized(mock);
    private final Integer a = Any.intValue();
    private final Integer b = Any.intValue();

    @Test
    public void shouldPassWhenVoidMethodIsCalledCorrectlyInSynchronizedBlock() {
      //WHEN-THEN
      XAssert.assertSynchronizedVoidMethod(mock, realThing, instance -> {
        instance.correctlyWrappedVoidMethod(a, b);
      });
      assertThat(Thread.holdsLock(realThing)).isFalse();
    }

    @Test
    public void shouldFailWhenVoidMethodIsCalledCorrectlyButNotInSynchronizedBlock() {
      //WHEN-THEN
      assertThatThrownBy(() ->
          XAssert.assertSynchronizedVoidMethod(mock, realThing, instance -> {
            instance.correctlyCalledButNotSynchronizedVoidMethod(a, b);
          })
      ).isInstanceOf(AssertionError.class);
      assertThat(Thread.holdsLock(realThing)).isFalse();
    }

    @Test
    public void shouldFailWhenVoidMethodIsNotCalledAtAll() {
      //WHEN-THEN
      assertThatThrownBy(() ->
          XAssert.assertSynchronizedVoidMethod(mock, realThing, instance -> {
            instance.voidMethodNotCalledAtAll(a, b);
          })
      ).isInstanceOf(AssertionError.class);
      assertThat(Thread.holdsLock(realThing)).isFalse();
    }

    @Test
    public void shouldFailWhenVoidMethodIsSynchronizedButCalledWithWrongArguments() {
      //WHEN-THEN
      assertThatThrownBy(() ->
          XAssert.assertSynchronizedVoidMethod(mock, realThing, instance -> {
            instance.voidMethodCalledWithWrongArguments(a, b);
          })
      ).isInstanceOf(AssertionError.class);
      assertThat(Thread.holdsLock(realThing)).isFalse();
    }

    //TODO tests for:
    //4. release on throwing exception
  }

  public class LockingFunctionsAssertionsSpecification {

    private final InterfaceToBeSynchronized mock = mock(InterfaceToBeSynchronized.class);
    private final InterfaceToBeSynchronized realThing = new SynchronizedWrapperOverInterfaceToBeSynchronized(mock);
    private final Integer a = Any.intValue();
    private final Integer b = Any.intValue();

    @Test
    public void shouldPassWhenFunctionIsCalledCorrectlyInSynchronizedBlock() {
      //WHEN-THEN
      XAssert.assertSynchronizedFunction(mock, realThing, instance -> {
        return instance.correctlyWrappedFunction(a, b);
      }, Integer.class);
      assertThat(Thread.holdsLock(realThing)).isFalse();
    }

    @Test
    public void shouldFailWhenVoidMethodIsCalledCorrectlyButNotInSynchronizedBlock() {
      //WHEN-THEN
      assertThatThrownBy(() ->
          XAssert.assertSynchronizedFunction(mock, realThing, instance -> {
            return instance.correctlyCalledButNotSynchronizedFunction(a, b);
          }, Integer.class)
      ).isInstanceOf(AssertionError.class);
      assertThat(Thread.holdsLock(realThing)).isFalse();
    }

    @Test
    public void shouldFailWhenFunctionIsNotCalledAtAll() {
      //WHEN-THEN
      assertThatThrownBy(() ->
          XAssert.assertSynchronizedFunction(mock, realThing, instance -> {
            return instance.functionNotCalledAtAll(a, b);
          }, Integer.class)
      ).isInstanceOf(AssertionError.class);
      assertThat(Thread.holdsLock(realThing)).isFalse();
    }

    @Test
    public void shouldFailWhenFunctionIsSynchronizedButCalledWithWrongArguments() {
      //WHEN-THEN
      assertThatThrownBy(() ->
          XAssert.assertSynchronizedFunction(mock, realThing, instance -> {
            return instance.functionCalledWithWrongArguments(a, b);
          }, Integer.class)
      ).isInstanceOf(AssertionError.class);
      assertThat(Thread.holdsLock(realThing)).isFalse();
    }

    @Test
    public void shouldFailWhenFunctionIsSynchronizedButItsReturnValueIsNotPropagatedBack() {
      //WHEN-THEN
      assertThatThrownBy(() ->
          XAssert.assertSynchronizedFunction(mock, realThing, instance -> {
            return instance.functionWithNonPropagatedReturnValue(a, b);
          }, Integer.class)
      ).isInstanceOf(AssertionError.class);
      assertThat(Thread.holdsLock(realThing)).isFalse();
    }
    //4. release on throwing exception

    //TODO what to do with this one?
    @Test
    public void shouldLockBlaBlaBla3() {
      //GIVEN
      final InterfaceToBeSynchronized mock = mock(InterfaceToBeSynchronized.class);
      final InterfaceToBeSynchronized realThing = new SynchronizedWrapperOverInterfaceToBeSynchronized(mock);
      final Integer a = Any.intValue();
      final Integer b = Any.intValue();

      //WHEN-THEN
      XAssert.assertSynchronizedFunction(mock, realThing,
          instance -> instance.methodWithGenericReturn(a, b),
          new InstanceOf<List<Integer>>() {
          });
    }

    //TODO tests for:
    //1. not calling the method
    //2. not synchronizing the method
    //3. calling the method and synchronizing, but in the wrong order
    //4. release on throwing exception
  }
}
