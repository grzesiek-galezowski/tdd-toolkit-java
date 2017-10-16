package com.github.grzesiek_galezowski.test_environment.buffer.implementation;

import com.github.grzesiek_galezowski.test_environment.buffer.interfaces.BufferObserver;
import com.github.grzesiek_galezowski.test_environment.buffer.interfaces.Checkable;
import com.github.grzesiek_galezowski.test_environment.buffer.interfaces.MatchCountCondition;
import com.github.grzesiek_galezowski.test_environment.buffer.interfaces.Poll;
import org.assertj.core.api.Condition;
import org.awaitility.Duration;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.concurrent.TimeUnit;

import static com.github.grzesiek_galezowski.test_environment.buffer.implementation.LambdaBasedExpectedMatchCount.no;
import static org.awaitility.Awaitility.await;

/**
 * Created by grzes on 10.07.2017.
 */
public class AwaitilityPoll<T> implements Poll<T> {

  private final Duration pollDuration;
  private BufferObserver observer;
  private Checkable<T> buffer;

  public AwaitilityPoll(
      final Checkable<T> buffer,
      final Duration pollDuration,
      final BufferObserver observer) {
    this.buffer = buffer;
    this.pollDuration = pollDuration;
    this.observer = observer;
  }

  public static <T> Poll<T> on(
      final Checkable<T> buffer,
      final java.time.Duration duration,
      final BufferObserver observer) {
    return new AwaitilityPoll<>(
        buffer,
        new Duration(duration.toMillis(), TimeUnit.MILLISECONDS),
        observer);
  }

  public static <T> Poll<T> on(
      final Checkable<T> buffer,
      final BufferObserver observer) {
    return new AwaitilityPoll<>(
        buffer, Duration.FIVE_SECONDS,
        observer);
  }

  @Override
  public void awaiting(
      final MatchCountCondition matchCountCondition,
      final Condition<T> condition) {
    observer.periodicPollingStarted();
    await().pollInterval(Duration.ONE_SECOND).atMost(pollDuration).until(
        () -> {
          observer.singlePollStarted();
          boolean pollResult = buffer.contains(matchCountCondition, condition);
          observer.singlePollFinishedWith(pollResult);
          return pollResult;
        },
        createDescribingMatcher(condition, matchCountCondition));
  }

  private static <T> BaseMatcher<Boolean> createDescribingMatcher(
      final Condition<T> condition, final MatchCountCondition matchCountCondition) {
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
            "after polling "
                + matchAttemptCount
                + " times to find "
                + matchCountCondition
                + " occurence(s) of item: "
                + condition.toString());
      }
    };
  }

  @Override
  public void toEnsureThereIsNo(final Condition<T> condition) {
    observer.periodicPollingStarted();

    try {
      Thread.sleep(pollDuration.getValueInMS());
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    buffer.assertContains(
        no(), condition);
  }
}

