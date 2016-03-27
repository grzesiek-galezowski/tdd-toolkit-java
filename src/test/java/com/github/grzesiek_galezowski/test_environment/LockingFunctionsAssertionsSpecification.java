package com.github.grzesiek_galezowski.test_environment;

import autofixture.publicinterface.Any;
import com.github.grzesiek_galezowski.test_environment.fixtures.InterfaceToBeSynchronized;
import com.github.grzesiek_galezowski.test_environment.fixtures.SynchronizedWrapperOverInterfaceToBeSynchronized;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

public class LockingFunctionsAssertionsSpecification {

  private InterfaceToBeSynchronized mock;
  private InterfaceToBeSynchronized realThing;
  private Integer a;
  private Integer b;

  @BeforeMethod
  public void initialize() {
    mock = mock(InterfaceToBeSynchronized.class);
    realThing = new SynchronizedWrapperOverInterfaceToBeSynchronized(mock);
    a = Any.intValue();
    b = Any.intValue();
  }

  @Test
  public void shouldPassWhenFunctionIsCalledCorrectlyInSynchronizedBlock() {
    //WHEN-THEN
    final Function<InterfaceToBeSynchronized, Integer> methodCallToVerify = instance -> instance.correctlyWrappedFunction(a, b);

    XAssert.assertThatProxyTo(mock, realThing)
        .whenReceives(methodCallToVerify, Integer.class).thenLocksCorrectly();
    assertThat(Thread.holdsLock(realThing)).isFalse();
  }

  @Test
  public void shouldFailWhenVoidMethodIsCalledCorrectlyButNotInSynchronizedBlock() {
    //WHEN-THEN
    assertThrowsWhen(instance -> instance.correctlyCalledButNotSynchronizedFunction(a, b));
  }

  @Test
  public void shouldFailWhenFunctionIsNotCalledAtAll() {
    //WHEN-THEN
    assertThrowsWhen(instance -> instance.functionNotCalledAtAll(a, b));
  }

  @Test
  public void shouldFailWhenFunctionIsSynchronizedButCalledWithWrongArguments() {
    //WHEN-THEN
    assertThrowsWhen(instance -> instance.functionCalledWithWrongArguments(a, b));
  }

  @Test
  public void shouldFailWhenFunctionIsSynchronizedButItsReturnValueIsNotPropagatedBack() {
    //WHEN-THEN
    assertThrowsWhen(instance -> instance.functionWithNonPropagatedReturnValue(a, b));
  }

  private void assertThrowsWhen(final Function<InterfaceToBeSynchronized, Integer> function) {
    assertThatThrownBy(() -> XAssert.assertThatProxyTo(mock, realThing)
        .whenReceives(function, Integer.class).thenLocksCorrectly()
    ).isInstanceOf(AssertionError.class);
    assertThat(Thread.holdsLock(realThing)).isFalse();
  }
  //4. release whenReceives throwing exception
}
