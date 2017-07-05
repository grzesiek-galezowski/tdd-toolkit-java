package com.github.grzesiek_galezowski.test_environment.implementation_details;

import com.github.grzesiek_galezowski.test_environment.implementation_details.fixtures.LockAssertionsFixture;
import lombok.val;
import org.testng.annotations.Test;

/**
 * Created by astral on 20.03.2016.
 */
public class LockAssertionsForReentrantLockSpecification {


  @Test
  public void shouldThrowWhenAssertingThatUnlockedReentrantLockIsLocked() {
    //GIVEN
    val lockAssertionsFixture = LockAssertionsFixture.createReentrantLockFixture();
    //WHEN - THEN
    lockAssertionsFixture.assertThatLockedAssertionFails();

  }

  @Test
  public void shouldNotThrowWhenAssertingThatLockedReentrantLockIsLocked() {
    //GIVEN
    val lockAssertionsFixture = LockAssertionsFixture.createReentrantLockFixture();

    //WHEN - THEN
    lockAssertionsFixture.getLock().lock();
    lockAssertionsFixture.assertThatLockedAssertionPasses();
    lockAssertionsFixture.getLock().unlock();
  }

  @Test
  public void shouldThrowWhenAssertingThatLockedReentrantLockIsUnlocked() {
    //GIVEN
    val lockAssertionsFixture = LockAssertionsFixture.createReentrantLockFixture();

    //WHEN - THEN
    lockAssertionsFixture.getLock().lock();
    lockAssertionsFixture.assertThatUnlockedAssertionFails();
    lockAssertionsFixture.getLock().unlock();
  }

  @Test
  public void shouldNotThrowWhenAssertingThatUnlockedReentrantLockIsUnlocked() {
    //GIVEN
    val lockAssertionsFixture = LockAssertionsFixture.createReentrantLockFixture();

    //WHEN - THEN
    lockAssertionsFixture.assertThatUnlockedAssertionPasses();
  }

}