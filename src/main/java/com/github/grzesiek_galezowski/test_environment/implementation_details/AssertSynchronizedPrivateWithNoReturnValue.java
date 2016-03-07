package com.github.grzesiek_galezowski.test_environment.implementation_details;

import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Created by astral on 07.03.2016.
 */
public class AssertSynchronizedPrivateWithNoReturnValue<T> {
  private final MyAssertSynchronizedPrivate assertSynchronizedPrivate;
  private Consumer<T> methodCallToVerify;

  public AssertSynchronizedPrivateWithNoReturnValue(
      T wrappedInterfaceMock, T synchronizedProxy, final Consumer<T> methodCallToVerify) {
    assertSynchronizedPrivate = new MyAssertSynchronizedPrivate(wrappedInterfaceMock, synchronizedProxy);
    AssertSynchronizedPrivateWithNoReturnValue.this.methodCallToVerify = methodCallToVerify;
  }

  private class MyAssertSynchronizedPrivate extends AssertSynchronizedPrivate<T> {
    public MyAssertSynchronizedPrivate(
        T wrappedInterfaceMock,
        T synchronizedProxy) {
      super(wrappedInterfaceMock, synchronizedProxy);
    }

    @Override
    protected void assertMethodResult() {
      methodCallToVerify.accept(verify(wrappedInterfaceMock));
    }

    @Override
    protected void callMethodOnProxy(T synchronizedProxy) {
      methodCallToVerify.accept(synchronizedProxy);
    }

    @Override
    protected void prepareMockForCall(T wrappedInterfaceMock, T synchronizedProxy) {
      methodCallToVerify.accept(
          Mockito.doAnswer((Answer<Void>) invocation -> {
            assertThat(Thread.holdsLock(synchronizedProxy)).isTrue();
            return null;
          }).when(wrappedInterfaceMock)
      );
    }
  }
}
