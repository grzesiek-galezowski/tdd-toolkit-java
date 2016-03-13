package com.github.grzesiek_galezowski.test_environment.fixtures;

import java.util.List;

/**
 * Created by astral on 06.03.2016.
 */
public interface InterfaceToBeSynchronized {

  void correctlyWrappedVoidMethod(int a, int b);

  int correctlyWrappedFunction(int a, int b);

  List<Integer> methodWithGenericReturn(int a, int b);

  void correctlyCalledButNotSynchronizedVoidMethod(Integer a, Integer b);

  void voidMethodNotCalledAtAll(Integer a, Integer b);

  void voidMethodCalledWithWrongArguments(Integer a, Integer b);

  int correctlyCalledButNotSynchronizedFunction(int a, int b);

  int functionNotCalledAtAll(int a, int b);

  int functionCalledWithWrongArguments(int a, int b);

  int functionWithNonPropagatedReturnValue(int a, int b);

  //TODO for methods with return values
}
