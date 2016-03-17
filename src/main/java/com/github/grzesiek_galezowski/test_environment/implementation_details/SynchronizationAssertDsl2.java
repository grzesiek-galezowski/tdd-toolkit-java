package com.github.grzesiek_galezowski.test_environment.implementation_details;

import java.util.function.Function;

/**
 * Created by astral whenReceives 17.03.2016.
 */
public class SynchronizationAssertDsl2<T, TReturn> {
  private final T wrappedMock;
  private final T realThing;
  private final Function<T, TReturn> methodCallToVerify;
  private final TReturn retVal;

  public SynchronizationAssertDsl2(final T wrappedMock, final T realThing, final Function<T, TReturn> methodCallToVerify, final TReturn retVal) {

    this.wrappedMock = wrappedMock;
    this.realThing = realThing;
    this.methodCallToVerify = methodCallToVerify;
    this.retVal = retVal;
  }

  public void thenLocksCorrectly() {
    thenLocksCorrectlyOn(realThing);
  }

  public void thenLocksCorrectlyOn(final Object monitorObject) {
    new AssertSynchronizedPrivateWithReturnValue<>(
        wrappedMock, realThing, methodCallToVerify, retVal, monitorObject).invoke();
  }

}
