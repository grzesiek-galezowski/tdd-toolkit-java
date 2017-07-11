package com.github.grzesiek_galezowski.test_environment.buffer;

import autofixture.publicinterface.Any;
import com.github.grzesiek_galezowski.test_environment.Item;
import com.github.grzesiek_galezowski.test_environment.implementation_details.FakeCondition;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import static com.github.grzesiek_galezowski.test_environment.XAssert.assertThatNotThrownBy;
import static com.github.grzesiek_galezowski.test_environment.buffer.ExpectedMatchCount.atLeast;
import static com.github.grzesiek_galezowski.test_environment.buffer.ExpectedMatchCount.atLeastOne;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AssertionsSpecification {

  @Test
  public void shouldThrowExceptionWhenAssertingObjectOtherThanStored() {
    //GIVEN
    val buffer = ReceivedObjectBuffer.createDefault();
    val storedValue1 = Any.intValue();
    val storedValue2 = Any.intValue();
    val storedValue3 = Any.intValue();
    val notStoredValue = Any.intOtherThan(storedValue1, storedValue2, storedValue3);

    //WHEN
    buffer.store(storedValue1);
    buffer.store(storedValue2);
    buffer.store(storedValue3);

    //WHEN
    Assertions.assertThatThrownBy(() -> {
          buffer.assertContains(atLeast(2), Item.equalTo(storedValue2));
        }
    ).isInstanceOf(MismatchException.class)
        .hasMessage(
        "<at least 2 item(s) equal to " + storedValue2 + ">"
        + " failed to match the specified condition.\n"
        + "Details for all items:\n"
        + "Item #1 yielded false because " + storedValue1 + " is not equal to expected " + storedValue2 + "\n"
        + "Item #2 yielded true because " + storedValue2 + " is equal to expected " + storedValue2 + "\n"
        + "Item #3 yielded false because " + storedValue3 + " is not equal to expected " + storedValue2 + "\n"
        + "More information can be acquired by using observers." + "\n"
        + "When implementing your conditions, be sure to use describeAs() every time both in case of success and failure.");
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
    assertThatNotThrownBy(() -> buffer.assertContains(atLeastOne(), Item.equalTo(storedValue1)));
    assertThatNotThrownBy(() -> buffer.assertContains(atLeastOne(), Item.equalTo(storedValue2)));
    assertThatNotThrownBy(() -> buffer.assertContains(atLeastOne(), Item.equalTo(storedValue3)));
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
      buffer.assertContains(atLeastOne(), condition);
    })
        .hasMessage(
            condition.toString()
                + " raised an exception for one of the items: "
                + exception.toString()
        );
  }

  private ReceivedObjectBuffer<Integer> createNewBuffer() {
    return ReceivedObjectBuffer.createDefault();
  }

}