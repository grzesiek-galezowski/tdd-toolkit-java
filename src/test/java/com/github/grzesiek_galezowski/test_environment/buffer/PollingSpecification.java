package com.github.grzesiek_galezowski.test_environment.buffer;

import autofixture.publicinterface.Any;
import com.github.grzesiek_galezowski.test_environment.Item;
import com.github.grzesiek_galezowski.test_environment.buffer.implementation.MismatchException;
import com.github.grzesiek_galezowski.test_environment.buffer.interfaces.Buffer;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.awaitility.core.ConditionTimeoutException;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.github.grzesiek_galezowski.test_environment.buffer.ExpectedMatchCount.*;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PollingSpecification {
  private final Duration fiveSeconds = Duration.ofSeconds(5);
  private final Duration twoSeconds = Duration.ofSeconds(2);
  private final Duration oneSecond = Duration.ofSeconds(1);
  //todo convert expected count to condition

  @Test
  public void shouldAllowPollingForItemExistence() throws ExecutionException, InterruptedException { //bug
    //GIVEN
    Buffer buffer = ReceivedObjectBuffer.createDefault();
    val storedValue = Any.intValue();

    //WHEN - THEN
    val future = insertAfter1Second(storedValue, buffer);
    buffer.pollFor(fiveSeconds)
        .awaiting(atLeastOne(), Item.equalTo(storedValue));

    //ANNIHILATE
    future.get();
  }

  @Test
  public void shouldThrowExceptionWhenPollingForItemExistenceFails() throws ExecutionException, InterruptedException { //bug
    //GIVEN
    Buffer buffer = ReceivedObjectBuffer.createDefault();
    val storedValue3 = Any.intValue();

    //WHEN - THEN
    assertThatThrownBy(() ->
      buffer.pollFor(twoSeconds)
          .awaiting(atLeastOne(), Item.equalTo(storedValue3))
    ).isInstanceOf(ConditionTimeoutException.class);
  }

  @Test
  public void shouldAllowPollingForItemNonExistence() throws ExecutionException, InterruptedException { //bug
    //GIVEN
    Buffer buffer = ReceivedObjectBuffer.createDefault();

    //WHEN - THEN
    assertThatCode(() ->
      buffer.pollFor(twoSeconds)
        .toEnsureThereIsNo(Item.equalTo(Any.intValue()))
    ).doesNotThrowAnyException();
  }

  @Test
  public void shouldFailWhenPollingForItemExistenceFindsTheItem() throws ExecutionException, InterruptedException { //bug
    //GIVEN
    Buffer buffer = ReceivedObjectBuffer.createDefault();
    val storedValue = Any.intValue();

    //WHEN - THEN
    val future = insertAfter1Second(storedValue, buffer);
    assertThatThrownBy(() ->
        buffer.pollFor(twoSeconds)
            .toEnsureThereIsNo(Item.equalTo(storedValue)))
    .isInstanceOf(MismatchException.class);

    //ANNIHILATE
    future.get();
  }

  private CompletableFuture<Void> insertAfter1Second(final Integer storedValue3,
                                                     final Buffer buffer) {
    Assertions.assertThat(buffer.isEmpty()).isTrue();
    return CompletableFuture.runAsync(() -> {
      try {
        Thread.sleep(oneSecond.toMillis());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      buffer.store(storedValue3);
    });
  }
}