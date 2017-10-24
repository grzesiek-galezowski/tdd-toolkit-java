package com.github.grzesiek_galezowski.test_environment.implementation_details;

import lombok.val;
import org.testng.annotations.Test;


public class LockAssertionsForMonitorSpecification {
  @Test
  public void shouldThrowWhenAssertingThatUnlockedMonitorIsLocked() {
    //GIVEN
    val fixture = LockAssertionsFixture.createMonitorFixture();
    //WHEN - THEN
    fixture.assertThatLockedAssertionFails();

  }

  @Test
  public void shouldNotThrowWhenAssertingThatLockedMonitorIsLocked() {
    //GIVEN
    val fixture = LockAssertionsFixture.createMonitorFixture();
    //WHEN - THEN
    synchronized (fixture.getLock()) {
      fixture.assertThatLockedAssertionPasses();
    }
  }

  @Test
  public void shouldThrowWhenAssertingThatLockedMonitorIsUnlocked() {
    //GIVEN
    final LockAssertionsFixture<Object, LockAssertionsForMonitor> fixture =
        LockAssertionsFixture.createMonitorFixture();

    //WHEN - THEN
    synchronized (fixture.getLock()) {
      fixture.assertThatUnlockedAssertionFails();
    }

  }

  @Test
  public void shouldNotThrowWhenAssertingThatUnlockedMonitorIsUnlocked() {
    //GIVEN
    val fixture = LockAssertionsFixture.createMonitorFixture();

    //WHEN - THEN
    fixture.assertThatUnlockedAssertionPasses();
  }


}