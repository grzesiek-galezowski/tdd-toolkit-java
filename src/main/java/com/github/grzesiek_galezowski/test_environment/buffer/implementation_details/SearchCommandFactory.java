package com.github.grzesiek_galezowski.test_environment.buffer.implementation_details;

import com.github.grzesiek_galezowski.test_environment.Clone;
import com.github.grzesiek_galezowski.test_environment.buffer.BufferObserver;
import org.assertj.core.api.Condition;

import java.util.List;

/**
 * Created by grzes on 09.07.2017.
 */
public class SearchCommandFactory<T> {
  private final BufferObserver observer;
  private final List<T> receivedObjects;

  public SearchCommandFactory(final BufferObserver observer, final List<T> receivedObjects) {
    this.observer = observer;
    this.receivedObjects = receivedObjects;
  }

  public SearchCommand<T> searchFor(
      final Condition<T> expected,
      final SearchResult<T> searchResult) {
    return new SearchCommand<T>(
        this.observer,
        this.receivedObjects,
        Clone.of(expected),
        searchResult);
  }
}
