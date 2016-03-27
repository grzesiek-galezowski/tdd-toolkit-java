package com.github.grzesiek_galezowski.test_environment;

import autofixture.publicinterface.Any;
import autofixture.publicinterface.InstanceOf;
import com.github.grzesiek_galezowski.test_environment.fixtures.InterfaceToBeSynchronized;
import com.github.grzesiek_galezowski.test_environment.fixtures.SynchronizedWrapperOverInterfaceToBeSynchronized;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.function.Function;

import static com.github.grzesiek_galezowski.test_environment.XAssert.assertThatProxyTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

public class LockingFunctionsWithGenericReturnTypeAssertionsSpecification {
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

    assertThatProxyTo(mock, realThing)
        .whenReceives(
            instance -> instance.genericCorrectlySynchronizedFunction(a, b),
            new InstanceOf<List<Integer>>() {
            })
        .thenLocksCorrectly();
    assertThat(Thread.holdsLock(realThing)).isFalse();
  }

  @Test
  public void shouldFailWhenVoidMethodIsCalledCorrectlyButNotInSynchronizedBlock() {
    //WHEN-THEN
    assertThrowsWhen(instance -> instance.genericCorrectlyCalledButNotSynchronizedFunction(a, b));
  }

  @Test
  public void shouldFailWhenFunctionIsNotCalledAtAll() {
    //WHEN-THEN
    assertThrowsWhen(instance -> instance.genericFunctionNotCalledAtAll(a, b));
  }

  @Test
  public void shouldFailWhenFunctionIsSynchronizedButCalledWithWrongArguments() {
    //WHEN-THEN
    assertThrowsWhen(instance -> instance.genericFunctionCalledWithWrongArguments(a, b));
  }

  @Test
  public void shouldFailWhenFunctionIsSynchronizedButItsReturnValueIsNotPropagatedBack() {
    //WHEN-THEN
    assertThrowsWhen(instance -> instance.genericFunctionWithNonPropagatedReturnValue(a, b));
  }

  private void assertThrowsWhen(final Function<InterfaceToBeSynchronized, List<Integer>> function) {
    assertThatThrownBy(() -> assertThatProxyTo(mock, realThing)
        .whenReceives(function, new InstanceOf<List<Integer>>() {
        }).thenLocksCorrectly()
    ).isInstanceOf(AssertionError.class);
    assertThat(Thread.holdsLock(realThing)).isFalse();
  }

  //TODO
  //4. release whenReceives throwing exception
}
