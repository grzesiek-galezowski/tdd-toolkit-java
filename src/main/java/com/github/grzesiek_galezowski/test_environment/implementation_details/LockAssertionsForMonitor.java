package com.github.grzesiek_galezowski.test_environment.implementation_details;

import org.assertj.core.api.Assertions;

public class LockAssertionsForMonitor implements LockAssertions {
  private final Object monitorObject;

  public LockAssertionsForMonitor(final Object monitorObject) {
    this.monitorObject = monitorObject;
  }

  @Override
  public void assertUnlocked() {
    assertLockNotHeldOn(monitorObject);
  }

  @Override
  public void assertLocked() {
    assertThreadHoldsALockOn(monitorObject);
  }

  private static void assertLockNotHeldOn(final Object monitor) {
    Assertions.assertThat(Thread.holdsLock(monitor)).withFailMessage("Expected this thread to not hold a lock on " + monitor + " during a call to wrapped method, but it did").isFalse();
  }

  private void assertThreadHoldsALockOn(final Object monitor) {
    Assertions.assertThat(Thread.holdsLock(monitor)).withFailMessage("Expected this thread to hold a lock on " + monitor + " during a call to wrapped method, but it didn't").isTrue();
  }
}