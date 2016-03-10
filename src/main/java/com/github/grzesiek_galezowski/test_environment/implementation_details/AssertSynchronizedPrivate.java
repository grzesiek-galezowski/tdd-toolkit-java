package com.github.grzesiek_galezowski.test_environment.implementation_details;

import javax.annotation.Nonnull;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;

public abstract class AssertSynchronizedPrivate<T> {
  private T wrappedInterfaceMock;
  private T synchronizedProxy;

  public AssertSynchronizedPrivate(
      final T wrappedInterfaceMock,
      final T synchronizedProxy) {

    this.setWrappedInterfaceMock(wrappedInterfaceMock);
    this.setSynchronizedProxy(synchronizedProxy);
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

  protected void setWrappedInterfaceMock(final T wrappedInterfaceMock) {
    this.wrappedInterfaceMock = wrappedInterfaceMock;
  }

  protected T getSynchronizedProxy() {
    return synchronizedProxy;
  }

  protected void setSynchronizedProxy(final T synchronizedProxy) {
    this.synchronizedProxy = synchronizedProxy;
  }
}
