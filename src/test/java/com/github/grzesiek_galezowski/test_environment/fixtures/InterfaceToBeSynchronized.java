package com.github.grzesiek_galezowski.test_environment.fixtures;

import java.io.IOException;
import java.util.List;

/**
 * Created by astral whenReceives 06.03.2016.
 */
public interface InterfaceToBeSynchronized {

  void correctlyWrappedVoidMethod(final int a, final int b);

  void correctlyWrappedThrowingVoidMethod(final int a, final int b) throws IOException;

  int correctlyWrappedFunction(int a, int b);

  int correctlyWrappedThrowingFunction(int a, int b) throws IOException;

  List<Integer> genericCorrectlySynchronizedFunction(int a, int b);

  void correctlyCalledButNotSynchronizedVoidMethod(Integer a, Integer b);

  void voidMethodNotCalledAtAll(Integer a, Integer b);

  void voidMethodCalledWithWrongArguments(Integer a, Integer b);

  int correctlyCalledButNotSynchronizedFunction(int a, int b);

  int functionNotCalledAtAll(int a, int b);

  int functionCalledWithWrongArguments(int a, int b);

  int functionWithNonPropagatedReturnValue(int a, int b);

  List<Integer> genericCorrectlyCalledButNotSynchronizedFunction(int a, int b);

  List<Integer> genericFunctionNotCalledAtAll(int a, int b);

  List<Integer> genericFunctionCalledWithWrongArguments(int a, int b);

  List<Integer> genericFunctionWithNonPropagatedReturnValue(Integer a, Integer b);
}
