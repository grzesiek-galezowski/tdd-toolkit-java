package com.github.grzesiek_galezowski.test_environment;

import autofixture.publicinterface.Any;
import com.github.grzesiek_galezowski.test_environment.fixtures.InterfaceToBeSynchronized;
import com.github.grzesiek_galezowski.test_environment.fixtures.SynchronizedWrapperOverInterfaceToBeSynchronized;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.function.Consumer;

import static com.github.grzesiek_galezowski.test_environment.XAssert.assertThatProxyTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

public class LockingVoidMethodAssertionsSpecification {

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
  public void shouldPassWhenVoidMethodIsCalledCorrectlyInSynchronizedBlock() {
    //WHEN-THEN

    assertThatProxyTo(mock, realThing)
        .whenReceives(instance -> instance.correctlyWrappedVoidMethod(a, b))
        .thenLocksCorrectly();
    assertThat(Thread.holdsLock(realThing)).isFalse();
  }

  @Test
  public void shouldFailWhenVoidMethodIsCalledCorrectlyButNotInSynchronizedBlock() {
    //WHEN-THEN
    assertExceptionIsThrownOn(
        instance -> instance.correctlyCalledButNotSynchronizedVoidMethod(a, b));
  }

  @Test
  public void shouldFailWhenVoidMethodIsNotCalledAtAll() {
    //WHEN-THEN
    assertExceptionIsThrownOn(instance -> instance.voidMethodNotCalledAtAll(a, b));
  }

  @Test
  public void shouldFailWhenVoidMethodIsSynchronizedButCalledWithWrongArguments() {
    //WHEN-THEN
    assertExceptionIsThrownOn(instance -> {
      instance.voidMethodCalledWithWrongArguments(a, b);
    });
  }

  private void assertExceptionIsThrownOn(final Consumer<InterfaceToBeSynchronized> consumer) {
    assertThatThrownBy(() -> assertThatProxyTo(
        mock, realThing).whenReceives(consumer).thenLocksCorrectly()
    ).isInstanceOf(AssertionError.class);
    assertThat(Thread.holdsLock(realThing)).isFalse();
  }


  //TODO tests for:
  //4. release whenReceives throwing exception
}
