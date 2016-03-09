package com.github.grzesiek_galezowski.test_environment.implementation_details;

import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Created by astral on 07.03.2016.
 */
public class AssertSynchronizedPrivateWithNoReturnValue<T> extends AssertSynchronizedPrivate<T> {
  private final Consumer<T> methodCallToVerify;

  public AssertSynchronizedPrivateWithNoReturnValue(
      final T wrappedInterfaceMock, final T synchronizedProxy, final Consumer<T> methodCallToVerify) {
    super(wrappedInterfaceMock, synchronizedProxy);
    this.methodCallToVerify = methodCallToVerify;
  }

  @Override
  protected void assertMethodResult() {
    methodCallToVerify.accept(verify(wrappedInterfaceMock));
  }

  @Override
  protected void callMethodOnProxy(final T synchronizedProxy) {
    methodCallToVerify.accept(synchronizedProxy);
  }

  @Override
  protected void prepareMockForCall(final T wrappedInterfaceMock, final T synchronizedProxy) {
    methodCallToVerify.accept(
        Mockito.doAnswer((Answer<Void>) invocation -> {
          assertThreadHoldsALockOn(synchronizedProxy);
          return null;
        }).when(wrappedInterfaceMock)
    );
  }

  private Object assertThreadHoldsALockOn(final T synchronizedProxy) {
    return assertThat(Thread.holdsLock(synchronizedProxy)).withFailMessage("Expected this thread to hold a lock on " + synchronizedProxy + " during a call to wrapped method, but it didn't").isTrue();
  }
}
