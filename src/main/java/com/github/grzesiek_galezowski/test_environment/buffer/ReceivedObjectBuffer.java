package com.github.grzesiek_galezowski.test_environment.buffer;

import org.assertj.core.api.Condition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grzes on 26.06.2017.
 */
public class ReceivedObjectBuffer<T> {
  private List<T> receivedObjects = new ArrayList<>();

  public void store(final T object) {
    this.receivedObjects.add(object);
  }

  public void assertHasAny(final Condition<T> expected) {
    MatchingResult matchingResult = MatchingResult.empty();
    assertBufferNotEmpty(receivedObjects, expected);
    matchingResult.match(receivedObjects, expected);
    matchingResult.assertMatchFound(expected);
  }

  //todo assert has none
  //todo assert has exactly X matches
  //todo add toString that prints current buffer

  private static <T> void assertBufferNotEmpty(final List<T> receivedObjects, final Condition<T> expected) {
    if (receivedObjects.isEmpty()) {
      throw new EmptyBufferException(expected);
    }
  }
}
