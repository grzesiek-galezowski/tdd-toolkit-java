package com.github.grzesiek_galezowski.test_environment.buffer;

import autofixture.publicinterface.Any;
import com.github.grzesiek_galezowski.test_environment.Item;
import com.github.grzesiek_galezowski.test_environment.implementation_details.FakeCondition;
import org.assertj.core.api.Condition;
import org.testng.annotations.Test;

import static com.github.grzesiek_galezowski.test_environment.XAssert.assertThatNotThrownBy;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by grzes on 26.06.2017.
 */
public class ReceivedObjectBufferSpecificationWithSingleStoredObject {

  @Test
  public void shouldThrowExceptionWhenEmptyDuringAssertion() {
    String anyMessage = Any.string();
    Condition<Integer> condition = FakeCondition.failingWith(anyMessage);
    assertThatThrownBy(() -> {
          new ReceivedObjectBuffer<Integer>().assertHasAny(condition);
        }
    ).isInstanceOf(EmptyBufferException.class)
        .hasMessage("Could not evaluate " + condition.toString() + ". The buffer is empty.");
  }

  @Test
  public void shouldThrowExceptionWhenAssertingObjectOtherThanStored() {
    //GIVEN
    ReceivedObjectBuffer<Integer> buffer = new ReceivedObjectBuffer<>();
    Integer storedValue1 = Any.intValue();
    Integer storedValue2 = Any.intValue();
    Integer storedValue3 = Any.intValue();
    String anyMessage = Any.string();
    Condition<Integer> condition = FakeCondition.failingWith(anyMessage);

    //WHEN
    buffer.store(storedValue1);
    buffer.store(storedValue2);
    buffer.store(storedValue3);

    //WHEN
    assertThatThrownBy(() -> buffer.assertHasAny(condition)
    ).isInstanceOf(ObjectNotFoundInBufferException.class)
    .hasMessage(condition.toString() + " failed to match any of the items: \n" +
        "Check #1 is false because " + anyMessage + "\n" +
        "Check #2 is false because " + anyMessage + "\n" +
        "Check #3 is false because " + anyMessage);
    //fixme add a property to exception for each failed check
    // -> gather not in string, but collection or builder
  }

  @Test
  public void shouldNotThrowAnythingWhenConditionIsPassingForItem() {
    //GIVEN
    ReceivedObjectBuffer<Integer> buffer = new ReceivedObjectBuffer<>();
    Integer storedValue = Any.intValue();

    //WHEN
    buffer.store(storedValue);

    //WHEN
    assertThatNotThrownBy(() -> buffer.assertHasAny(Item.equalTo(storedValue))
    );
  }

  @Test
  public void shouldNotThrowAnythingWhenConditionIsPassingForAtLeastOneItem() {
    //GIVEN
    ReceivedObjectBuffer<Integer> buffer = new ReceivedObjectBuffer<>();
    Integer storedValue1 = Any.intValue();
    Integer storedValue2 = Any.intValue();
    Integer storedValue3 = Any.intValue();

    //WHEN
    buffer.store(storedValue1);
    buffer.store(storedValue2);
    buffer.store(storedValue3);

    //WHEN
    assertThatNotThrownBy(() -> buffer.assertHasAny(Item.equalTo(storedValue1)));
    assertThatNotThrownBy(() -> buffer.assertHasAny(Item.equalTo(storedValue2)));
    assertThatNotThrownBy(() -> buffer.assertHasAny(Item.equalTo(storedValue3)));
  }

  @Test
  public void shouldThrowExceptionWhenConditionRaisesAnException() {
    //GIVEN
    ReceivedObjectBuffer<Integer> buffer = new ReceivedObjectBuffer<>();
    RuntimeException exception = Any.runtimeException();
    Condition<Integer> condition = FakeCondition.throwing(exception);
    Integer storedValue = Any.intValue();

    //WHEN
    buffer.store(storedValue);

    //WHEN
    assertThatThrownBy(() -> {
      buffer.assertHasAny(condition);
    })
    .hasMessage(
        condition.toString()
        + " raised an exception for one of the items: "
        + exception.toString()
    );
  }


}
