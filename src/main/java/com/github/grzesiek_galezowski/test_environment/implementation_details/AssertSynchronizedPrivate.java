package com.github.grzesiek_galezowski.test_environment.implementation_details;

import javax.annotation.Nonnull;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;

public abstract class AssertSynchronizedPrivate<T> {
  private final T wrappedInterfaceMock;
  private final T synchronizedProxy;
  private final Object monitorObject;

  public AssertSynchronizedPrivate(
      final T wrappedInterfaceMock,
      final T synchronizedProxy, final Object monitorObject) {

    this.wrappedInterfaceMock = wrappedInterfaceMock;
    this.synchronizedProxy = synchronizedProxy;
    this.monitorObject = monitorObject;
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

  protected void assertLockNotHeld() {
    assertLockNotHeldOn(getMonitorObject());
  }

  protected void assertLockHeld() {
    assertThreadHoldsALockOn(getMonitorObject());
  }


  private static <T> void assertLockNotHeldOn(@Nonnull final Object monitor) {
    assertThat(Thread.holdsLock(monitor)).isFalse();
  }

  private void assertThreadHoldsALockOn(final Object monitor) {
    assertThat(Thread.holdsLock(monitor)).withFailMessage("Expected this thread to hold a lock whenReceives " + monitor + " during a call to wrapped method, but it didn't").isTrue();
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

  protected Object getMonitorObject() {
    return monitorObject;
  }
}
