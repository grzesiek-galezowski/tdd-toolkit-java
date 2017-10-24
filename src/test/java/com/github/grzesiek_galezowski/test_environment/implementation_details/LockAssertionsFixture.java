package com.github.grzesiek_galezowski.test_environment.implementation_details;

import lombok.val;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class LockAssertionsFixture<T, U extends LockAssertions> {
  private T lock;
  private U assertions;

  public static LockAssertionsFixture<ReentrantLock, LockAssertionsForReentrantLock>
  createReentrantLockFixture() {
    val reentrantLock = new ReentrantLock();
    return new LockAssertionsFixture<>(
        reentrantLock, new LockAssertionsForReentrantLock(reentrantLock));
  }

  public static LockAssertionsFixture<ReentrantReadWriteLock, LockAssertionsForReentrantReadLock>
  createReentrantReadLockFixture() {
    val reentrantReadWriteLock = new ReentrantReadWriteLock();
    return new LockAssertionsFixture<>(
        reentrantReadWriteLock, new LockAssertionsForReentrantReadLock(reentrantReadWriteLock));
  }

  public static LockAssertionsFixture<ReentrantReadWriteLock, LockAssertionsForReentrantWriteLock>
  createReentrantWriteLockFixture() {
    val reentrantReadWriteLock = new ReentrantReadWriteLock();
    return new LockAssertionsFixture<>(
        reentrantReadWriteLock, new LockAssertionsForReentrantWriteLock(reentrantReadWriteLock));
  }

  public static LockAssertionsFixture<Object, LockAssertionsForMonitor>
  createMonitorFixture() {
    val o = new Object();
    return new LockAssertionsFixture<>(o, new LockAssertionsForMonitor(o));
  }

  public void assertThatLockedAssertionPasses() {
    assertThatCode(() -> assertLocked()).doesNotThrowAnyException();
  }

  public void assertThatUnlockedAssertionPasses() {
    assertThatCode(() -> assertUnlocked()).doesNotThrowAnyException();
  }


  public void assertThatUnlockedAssertionFails() {
    assertThatThrownBy(() -> assertUnlocked())
        .hasMessageStartingWith("Expected this thread to not hold a lock on ")
        .hasMessageEndingWith("during a call to wrapped method, but it did");
  }

  public void assertThatLockedAssertionFails() {
    assertThatThrownBy(() -> assertLocked())
        .hasMessageStartingWith("Expected this thread to hold a lock on ")
        .hasMessageEndingWith("during a call to wrapped method, but it didn't");
  }

  public void assertLocked() {
    assertions.assertLocked();
  }

  public void assertUnlocked() {
    assertions.assertUnlocked();
  }


  public T getLock() {
    return lock;
  }

  public LockAssertionsFixture(
      final T lock, final U assertions) {

    this.lock = lock;
    this.assertions = assertions;
  }
}

