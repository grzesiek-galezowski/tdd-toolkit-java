package com.github.grzesiek_galezowski.test_environment.buffer.implementation;

import com.github.grzesiek_galezowski.test_environment.buffer.interfaces.BufferObserver;
import com.github.grzesiek_galezowski.test_environment.buffer.interfaces.ExceptionRaisedByConditionException;
import lombok.val;
import org.assertj.core.api.Condition;

import java.util.List;

public class SearchCommand<T> {
  private final SearchResult<T> searchResult;
  private BufferObserver<T> observer;
  private List<T> receivedObjects;
  private Condition<T> condition;

  public SearchCommand(
      final BufferObserver<T> observer,
      final List<T> receivedObjects,
      final Condition<T> expected,
      final SearchResult<T> searchResult) {
    this.observer = observer;
    this.receivedObjects = receivedObjects;
    this.condition = expected;
    this.searchResult = searchResult;
  }

  public void performSearch() {
    observer.searchingStartedWithin(receivedObjects, condition);

    for (T receivedObject : this.receivedObjects) {
      determineMatchOf(receivedObject);
    }

    searchResult.conclude();
  }

  private void determineMatchOf(final T receivedObject) {
    String previousDescription = condition.description().toString();
    try {
      observer.tryingToMatch(receivedObject, condition);

      val isMatch = this.condition.matches(receivedObject);
      val matchDescription = this.condition.description().toString();

      searchResult.add(receivedObject, isMatch, matchDescription);
    } catch (Throwable t) {
      val exception
          = new ExceptionRaisedByConditionException(condition, t);
      observer.searchingFinishedWith(exception, receivedObject);
      throw exception;
    } finally {
      condition.describedAs(previousDescription);
    }
  }

}
