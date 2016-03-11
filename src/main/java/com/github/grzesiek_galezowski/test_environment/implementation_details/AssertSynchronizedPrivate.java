package com.github.grzesiek_galezowski.test_environment.implementation_details;

import javax.annotation.Nonnull;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;

public abstract class AssertSynchronizedPrivate<T> {
  private final T wrappedInterfaceMock;
  private final T synchronizedProxy;

  public AssertSynchronizedPrivate(
      final T wrappedInterfaceMock,
      final T synchronizedProxy) {

    this.wrappedInterfaceMock = wrappedInterfaceMock;
    this.synchronizedProxy = synchronizedProxy;
  }

  public void invoke() {
    try {
      assertLockNotHeld();
      prepareMockForCall(getWrappedInterfaceMock(), getSynchronizedProxy());

      //WHEN
      callMethodOnProxy(getSynchronizedProxy());

      //THEN
      assertMethodResult();
      assertLockNotHeld();
    } finally {
      resetMock();
    }
  }

  private void resetMock() {
    reset(getWrappedInterfaceMock());
  }

  private void assertLockNotHeld() {
    assertLockNotHeldOn(getSynchronizedProxy());
  }


  private static <T> void assertLockNotHeldOn(@Nonnull final T synchronizedProxy) {
    assertThat(Thread.holdsLock(synchronizedProxy)).isFalse();
  }

  protected abstract void assertMethodResult();

  protected abstract void callMethodOnProxy(@Nonnull T synchronizedProxy);

  protected abstract void prepareMockForCall(@Nonnull T wrappedInterfaceMock, T synchronizedProxy);


  protected T getWrappedInterfaceMock() {
    return wrappedInterfaceMock;
  }

  protected T getSynchronizedProxy() {
    return synchronizedProxy;
  }

}
