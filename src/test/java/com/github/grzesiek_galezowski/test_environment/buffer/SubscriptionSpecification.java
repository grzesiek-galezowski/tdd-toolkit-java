package com.github.grzesiek_galezowski.test_environment.buffer;

import autofixture.publicinterface.Any;
import lombok.val;
import org.testng.annotations.Test;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by grzes on 11.07.2017.
 */
public class SubscriptionSpecification {
  @Test
  public void shouldPassAllStoredValuesToSubscriber() {
    //GIVEN
    val buffer = ReceivedObjectBuffer.<Integer>createDefault();
    final ItemSubscriber<Integer> subscriber = mock(ItemSubscriber.class);
    final int value = Any.intValue();
    buffer.subscribeForItems(subscriber);

    //WHEN
    buffer.store(value);

    //THEN
    verify(subscriber).itemStored(value);
  }

  @Test
  public void shouldPassAllStoredValuesToAllSubscribers() {
    //GIVEN
    val buffer = ReceivedObjectBuffer.<Integer>createDefault();
    final ItemSubscriber<Integer> subscriber1 = mock(ItemSubscriber.class);
    final ItemSubscriber<Integer> subscriber2 = mock(ItemSubscriber.class);
    final ItemSubscriber<Integer> subscriber3 = mock(ItemSubscriber.class);
    final int value = Any.intValue();
    buffer.subscribeForItems(subscriber1);
    buffer.subscribeForItems(subscriber2);
    buffer.subscribeForItems(subscriber3);

    //WHEN
    buffer.store(value);

    //THEN
    verify(subscriber1).itemStored(value);
    verify(subscriber2).itemStored(value);
    verify(subscriber3).itemStored(value);
  }

  @Test
  public void shouldNotifyOtherSubscriberEvenWhenOneThrowsAnException() {
    //GIVEN
    val buffer = ReceivedObjectBuffer.<Integer>createDefault();
    final ItemSubscriber<Integer> subscriber1 = mock(ItemSubscriber.class);
    final ItemSubscriber<Integer> subscriber2 = mock(ItemSubscriber.class);
    final ItemSubscriber<Integer> subscriber3 = mock(ItemSubscriber.class);
    final int value = Any.intValue();
    buffer.subscribeForItems(subscriber1);
    buffer.subscribeForItems(subscriber2);
    buffer.subscribeForItems(subscriber3);

    doThrow(Throwable.class).when(subscriber1).itemStored(value);
    doThrow(Throwable.class).when(subscriber2).itemStored(value);
    doThrow(Throwable.class).when(subscriber3).itemStored(value);

    //WHEN
    buffer.store(value);

    //THEN
    verify(subscriber1).itemStored(value);
    verify(subscriber2).itemStored(value);
    verify(subscriber3).itemStored(value);
  }


  //todo subscriber with condition
  //todo subscriber throws an exception
}
