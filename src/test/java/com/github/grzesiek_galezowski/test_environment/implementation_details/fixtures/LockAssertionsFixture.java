package com.github.grzesiek_galezowski.test_environment.implementation_details.fixtures;

import com.github.grzesiek_galezowski.test_environment.implementation_details.LockAssertions;
import com.github.grzesiek_galezowski.test_environment.implementation_details.LockAssertionsForMonitor;
import com.github.grzesiek_galezowski.test_environment.implementation_details.LockAssertionsForReentrantLock;
import com.github.grzesiek_galezowski.test_environment.implementation_details.LockAssertionsForReentrantReadLock;
import com.github.grzesiek_galezowski.test_environment.implementation_details.LockAssertionsForReentrantWriteLock;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;

import static com.github.grzesiek_galezowski.test_environment.XAssert.assertThatNotThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class LockAssertionsFixture<T, U extends LockAssertions> {
  private T lock;
  private U assertions;

  public static LockAssertionsFixture<ReentrantLock, LockAssertionsForReentrantLock>
  createReentrantLockFixture() {
    return new LockAssertionsFixture<>(ReentrantLock::new,
        LockAssertionsForReentrantLock::new);
  }

  public static LockAssertionsFixture<ReentrantReadWriteLock, LockAssertionsForReentrantReadLock>
  createReentrantReadLockFixture() {
    return new LockAssertionsFixture<>(ReentrantReadWriteLock::new,
        LockAssertionsForReentrantReadLock::new);
  }

  public static LockAssertionsFixture<ReentrantReadWriteLock, LockAssertionsForReentrantWriteLock>
  createReentrantWriteLockFixture() {
    return new LockAssertionsFixture<>(ReentrantReadWriteLock::new,
        LockAssertionsForReentrantWriteLock::new);
  }

  public static LockAssertionsFixture<Object, LockAssertionsForMonitor>
  createMonitorFixture() {
    return new LockAssertionsFixture<>(Object::new, LockAssertionsForMonitor::new);
  }

  public void assertThatLockedAssertionPasses() {
    assertThatNotThrownBy(() -> assertLocked());
  }

  public void assertThatUnlockedAssertionPasses() {
    assertThatNotThrownBy(() -> assertUnlocked());
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
      final ParameterlessFunction<T> lockSource,
      final Function<T, U> assertionsSource) {

    lock = lockSource.create();
    assertions = assertionsSource.apply(lock);
  }
}

