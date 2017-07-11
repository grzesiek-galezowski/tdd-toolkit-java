package com.github.grzesiek_galezowski.test_environment.buffer;

import autofixture.publicinterface.Any;
import com.github.grzesiek_galezowski.test_environment.Item;
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
  //TODO events with conditions
  //todo convert expected count to condition

  @Test
  public void shouldAllowPollingForItemExistence() throws ExecutionException, InterruptedException { //bug
    //GIVEN
    ReceivedObjectBuffer<Integer> buffer = ReceivedObjectBuffer.createDefault();
    val storedValue = Any.intValue();

    //WHEN - THEN
    val future = insertAfter1Second(storedValue, buffer);
    buffer.pollFor(Duration.ofSeconds(5))
        .awaiting(atLeastOne(), Item.equalTo(storedValue));

    //ANNIHILATE
    future.get();
  }

  @Test
  public void shouldThrowExceptionWhenPollingForItemExistenceFails() throws ExecutionException, InterruptedException { //bug
    //GIVEN
    ReceivedObjectBuffer<Integer> buffer = ReceivedObjectBuffer.createDefault();
    val storedValue3 = Any.intValue();

    //WHEN - THEN
    assertThatThrownBy(() ->
      buffer.pollFor(Duration.ofSeconds(2))
          .awaiting(atLeastOne(), Item.equalTo(storedValue3))
    ).isInstanceOf(ConditionTimeoutException.class);
  }

  @Test
  public void shouldAllowPollingForItemNonExistence() throws ExecutionException, InterruptedException { //bug
    //GIVEN
    ReceivedObjectBuffer<Integer> buffer = ReceivedObjectBuffer.createDefault();

    //WHEN - THEN
    assertThatCode(() ->
      buffer.pollFor(Duration.ofSeconds(5))
        .toEnsureThereIsNo(Item.equalTo(Any.intValue()))
    ).doesNotThrowAnyException();
  }

  @Test
  public void shouldFailWhenPollingForItemExistenceFindsTheItem() throws ExecutionException, InterruptedException { //bug
    //GIVEN
    ReceivedObjectBuffer<Integer> buffer = ReceivedObjectBuffer.createDefault();
    val storedValue = Any.intValue();

    //WHEN - THEN
    val future = insertAfter1Second(storedValue, buffer);
    assertThatThrownBy(() ->
        buffer.pollFor(Duration.ofSeconds(5))
            .toEnsureThereIsNo(Item.equalTo(storedValue)))
    .isInstanceOf(MismatchException.class);

    //ANNIHILATE
    future.get();
  }

  private CompletableFuture<Void> insertAfter1Second(final Integer storedValue3,
                                                     final ReceivedObjectBuffer<Integer> buffer) {
    Assertions.assertThat(buffer.isEmpty()).isTrue();
    return CompletableFuture.runAsync(() -> {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      buffer.store(storedValue3);
    });
  }
}