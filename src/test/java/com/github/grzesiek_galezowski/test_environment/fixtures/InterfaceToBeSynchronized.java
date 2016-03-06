package com.github.grzesiek_galezowski.test_environment.fixtures;

import java.util.List;

/**
 * Created by astral on 06.03.2016.
 */
public interface InterfaceToBeSynchronized {

  void voidMethod(int a, int b);

  int methodWithReturn(int a, int b);
  List<Integer> methodWithGenericReturn(int a, int b);

  //TODO for methods with return values
}
