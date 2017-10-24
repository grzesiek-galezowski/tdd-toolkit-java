package com.github.grzesiek_galezowski.test_environment.implementation_details;

import lombok.val;
import org.testng.annotations.Test;

/**
 * Created by astral on 20.03.2016.
 */
public class LockAssertionsForReentrantWriteLockSpecification {

  @Test
  public void shouldThrowWhenAssertingThatUnlockedReentrantLockIsLocked() {
    //GIVEN
    val fixture = LockAssertionsFixture.createReentrantWriteLockFixture();

    //WHEN - THEN
    fixture.assertThatLockedAssertionFails();

  }

  @Test
  public void shouldNotThrowWhenAssertingThatLockedReentrantLockIsLocked() {
    //GIVEN
    val fixture = LockAssertionsFixture.createReentrantWriteLockFixture();

    //WHEN - THEN
    fixture.getLock().writeLock().lock();
    fixture.assertThatLockedAssertionPasses();
    fixture.getLock().writeLock().unlock();
  }

  @Test
  public void shouldThrowWhenAssertingThatLockedReentrantLockIsUnlocked() {
    //GIVEN
    val fixture = LockAssertionsFixture.createReentrantWriteLockFixture();

    //WHEN - THEN
    fixture.getLock().writeLock().lock();
    fixture.assertThatUnlockedAssertionFails();
    fixture.getLock().writeLock().unlock();
  }

  @Test
  public void shouldNotThrowWhenAssertingThatUnlockedReentrantLockIsUnlocked() {
    //GIVEN
    val fixture = LockAssertionsFixture.createReentrantWriteLockFixture();

    //WHEN - THEN
    fixture.assertThatUnlockedAssertionPasses();
  }

}