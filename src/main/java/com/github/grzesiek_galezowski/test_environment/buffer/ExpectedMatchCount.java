package com.github.grzesiek_galezowski.test_environment.buffer;

import com.github.grzesiek_galezowski.test_environment.buffer.implementation.LambdaBasedExpectedMatchCount;
import com.github.grzesiek_galezowski.test_environment.buffer.interfaces.MatchCountCondition;

/**
  * Created by grzes on 09.07.2017.
 */
public interface ExpectedMatchCount {
  static MatchCountCondition atLeast(int times) {
    return LambdaBasedExpectedMatchCount.atLeast(times);
  }

  static MatchCountCondition exactly(int times) {
    return LambdaBasedExpectedMatchCount.exactly(times);
  }

  static MatchCountCondition atLeastOne() {
    return LambdaBasedExpectedMatchCount.atLeastOne();
  }

  static MatchCountCondition exactlyOne() {
    return LambdaBasedExpectedMatchCount.exactlyOne();
  }

  static MatchCountCondition no() {
    return LambdaBasedExpectedMatchCount.no();
  }

}

