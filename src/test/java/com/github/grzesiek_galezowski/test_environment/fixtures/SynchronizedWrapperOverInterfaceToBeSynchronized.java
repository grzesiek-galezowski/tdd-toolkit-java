package com.github.grzesiek_galezowski.test_environment.fixtures;

import java.util.List;

/**
 * Created by astral on 06.03.2016.
 */
public class SynchronizedWrapperOverInterfaceToBeSynchronized implements InterfaceToBeSynchronized {

  private final InterfaceToBeSynchronized iface;

  public SynchronizedWrapperOverInterfaceToBeSynchronized(final InterfaceToBeSynchronized iface) {

    this.iface = iface;
  }

  @Override
  public void correctlyWrappedVoidMethod(final int a, final int b) {
    synchronized (this) {
      iface.correctlyWrappedVoidMethod(a, b);
    }
  }

  @Override
  public int methodWithReturn(final int a, final int b) {
    synchronized (this) {
      return iface.methodWithReturn(a, b);
    }
  }

  @Override
  public List<Integer> methodWithGenericReturn(final int a, final int b) {
    synchronized (this) {
      return iface.methodWithGenericReturn(a, b);
    }
  }

  @Override
  public void correctlyCalledButNotSynchronizedVoidMethod(final Integer a, final Integer b) {
    iface.correctlyCalledButNotSynchronizedVoidMethod(a, b);
  }

  @Override
  public void voidMethodNotCalledAtAll(final Integer a, final Integer b) {
    synchronized (this) {
      final int x = 1;
    }
  }

  @Override
  public void voidMethodCalledWithWrongArguments(final Integer a, final Integer b) {
    synchronized (this) {
      iface.voidMethodCalledWithWrongArguments(b, a);
    }
  }
}
