package com.github.grzesiek_galezowski.test_environment;

import org.mockito.stubbing.Answer;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by astral on 07.03.2016.
 */
public class AssertSynchronizedPrivateWithReturnValue<T, TReturn> extends AssertSynchronizedPrivate<T> {
  protected TReturn retVal;
  protected TReturn resultFromWrapper = null;
  protected Function<T, TReturn> methodCallToVerify;

  public AssertSynchronizedPrivateWithReturnValue(
      T wrappedInterfaceMock,
      T synchronizedProxy,
      Function<T, TReturn> methodCallToVerify,
      TReturn retVal) {
    super(wrappedInterfaceMock, synchronizedProxy);
    this.methodCallToVerify = methodCallToVerify;
    this.retVal = retVal;
  }

  @Override
  protected void assertMethodResult() {
    assertThat(resultFromWrapper).isEqualTo(retVal);
  }

  @Override
  protected void prepareMockForCall(T wrappedInterfaceMock, T synchronizedProxy) {
    when(methodCallToVerify.apply(wrappedInterfaceMock))
        .then((Answer<TReturn>) invocation -> {
          assertThat(Thread.holdsLock(synchronizedProxy)).isTrue();
          return retVal;
        });
  }

  @Override
  protected void callMethodOnProxy(T synchronizedProxy) {
    resultFromWrapper = methodCallToVerify.apply(synchronizedProxy);
  }
}
