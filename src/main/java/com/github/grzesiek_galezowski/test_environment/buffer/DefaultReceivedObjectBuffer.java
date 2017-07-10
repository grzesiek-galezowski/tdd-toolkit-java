package com.github.grzesiek_galezowski.test_environment.buffer;

import lombok.val;
import org.assertj.core.api.Condition;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by grzes on 26.06.2017.
 */
public final class DefaultReceivedObjectBuffer<T> implements ReceivedObjectBuffer<T> {
  private List<T> receivedObjects = new ArrayList<>();
  private BufferObserver observer;
  private SearchCommandFactory<T> searchCommandFactory;

  public DefaultReceivedObjectBuffer(final BufferObserver observer) {
    this.observer = observer;
    this.searchCommandFactory = new SearchCommandFactory<>(observer, receivedObjects);
  }

  @Override
  public void store(final T object) {
    System.out.println("stored");
    this.receivedObjects.add(object);
  }

  @Override
  public void assertHasAtLeastOne(final Condition<T> condition) {
    assertHas(ExpectedMatchCount.atLeastOne(), condition);
  }

  @Override
  public void assertHas(final ExpectedMatchCount expectedMatchCount, final Condition<T> condition) {
    assertBufferNotEmpty(receivedObjects, condition);

    val searchResult = blankResultFor(condition, expectedMatchCount);
    val searchCommand = searchCommandFactory.searchFor(condition, searchResult);
    searchCommand.performSearch();
    searchResult.assertFoundAccordingToSpecification();
  }

  @Override
  public boolean has(final ExpectedMatchCount expectedMatchCount, final Condition<T> condition) {
    val searchResult = blankResultFor(condition, expectedMatchCount);
    val searchCommand = searchCommandFactory.searchFor(condition, searchResult);
    searchCommand.performSearch();
    return searchResult.foundAccordingToSpecification();
  }

  @Override
  public boolean isEmpty() {
    return receivedObjects.isEmpty();
  }

  private SearchResult<T> blankResultFor(final Condition<T> expected, final ExpectedMatchCount expectedMatchCount) {
    return new SearchResult<T>(expectedMatchCount, expected, observer);
  }

  //todo add toString that prints current buffer

  private static <T> void assertBufferNotEmpty(final List<T> receivedObjects, final Condition<T> expected) {
    if (receivedObjects.isEmpty()) {
      throw new EmptyBufferException(expected);
    }
  }

  @Override
  public Poll<T> poll() {
    return Poll.on(this, observer);
  }

  @Override
  public Poll<T> poll(final Duration duration) {
    return Poll.on(this, duration, observer);
  }
}
