package com.github.grzesiek_galezowski.test_environment;

import com.github.grzesiek_galezowski.test_environment.fixtures.InterfaceToBeSynchronized;
import com.github.grzesiek_galezowski.test_environment.fixtures.SyncAssertFixture;
import com.github.grzesiek_galezowski.test_environment.implementation_details.CheckedFunction;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LockingFunctionsAssertionsSpecification {

  @Test
  public void shouldPassWhenFunctionIsCalledCorrectlyInSynchronizedBlock() {
    //GIVEN
    final SyncAssertFixture fixture = new SyncAssertFixture();
    //WHEN-THEN

    XAssert.assertThatProxyTo(fixture.getMock(), fixture.getRealThing())
        .whenReceives(instance -> instance.correctlyWrappedFunction(fixture.getA(), fixture.getB()), Integer.class).thenLocksCorrectly();
    assertThat(Thread.holdsLock(fixture.getRealThing())).isFalse();
  }

  @Test
  public void shouldPassWhenThrowingFunctionIsCalledCorrectlyInSynchronizedBlock() {
    //GIVEN
    final SyncAssertFixture fixture = new SyncAssertFixture();
    //WHEN-THEN
    final CheckedFunction<InterfaceToBeSynchronized, Integer> methodCallToVerify =
        instance -> instance.correctlyWrappedThrowingFunction(fixture.getA(), fixture.getB());

    XAssert.assertThatProxyTo(fixture.getMock(), fixture.getRealThing())
        .whenReceives(methodCallToVerify, Integer.class).thenLocksCorrectly();
    assertThat(Thread.holdsLock(fixture.getRealThing())).isFalse();
  }


  @Test
  public void shouldFailWhenVoidMethodIsCalledCorrectlyButNotInSynchronizedBlock() {
    //GIVEN
    final SyncAssertFixture fixture = new SyncAssertFixture();

    //WHEN-THEN
    assertThrowsWhen(instance -> instance.correctlyCalledButNotSynchronizedFunction(
        fixture.getA(), fixture.getB()), fixture);
  }

  @Test
  public void shouldFailWhenFunctionIsNotCalledAtAll() {
    //GIVEN
    final SyncAssertFixture fixture = new SyncAssertFixture();

    //WHEN-THEN
    assertThrowsWhen(instance -> instance.functionNotCalledAtAll(
        fixture.getA(), fixture.getB()), fixture);
  }

  @Test
  public void shouldFailWhenFunctionIsSynchronizedButCalledWithWrongArguments() {
    //GIVEN
    final SyncAssertFixture fixture = new SyncAssertFixture();

    //WHEN-THEN
    assertThrowsWhen(instance -> instance.functionCalledWithWrongArguments(
        fixture.getA(), fixture.getB()), fixture);
  }

  @Test
  public void shouldFailWhenFunctionIsSynchronizedButItsReturnValueIsNotPropagatedBack() {
    //GIVEN
    final SyncAssertFixture fixture = new SyncAssertFixture();

    //WHEN-THEN
    assertThrowsWhen(instance -> instance.functionWithNonPropagatedReturnValue(
        fixture.getA(), fixture.getB()), fixture);
  }

  private void assertThrowsWhen(final CheckedFunction<InterfaceToBeSynchronized, Integer> function, final SyncAssertFixture syncAssertFixture) {
    assertThatThrownBy(() -> XAssert.assertThatProxyTo(syncAssertFixture.getMock(), syncAssertFixture.getRealThing())
        .whenReceives(function, Integer.class).thenLocksCorrectly()
    ).isInstanceOf(AssertionError.class);
    assertThat(Thread.holdsLock(syncAssertFixture.getRealThing())).isFalse();
  }
  //4. release whenReceives throwing exception
}
