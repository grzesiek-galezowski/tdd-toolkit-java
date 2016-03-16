package com.github.grzesiek_galezowski.test_environment;

import autofixture.publicinterface.Any;
import com.github.grzesiek_galezowski.test_environment.fixtures.InterfaceToBeSynchronized;
import com.github.grzesiek_galezowski.test_environment.fixtures.SynchronizedWrapperOverInterfaceToBeSynchronized;
import org.testng.annotations.Test;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

public class LockingVoidMethodAssertionsSpecification {

  private final InterfaceToBeSynchronized mock = mock(InterfaceToBeSynchronized.class);
  private final InterfaceToBeSynchronized realThing = new SynchronizedWrapperOverInterfaceToBeSynchronized(mock);
  private final Integer a = Any.intValue();
  private final Integer b = Any.intValue();

  @Test
  public void shouldPassWhenVoidMethodIsCalledCorrectlyInSynchronizedBlock() {
    //WHEN-THEN
    XAssert.assertSynchronizedVoidMethod(mock, realThing,
        instance -> instance.correctlyWrappedVoidMethod(a, b));
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
    assertThatThrownBy(() ->
        XAssert.assertSynchronizedVoidMethod(mock, realThing, consumer)
    ).isInstanceOf(AssertionError.class);
    assertThat(Thread.holdsLock(realThing)).isFalse();
  }


  //TODO tests for:
  //4. release on throwing exception
}