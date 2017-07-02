package com.github.grzesiek_galezowski.test_environment;

import com.github.grzesiek_galezowski.test_environment.fixtures.InterfaceToBeSynchronized;
import com.github.grzesiek_galezowski.test_environment.fixtures.SyncAssertFixture;
import com.github.grzesiek_galezowski.test_environment.implementation_details.CheckedConsumer;
import lombok.val;
import org.testng.annotations.Test;

import static com.github.grzesiek_galezowski.test_environment.XAssert.assertThatProxyTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LockingVoidMethodAssertionsSpecification {

  @Test
  public void shouldPassWhenVoidMethodIsCalledCorrectlyInSynchronizedBlock() {
    //GIVEN
    val syncAssertFixture = SyncAssertFixture.create();
    //WHEN-THEN

    assertThatProxyTo(syncAssertFixture.getMock(), syncAssertFixture.getRealThing())
        .whenReceives(instance -> instance.correctlyWrappedVoidMethod(syncAssertFixture.getA(), syncAssertFixture.getB()))
        .thenLocksCorrectly();
    assertThat(Thread.holdsLock(syncAssertFixture.getRealThing())).isFalse();
  }

  @Test
  public void shouldPassWhenThrowingVoidMethodIsCalledCorrectlyInSynchronizedBlock() {
    //GIVEN
    val syncAssertFixture = SyncAssertFixture.create();
    //WHEN-THEN

    assertThatProxyTo(syncAssertFixture.getMock(), syncAssertFixture.getRealThing())
        .whenReceives(instance -> instance.correctlyWrappedThrowingVoidMethod(syncAssertFixture.getA(), syncAssertFixture.getB()))
        .thenLocksCorrectly();
    assertThat(Thread.holdsLock(syncAssertFixture.getRealThing())).isFalse();
  }


  @Test
  public void shouldFailWhenVoidMethodIsCalledCorrectlyButNotInSynchronizedBlock() {
    //GIVEN
    val syncAssertFixture = SyncAssertFixture.create();

    //WHEN-THEN
    assertExceptionIsThrownOn(
        instance -> instance.correctlyCalledButNotSynchronizedVoidMethod(syncAssertFixture.getA(), syncAssertFixture.getB()));
  }

  @Test
  public void shouldFailWhenVoidMethodIsNotCalledAtAll() {
    //GIVEN
    val syncAssertFixture = SyncAssertFixture.create();

    //WHEN-THEN
    assertExceptionIsThrownOn(instance -> instance.voidMethodNotCalledAtAll(syncAssertFixture.getA(), syncAssertFixture.getB()));
  }

  @Test
  public void shouldFailWhenVoidMethodIsSynchronizedButCalledWithWrongArguments() {
    //GIVEN
    val syncAssertFixture = SyncAssertFixture.create();

    //WHEN-THEN
    assertExceptionIsThrownOn(instance -> {
      instance.voidMethodCalledWithWrongArguments(syncAssertFixture.getA(), syncAssertFixture.getB());
    });
  }

  private void assertExceptionIsThrownOn(final CheckedConsumer<InterfaceToBeSynchronized> consumer) {
    //GIVEN
    val syncAssertFixture = SyncAssertFixture.create();

    assertThatThrownBy(() -> assertThatProxyTo(
        syncAssertFixture.getMock(), syncAssertFixture.getRealThing())
        .whenReceives(consumer).thenLocksCorrectly()
    ).isInstanceOf(AssertionError.class);
    assertThat(Thread.holdsLock(syncAssertFixture.getRealThing())).isFalse();
  }


  //TODO tests for:
  //4. release whenReceives throwing exception
}

