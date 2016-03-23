package com.github.grzesiek_galezowski.test_environment.implementation_details;

import org.testng.annotations.Test;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.github.grzesiek_galezowski.test_environment.XAssert.assertThatNotThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

/**
 * Created by astral on 20.03.2016.
 */
public class LockAssertionsForReentrantWriteLockSpecification {

  private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
  private final LockAssertionsForReentrantWriteLock assertions = new LockAssertionsForReentrantWriteLock(lock);

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
    lock.writeLock().lock();
    assertThatNotThrownBy(() -> assertions.assertLocked());
    lock.writeLock().unlock();
  }

  @Test
  public void shouldThrowWhenAssertingThatLockedReentrantLockIsUnlocked() {
    //WHEN - THEN
    lock.writeLock().lock();
    assertThatThrownBy(() -> assertions.assertUnlocked())
        .hasMessageStartingWith("Expected this thread to not hold a lock on ")
        .hasMessageEndingWith("during a call to wrapped method, but it did");
    lock.writeLock().unlock();
  }

  @Test
  public void shouldNotThrowWhenAssertingThatUnlockedReentrantLockIsUnlocked() {
    //WHEN - THEN
    assertThatNotThrownBy(() -> assertions.assertUnlocked());
  }

}