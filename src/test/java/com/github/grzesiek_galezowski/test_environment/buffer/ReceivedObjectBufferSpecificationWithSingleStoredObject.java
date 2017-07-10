package com.github.grzesiek_galezowski.test_environment.buffer;

import autofixture.publicinterface.Any;
import com.github.grzesiek_galezowski.test_environment.Item;
import com.github.grzesiek_galezowski.test_environment.implementation_details.FakeCondition;
import lombok.val;
import org.testng.annotations.Test;

import static com.github.grzesiek_galezowski.test_environment.XAssert.assertThatNotThrownBy;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by grzes on 26.06.2017.
 */
public class ReceivedObjectBufferSpecificationWithSingleStoredObject {

  @Test
  public void shouldThrowExceptionWhenEmptyDuringAssertion() {
    val anyMessage = Any.string();
    val condition = FakeCondition.<Integer>failingWith(anyMessage);
    assertThatThrownBy(() -> {
          createNewBuffer().assertHasAtLeastOne(condition);
        }
    ).isInstanceOf(EmptyBufferException.class)
        .hasMessage("Could not evaluate " + condition.toString() + ". The buffer is empty.");
  }

  @Test
  public void shouldNotThrowAnythingWhenConditionIsPassingForItem() {
    //GIVEN
    val buffer = createNewBuffer();
    val storedValue = Any.intValue();

    //WHEN
    buffer.store(storedValue);

    //WHEN
    assertThatNotThrownBy(() -> buffer.assertHasAtLeastOne(Item.equalTo(storedValue))
    );
  }

  @Test
  public void shouldThrowExceptionWhenConditionRaisesAnException() {
    //GIVEN
    val buffer = createNewBuffer();
    val exception = Any.runtimeException();
    val condition = FakeCondition.<Integer>throwing(exception);
    val storedValue = Any.intValue();

    //WHEN
    buffer.store(storedValue);

    //WHEN
    assertThatThrownBy(() -> {
      buffer.assertHasAtLeastOne(condition);
    })
    .hasMessage(
        condition.toString()
        + " raised an exception for one of the items: "
        + exception.toString()
    );
  }

  private ReceivedObjectBuffer<Integer> createNewBuffer() {
    return ReceivedObjectBuffer.<Integer>createDefault();
  }


}
