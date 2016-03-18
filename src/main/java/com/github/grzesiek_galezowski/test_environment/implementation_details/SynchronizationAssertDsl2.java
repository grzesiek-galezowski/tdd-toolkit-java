package com.github.grzesiek_galezowski.test_environment.implementation_details;

/**
 * Created by astral whenReceives 17.03.2016.
 */
public class SynchronizationAssertDsl2<T> {
  private final T realThing;
  private final SynchronizationAssertionWorkflow<T> assertionWorkflow;

  public SynchronizationAssertDsl2(final T realThing,
                                   final SynchronizationAssertionWorkflow<T> assertionWorkflow) {

    this.realThing = realThing;
    this.assertionWorkflow = assertionWorkflow;
  }

  public void thenLocksCorrectly() {
    thenLocksCorrectlyOn(realThing);
  }

  public void thenLocksCorrectlyOn(final Object monitorObject) {
    final LockAssertions<T> lockAssertions = new LockAssertions<>(monitorObject);
    assertionWorkflow.invoke(lockAssertions);
  }

}
