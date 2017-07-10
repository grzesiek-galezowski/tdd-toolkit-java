package com.github.grzesiek_galezowski.test_environment.buffer;

import autofixture.publicinterface.Any;
import com.github.grzesiek_galezowski.test_environment.Item;
import com.github.grzesiek_galezowski.test_environment.implementation_details.FakeCondition;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import static com.github.grzesiek_galezowski.test_environment.XAssert.assertThatNotThrownBy;
import static com.github.grzesiek_galezowski.test_environment.buffer.ExpectedMatchCount.atLeastOne;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ReceivedObjectBufferSpecificationWithManyStoredObjects {
  public ReceivedObjectBufferSpecificationWithManyStoredObjects() {
  }

  @Test
  public void shouldThrowExceptionWhenAssertingObjectOtherThanStored() {
    //GIVEN
    val buffer = ReceivedObjectBuffer.createDefault();
    val storedValue1 = Any.intValue();
    val storedValue2 = Any.intValue();
    val storedValue3 = Any.intValue();
    val anyMessage = Any.string();
    val condition = FakeCondition.failingWith(anyMessage);

    //WHEN
    buffer.store(storedValue1);
    buffer.store(storedValue2);
    buffer.store(storedValue3);

    //WHEN
    Assertions.assertThatThrownBy(() -> buffer.assertHasAtLeastOne(condition)
    ).isInstanceOf(MismatchException.class)
        .hasMessage(condition
        + " failed to match the specified condition for the following matches: \n"
        + "Check #1 is false because " + anyMessage + "\n"
        + "Check #2 is false because " + anyMessage + "\n"
        + "Check #3 is false because " + anyMessage + "\n"
        + "More information can be acquired by using observers");
  }

  @Test
  public void shouldNotThrowAnythingWhenConditionIsPassingForAtLeastOneItem() {
    //GIVEN
    val buffer = ReceivedObjectBuffer.createDefault();
    val storedValue1 = Any.intValue();
    val storedValue2 = Any.intValue();
    val storedValue3 = Any.intValue();

    //WHEN
    buffer.store(storedValue1);
    buffer.store(storedValue2);
    buffer.store(storedValue3);

    //WHEN
    assertThatNotThrownBy(() -> buffer.assertHas(atLeastOne(), Item.equalTo(storedValue1)));
    assertThatNotThrownBy(() -> buffer.assertHas(atLeastOne(), Item.equalTo(storedValue2)));
    assertThatNotThrownBy(() -> buffer.assertHas(atLeastOne(), Item.equalTo(storedValue3)));
  }


}