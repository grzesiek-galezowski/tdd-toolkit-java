package com.github.grzesiek_galezowski.test_environment;

import autofixture.publicinterface.Any;
import autofixture.publicinterface.InstanceOf;
import com.github.grzesiek_galezowski.test_environment.fixtures.InterfaceToBeSynchronized;
import com.github.grzesiek_galezowski.test_environment.fixtures.SynchronizedWrapperOverInterfaceToBeSynchronized;
import org.testng.annotations.Test;

import java.util.List;
import java.util.function.Function;

import static com.github.grzesiek_galezowski.test_environment.XAssert.assertThatProxyTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

public class LockingFunctionsWithGenericReturnTypeAssertionsSpecification {
  private final InterfaceToBeSynchronized mock = mock(InterfaceToBeSynchronized.class);
  private final InterfaceToBeSynchronized realThing = new SynchronizedWrapperOverInterfaceToBeSynchronized(mock);
  private final Integer a = Any.intValue();
  private final Integer b = Any.intValue();

  @Test
  public void shouldPassWhenFunctionIsCalledCorrectlyInSynchronizedBlock() {
    //WHEN-THEN

    assertThatProxyTo(mock, realThing)
        .locksMonitorOn(instance -> instance.genericCorrectlySynchronizedFunction(a, b),
            new InstanceOf<List<Integer>>() {
            });
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
        .locksMonitorOn(function, new InstanceOf<List<Integer>>() {})
    ).isInstanceOf(AssertionError.class);
    assertThat(Thread.holdsLock(realThing)).isFalse();
  }

  //TODO
  //4. release on throwing exception
}
