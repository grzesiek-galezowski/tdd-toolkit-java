package com.github.grzesiek_galezowski.test_environment.implementation_details;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;

public abstract class AssertSynchronizedPrivate<T> {
  protected T wrappedInterfaceMock;
  protected T synchronizedProxy;

  public AssertSynchronizedPrivate(
      T wrappedInterfaceMock,
      T synchronizedProxy) {

    this.wrappedInterfaceMock = wrappedInterfaceMock;
    this.synchronizedProxy = synchronizedProxy;
  }

  public void invoke() {
    try {
      assertLockNotHeld();
      prepareMockForCall(wrappedInterfaceMock, synchronizedProxy);

      //WHEN
      callMethodOnProxy(synchronizedProxy);

      //THEN
      assertMethodResult();
      assertLockNotHeld();
    } finally {
      resetMock();
    }
  }

  private void resetMock() {
    reset(wrappedInterfaceMock);
  }

  private void assertLockNotHeld() {
    assertLockNotHeldOn(synchronizedProxy);
  }

  private static <T> void assertLockNotHeldOn(T synchronizedProxy) {
    assertThat(Thread.holdsLock(synchronizedProxy)).isFalse();
  }

  protected abstract void assertMethodResult();
  protected abstract void callMethodOnProxy(T synchronizedProxy);
  protected abstract void prepareMockForCall(T wrappedInterfaceMock, T synchronizedProxy);


}
