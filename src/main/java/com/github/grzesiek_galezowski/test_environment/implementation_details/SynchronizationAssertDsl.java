package com.github.grzesiek_galezowski.test_environment.implementation_details;

import autofixture.publicinterface.Any;
import autofixture.publicinterface.InstanceOf;
import com.google.common.base.Throwables;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by astral 16.03.2016.
 */
public class SynchronizationAssertDsl<T> {
  private final T wrappedMock;
  private final T realThing;

  public SynchronizationAssertDsl(final T wrappedMock, final T realThing) {

    this.wrappedMock = wrappedMock;
    this.realThing = realThing;
  }

  public <TReturn> SynchronizationAssertDsl2<T> whenReceives(
      final CheckedFunction<T, TReturn> methodCallToVerify,
      final Class<TReturn> clazz) {
    return whenReceives(throwing(methodCallToVerify), clazz);
  }

  public <TReturn> SynchronizationAssertDsl2<T> whenReceives(
      final CheckedFunction<T, TReturn> methodCallToVerify,
      final InstanceOf<TReturn> clazz) {
    return whenReceives(throwing(methodCallToVerify), clazz);
  }

  public SynchronizationAssertDsl2<T> whenReceives(final CheckedConsumer<T> consumer) {
    return whenReceives(throwing(consumer));
  }

  private <TReturn> SynchronizationAssertDsl2<T> whenReceives(
      final Function<T, TReturn> methodCallToVerify,
      final Class<TReturn> clazz) {

    final TReturn retVal = Any.anonymous(clazz);
    return dslOver(workflow(forCheckingSynchronizationOf(methodCallToVerify, retVal)));
  }

  private <TReturn> SynchronizationAssertDsl2<T> whenReceives(
      final Function<T, TReturn> methodCallToVerify,
      final InstanceOf<TReturn> clazz) {
    final TReturn retVal = Any.anonymous(clazz);
    return dslOver(workflow(forCheckingSynchronizationOf(methodCallToVerify, retVal)));
  }

  private SynchronizationAssertDsl2<T> whenReceives(final Consumer<T> consumer) {
    return dslOver(workflow(forCheckingSynchronizationOf(consumer)));
  }

  private SynchronizationAssertDsl2<T> dslOver(final SynchronizationAssertionWorkflow<T> workflow) {
    return new SynchronizationAssertDsl2<>(realThing,
        workflow);
  }

  private <TReturn> StepsForSynchronizingOnFunction<T, TReturn> forCheckingSynchronizationOf(final Function<T, TReturn> methodCallToVerify, final TReturn retVal) {
    return new StepsForSynchronizingOnFunction<>(methodCallToVerify, retVal);
  }

  private StepsForSynchronizingOnCommand<T> forCheckingSynchronizationOf(final Consumer<T> consumer) {
    return new StepsForSynchronizingOnCommand<>(consumer);
  }

  private SynchronizationAssertionWorkflow<T> workflow(final SynchronizationAssertionSteps<T> stepsForSynchronizingOnCommand) {
    return new SynchronizationAssertionWorkflow<>(this.wrappedMock, realThing,
        stepsForSynchronizingOnCommand);
  }

  private <TReturn> Function<T, TReturn> throwing(
      final CheckedFunction<T, TReturn> methodCallToVerify) {
    return x -> {
      try {
        return methodCallToVerify.apply(x);
      } catch(Throwable e) {
        Throwables.throwIfUnchecked(e);
        throw new RuntimeException(e);
      }
    };
  }

  private Consumer<T> throwing(
      final CheckedConsumer<T> consumer) {
    return x -> {
      try {
        consumer.accept(x);
      } catch(Throwable e) {
        Throwables.throwIfUnchecked(e);
        throw new RuntimeException(e);
      }
    };
  }


}
