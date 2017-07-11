package com.github.grzesiek_galezowski.test_environment.buffer;

import autofixture.publicinterface.Any;
import com.github.grzesiek_galezowski.test_environment.Item;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class ItemsClearingSpecification {
  @Test
  public void shouldNotFindStoredItemsAfterBeingCleared() {
    //GIVEN
    val buffer = ReceivedObjectBuffer.createDefault();
    val storedValue1 = Any.intValue();
    val storedValue2 = Any.intValue();
    val storedValue3 = Any.intValue();
    buffer.store(storedValue1);
    buffer.store(storedValue2);
    buffer.store(storedValue3);

    //WHEN
    buffer.clearItems();

    //WHEN
    Assertions.assertThat(buffer.contains(ExpectedMatchCount.atLeastOne(), Item.equalTo(storedValue1)))
        .isEqualTo(false);
    Assertions.assertThat(buffer.contains(ExpectedMatchCount.atLeastOne(), Item.equalTo(storedValue2)))
        .isEqualTo(false);
    Assertions.assertThat(buffer.contains(ExpectedMatchCount.atLeastOne(), Item.equalTo(storedValue3)))
        .isEqualTo(false);
  }
}