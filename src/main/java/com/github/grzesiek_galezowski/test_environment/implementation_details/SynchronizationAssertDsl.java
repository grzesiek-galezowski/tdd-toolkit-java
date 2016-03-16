package com.github.grzesiek_galezowski.test_environment.implementation_details;

import autofixture.publicinterface.Any;
import com.google.common.reflect.TypeToken;

import java.util.function.Function;

/**
 * Created by astral on 16.03.2016.
 */
public class SynchronizationAssertDsl<T> {
  private final T wrappedMock;
  private final T realThing;

  public SynchronizationAssertDsl(final T wrappedMock, final T realThing) {

    this.wrappedMock = wrappedMock;
    this.realThing = realThing;
  }

  public <TReturn> void locksMonitorOn(final Function<T, TReturn> methodCallToVerify,
                                       final Class<TReturn> clazz) {
    final TReturn retVal = Any.anonymous(clazz);
    new AssertSynchronizedPrivateWithReturnValue<>(
        wrappedMock, realThing, methodCallToVerify, retVal).invoke();
  }

  public <TReturn> void locksMonitorOn(
      final Function<T, TReturn> methodCallToVerify, final TypeToken<TReturn> clazz) {

    final TReturn retVal = Any.anonymous(clazz);
    new AssertSynchronizedPrivateWithReturnValue<>(
        this.wrappedMock, realThing, methodCallToVerify, retVal).invoke();
  }
}
