package com.github.grzesiek_galezowski.test_environment.implementation_details;

import org.mockito.stubbing.Answer;

import javax.annotation.Nullable;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by astral on 07.03.2016.
 */
public class AssertSynchronizedPrivateWithReturnValue<T, TReturn> extends AssertSynchronizedPrivate<T> {
  private final TReturn retVal;
  @Nullable
  private TReturn resultFromWrapper;
  private final Function<T, TReturn> methodCallToVerify;

  public AssertSynchronizedPrivateWithReturnValue(
      final T wrappedInterfaceMock,
      final T synchronizedProxy,
      final Function<T, TReturn> methodCallToVerify,
      final TReturn retVal) {
    super(wrappedInterfaceMock, synchronizedProxy);
    this.methodCallToVerify = methodCallToVerify;
    this.retVal = retVal;
  }

  @Override
  protected void assertMethodResult() {
    assertThat(resultFromWrapper).isEqualTo(retVal);
  }

  @Override
  protected void prepareMockForCall(final T wrappedInterfaceMock, final T synchronizedProxy) {
    when(methodCallToVerify.apply(wrappedInterfaceMock))
        .then((Answer<TReturn>) invocation -> {
          assertThat(Thread.holdsLock(synchronizedProxy)).isTrue();
          return retVal;
        });
  }

  @Override
  protected void callMethodOnProxy(final T synchronizedProxy) {
    resultFromWrapper = methodCallToVerify.apply(synchronizedProxy);
  }
}
