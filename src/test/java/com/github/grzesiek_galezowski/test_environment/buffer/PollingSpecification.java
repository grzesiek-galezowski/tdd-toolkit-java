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

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PollingSpecification {
  //todo add clear method
  //TODO events
  //todo convert expected count to condition
  //todo polling that something does NOT happen (happy+error)

  @Test
  public void shouldAllowPollingForItemExistence() throws ExecutionException, InterruptedException { //bug
    //GIVEN
    ReceivedObjectBuffer<Integer> buffer = ReceivedObjectBuffer.createDefault();
    val storedValue = Any.intValue();

    //WHEN - THEN
    val future = insertAfter1Second(storedValue, buffer);
    buffer.poll(Duration.ofSeconds(5)).toFind(ExpectedMatchCount.atLeastOne(), Item.equalTo(storedValue));

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
      buffer.poll(Duration.ofSeconds(2)).toFind(ExpectedMatchCount.atLeastOne(), Item.equalTo(storedValue3))
    ).isInstanceOf(ConditionTimeoutException.class);
  }

  @Test
  public void shouldAllowPollingForItemNonExistence() throws ExecutionException, InterruptedException { //bug
    //GIVEN
    ReceivedObjectBuffer<Integer> buffer = ReceivedObjectBuffer.createDefault();
    val storedValue = Any.intValue();

    //WHEN - THEN
    assertThatCode(() ->
      buffer.poll(Duration.ofSeconds(5))
        .toEnsureThereIsNo(Item.equalTo(storedValue))
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
        buffer.poll(Duration.ofSeconds(5)).toEnsureThereIsNo(Item.equalTo(storedValue)))
    .hasMessage("lol");

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
      System.out.println("inserted");
      buffer.store(storedValue3);
    });
  }
}