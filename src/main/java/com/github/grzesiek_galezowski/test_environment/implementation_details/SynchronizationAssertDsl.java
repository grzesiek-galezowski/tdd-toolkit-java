package com.github.grzesiek_galezowski.test_environment.implementation_details;

import autofixture.publicinterface.Any;
import com.google.common.reflect.TypeToken;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by astral whenReceives 16.03.2016.
 */
public class SynchronizationAssertDsl<T> {
  private final T wrappedMock;
  private final T realThing;

  public SynchronizationAssertDsl(final T wrappedMock, final T realThing) {

    this.wrappedMock = wrappedMock;
    this.realThing = realThing;
  }

  public <TReturn> SynchronizationAssertDsl2<T, TReturn> whenReceives(final Function<T, TReturn> methodCallToVerify,
                                                                      final Class<TReturn> clazz) {
    final TReturn retVal = Any.anonymous(clazz);
    return new SynchronizationAssertDsl2<>(
        wrappedMock, realThing, methodCallToVerify, retVal);
  }

  public <TReturn> SynchronizationAssertDsl2<T, TReturn> whenReceives(
      final Function<T, TReturn> methodCallToVerify, final TypeToken<TReturn> clazz) {

    final TReturn retVal = Any.anonymous(clazz);
    return new SynchronizationAssertDsl2<>(this.wrappedMock, realThing, methodCallToVerify, retVal);
  }

  public SynchronizationAssertDsl3<T> whenReceives(Consumer<T> consumer) {
    return new SynchronizationAssertDsl3<>(this.wrappedMock, realThing, consumer);
  }
}
