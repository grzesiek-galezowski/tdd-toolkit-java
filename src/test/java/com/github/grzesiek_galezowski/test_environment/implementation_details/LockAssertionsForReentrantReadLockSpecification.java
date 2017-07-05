package com.github.grzesiek_galezowski.test_environment.implementation_details;

import com.github.grzesiek_galezowski.test_environment.implementation_details.fixtures.LockAssertionsFixture;
import lombok.val;
import org.testng.annotations.Test;

/**
 * Created by astral on 20.03.2016.
 */
public class LockAssertionsForReentrantReadLockSpecification {

  @Test
  public void shouldThrowWhenAssertingThatUnlockedReentrantLockIsLocked() {
    //GIVEN
    val fixture = LockAssertionsFixture.createReentrantReadLockFixture();

    //WHEN - THEN
    fixture.assertThatLockedAssertionFails();

  }

  @Test
  public void shouldNotThrowWhenAssertingThatLockedReentrantLockIsLocked() {
    //GIVEN
    val fixture = LockAssertionsFixture.createReentrantReadLockFixture();

    //WHEN - THEN
    fixture.getLock().readLock().lock();
    fixture.assertThatLockedAssertionPasses();
    fixture.getLock().readLock().unlock();
  }

  @Test
  public void shouldThrowWhenAssertingThatLockedReentrantLockIsUnlocked() {
    //GIVEN
    val fixture = LockAssertionsFixture.createReentrantReadLockFixture();

    //WHEN - THEN
    fixture.getLock().readLock().lock();
    fixture.assertThatUnlockedAssertionFails();
    fixture.getLock().readLock().unlock();
  }

  @Test
  public void shouldNotThrowWhenAssertingThatUnlockedReentrantLockIsUnlocked() {
    //GIVEN
    val fixture = LockAssertionsFixture.createReentrantReadLockFixture();

    //WHEN - THEN
    fixture.assertThatUnlockedAssertionPasses();
  }


}