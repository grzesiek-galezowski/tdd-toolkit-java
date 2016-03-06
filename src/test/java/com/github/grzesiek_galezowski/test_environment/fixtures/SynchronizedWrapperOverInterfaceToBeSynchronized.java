package com.github.grzesiek_galezowski.test_environment.fixtures;

import java.util.List;

/**
 * Created by astral on 06.03.2016.
 */
public class SynchronizedWrapperOverInterfaceToBeSynchronized implements InterfaceToBeSynchronized {

  private InterfaceToBeSynchronized iface;

  public SynchronizedWrapperOverInterfaceToBeSynchronized(InterfaceToBeSynchronized iface) {

    this.iface = iface;
  }

  @Override
  public void voidMethod(int a, int b) {
    synchronized(this) {
      iface.voidMethod(a,b);
    }
  }

  @Override
  public int methodWithReturn(int a, int b) {
    synchronized(this) {
      return iface.methodWithReturn(a,b);
    }
  }

  @Override
  public List<Integer> methodWithGenericReturn(int a, int b) {
    synchronized(this) {
      return iface.methodWithGenericReturn(a,b);
    }
  }
}
