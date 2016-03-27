package com.github.grzesiek_galezowski.test_environment.implementation_details;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.locks.ReentrantLock;

import static com.github.grzesiek_galezowski.test_environment.XAssert.assertThatNotThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

/**
 * Created by astral on 20.03.2016.
 */
public class LockAssertionsForReentrantLockSpecification {

  private ReentrantLock lock;
  private LockAssertionsForReentrantLock assertions;

  @BeforeMethod
  public void initialize() {
    lock = new ReentrantLock();
    assertions = new LockAssertionsForReentrantLock(lock);
  }

  @Test
  public void shouldThrowWhenAssertingThatUnlockedReentrantLockIsLocked() {
    //WHEN - THEN
    assertThatThrownBy(() -> assertions.assertLocked())
        .hasMessageStartingWith("Expected this thread to hold a lock on ")
        .hasMessageEndingWith("during a call to wrapped method, but it didn't");

  }

  @Test
  public void shouldNotThrowWhenAssertingThatLockedReentrantLockIsLocked() {
    //WHEN - THEN
    lock.lock();
    assertThatNotThrownBy(() -> assertions.assertLocked());
    lock.unlock();
  }

  @Test
  public void shouldThrowWhenAssertingThatLockedReentrantLockIsUnlocked() {
    //WHEN - THEN
    lock.lock();
    assertThatThrownBy(() -> assertions.assertUnlocked())
        .hasMessageStartingWith("Expected this thread to not hold a lock on ")
        .hasMessageEndingWith("during a call to wrapped method, but it did");
    lock.unlock();
  }

  @Test
  public void shouldNotThrowWhenAssertingThatUnlockedReentrantLockIsUnlocked() {
    //WHEN - THEN
    assertThatNotThrownBy(() -> assertions.assertUnlocked());
  }

}