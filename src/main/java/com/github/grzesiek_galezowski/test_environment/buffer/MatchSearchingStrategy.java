package com.github.grzesiek_galezowski.test_environment.buffer;

import java.util.List;

public class MatchSearchingStrategy {
  public MatchSearchingStrategy() {
  }

  boolean matchFound(final List<Boolean> matchingResult) {
    return matchingResult.contains(true);
  }
}