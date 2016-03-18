package com.github.grzesiek_galezowski.test_environment.implementation_details;

import org.assertj.core.api.Assertions;

import javax.annotation.Nonnull;

public class LockAssertions<T> {
  private final Object monitorObject;

  public LockAssertions(final Object monitorObject) {
    this.monitorObject = monitorObject;
  }

  protected void assertLockNotHeld() {
    assertLockNotHeldOn(monitorObject);
  }

  protected void assertLockHeld() {
    assertThreadHoldsALockOn(monitorObject);
  }

  static <T> void assertLockNotHeldOn(@Nonnull final Object monitor) {
    Assertions.assertThat(Thread.holdsLock(monitor)).isFalse();
  }

  void assertThreadHoldsALockOn(final Object monitor) {
    Assertions.assertThat(Thread.holdsLock(monitor)).withFailMessage("Expected this thread to hold a lock whenReceives " + monitor + " during a call to wrapped method, but it didn't").isTrue();
  }
}