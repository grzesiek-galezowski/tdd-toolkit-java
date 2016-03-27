package com.github.grzesiek_galezowski.test_environment.implementation_details;

import org.testng.annotations.Test;

import static com.github.grzesiek_galezowski.test_environment.XAssert.assertThatNotThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

/**
 * Created by astral on 20.03.2016.
 */
public class LockAssertionsForMonitorSpecification {
  @Test
  public void shouldThrowWhenAssertingThatUnlockedMonitorIsLocked() {
    //GIVEN
    final Object monitor = new Object();
    final LockAssertionsForMonitor assertions = new LockAssertionsForMonitor(monitor);
    //WHEN - THEN
    assertThatThrownBy(() -> assertions.assertLocked())
        .hasMessageStartingWith("Expected this thread to hold a lock on ")
        .hasMessageEndingWith("during a call to wrapped method, but it didn't");

  }

  @Test
  public void shouldNotThrowWhenAssertingThatLockedMonitorIsLocked() {
    //GIVEN
    final Object monitor = new Object();
    final LockAssertionsForMonitor assertions = new LockAssertionsForMonitor(monitor);
    //WHEN - THEN
    synchronized (monitor) {
      assertThatNotThrownBy(() -> assertions.assertLocked());
    }
  }

  @Test
  public void shouldThrowWhenAssertingThatLockedMonitorIsUnlocked() {
    //GIVEN
    final Object monitor = new Object();
    final LockAssertionsForMonitor assertions = new LockAssertionsForMonitor(monitor);
    //WHEN - THEN
    synchronized (monitor) {
      assertThatThrownBy(() -> assertions.assertUnlocked())
          .hasMessageStartingWith("Expected this thread to not hold a lock on ")
          .hasMessageEndingWith("during a call to wrapped method, but it did");
    }

  }

  @Test
  public void shouldNotThrowWhenAssertingThatUnlockedMonitorIsUnlocked() {
    //GIVEN
    final Object monitor = new Object();
    final LockAssertionsForMonitor assertions = new LockAssertionsForMonitor(monitor);

    //WHEN - THEN
    assertThatNotThrownBy(() -> assertions.assertUnlocked());
  }


}