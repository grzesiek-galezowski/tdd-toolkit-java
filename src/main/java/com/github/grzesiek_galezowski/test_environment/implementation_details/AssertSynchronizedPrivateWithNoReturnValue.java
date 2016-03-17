package com.github.grzesiek_galezowski.test_environment.implementation_details;

import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.function.Consumer;

import static org.mockito.Mockito.verify;

/**
 * Created by astral whenReceives 07.03.2016.
 */
public class AssertSynchronizedPrivateWithNoReturnValue<T> extends AssertSynchronizedPrivate<T> {
  private final Consumer<T> methodCallToVerify;

  public AssertSynchronizedPrivateWithNoReturnValue(
      final T wrappedInterfaceMock, final T synchronizedProxy, final Consumer<T> methodCallToVerify, final Object monitorObject) {
    super(wrappedInterfaceMock, synchronizedProxy, monitorObject);
    this.methodCallToVerify = methodCallToVerify;
  }

  @Override
  protected void assertMethodResult() {
    methodCallToVerify.accept(verify(getWrappedInterfaceMock()));
  }

  @Override
  protected void callMethodOnProxy(final T synchronizedProxy) {
    methodCallToVerify.accept(synchronizedProxy);
  }

  @Override
  protected void prepareMockForCall(final T wrappedInterfaceMock, final T synchronizedProxy) {
    methodCallToVerify.accept(
        Mockito.doAnswer((Answer<Void>) invocation -> {
          assertLockHeld();
          return null;
        }).when(wrappedInterfaceMock)
    );
  }

}
