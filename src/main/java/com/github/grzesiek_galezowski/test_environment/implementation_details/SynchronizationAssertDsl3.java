package com.github.grzesiek_galezowski.test_environment.implementation_details;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

/**
 * Created by astral on 18.03.2016.
 */
public class SynchronizationAssertDsl3<T> {
  private final T wrappedMock;
  private final T realThing;
  private final Consumer<T> consumer;

  public SynchronizationAssertDsl3(final T wrappedMock, final T realThing, final Consumer<T> consumer) {
    super();
    this.wrappedMock = wrappedMock;
    this.realThing = realThing;
    this.consumer = consumer;
  }

  public void thenLocksCorrectly() {
    thenLocksCorrectlyOn(realThing);
  }

  @Nonnull
  public void thenLocksCorrectlyOn(final Object monitorObject) {
    new AssertSynchronizedPrivateWithNoReturnValue<>(wrappedMock, realThing, consumer, monitorObject)
        .invoke();
  }
}
