package com.github.grzesiek_galezowski.test_environment.types;

import net.bramp.objectgraph.ObjectGraph;
import org.assertj.core.api.Condition;

import java.util.Objects;


public class ObjectGraphContainsDependencyCondition extends Condition {
  private Object expected;

  public ObjectGraphContainsDependencyCondition(final Object expected) {
    this.expected = expected;
  }

  public static Condition dependencyOn(final Object dependency) {
    return new ObjectGraphContainsDependencyCondition(dependency);
  }

  @Override
  public boolean matches(final Object graph) {
    final boolean[] result = {false};
    ObjectGraph
        .visitor((actual, clazz) -> {
          if(expected == null && actual == null) {
            result[0] = true;
            return true;
          } else if(Objects.equals(expected, actual)) {
            result[0] = true;
            return true;
          }
          return false;
        })
        .traverse(graph);
    return result[0];
  }

  @Override
  public String toString() {
    return expected.getClass().getSimpleName() + ": " + expected;
  }
}
