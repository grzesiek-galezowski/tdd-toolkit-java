package com.github.grzesiek_galezowski.test_environment.buffer;

import org.assertj.core.api.Condition;
import org.awaitility.Duration;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

/**
 * Created by grzes on 10.07.2017.
 */
public class Poll<T> {

  private final Duration pollDuration;
  private BufferObserver observer;
  private ReceivedObjectBuffer<T> buffer;

  public Poll(
      final ReceivedObjectBuffer<T> buffer,
      final Duration pollDuration,
      final BufferObserver observer) {
    this.buffer = buffer;
    this.pollDuration = pollDuration;
    this.observer = observer;
  }

  public static <T> Poll<T> on(
      final ReceivedObjectBuffer<T> buffer,
      final java.time.Duration duration,
      final BufferObserver observer) {
    return new Poll<>(
        buffer,
        new Duration(duration.toMillis(), TimeUnit.MILLISECONDS),
        observer);
  }

  public static <T> Poll<T> on(
      final ReceivedObjectBuffer<T> buffer,
      final BufferObserver observer) {
    return new Poll<>(
        buffer, Duration.FIVE_SECONDS,
        observer);
  }

  public void toFind(
      final ExpectedMatchCount expectedMatchCount,
      final Condition<T> condition) {
    observer.periodicPollingStarted();
    await().pollInterval(Duration.ONE_SECOND).atMost(pollDuration).until(
        () -> {
          observer.singlePollStarted();
          boolean pollResult = buffer.has(expectedMatchCount, condition);
          observer.singlePollFinishedWith(pollResult);
          return pollResult;
        },
        createDescribingMatcher(condition));
  }

  private static <T> BaseMatcher<Boolean> createDescribingMatcher(final Condition<T> condition) {
    return new BaseMatcher<Boolean>() {
      private int matchAttemptCount = 0;

      @Override
      public boolean matches(final Object item) {
        matchAttemptCount++;
        return (Boolean) item;
      }

      @Override
      public void describeTo(final Description description) {
        description.appendText(
            "after polling " + matchAttemptCount + " times to find item: " + condition.toString());
      }
    };
  }

  public void toEnsureThereIsNo(final Condition<T> condition) {
    observer.periodicPollingStarted();

    // the below algorithm is wrong! it should sleep, then check
    await().pollInterval(Duration.ONE_SECOND).atMost(pollDuration).until(
        () -> {
          observer.singlePollStarted();
          boolean pollResult = !buffer.has(ExpectedMatchCount.atLeastOne(), condition);
          observer.singlePollFinishedWith(pollResult);
          return pollResult;
        },
        createDescribingMatcher(condition));
  }
}
