package com.github.grzesiek_galezowski.test_environment.buffer;

import org.assertj.core.api.Condition;

import java.util.List;

/**
 * Created by grzes on 26.06.2017.
 */
public class ObjectNotFoundInBufferException extends RuntimeException {

  public <T> ObjectNotFoundInBufferException(
      final Condition<T> condition,
      final List<String> matchingDescriptions,
      final List<Boolean> matchingResult) {
    super(getMessage(condition, matchingDescriptions, matchingResult)
    );
  }

  private static <T> String getMessage(
      final Condition<T> condition,
      final List<String> matchingDescriptions,
      final List<Boolean> matchingResult) {
    return condition.toString()
        + " failed to match any of the items: "
        + correlate(matchingResult, matchingDescriptions);
  }

  private static String correlate(final List<Boolean> matchingResult, final List<String> matchingDescriptions) {
    String message = "";
    for(int i = 0; i < matchingResult.size(); ++i) {
      boolean result = matchingResult.get(i);
      String description = matchingDescriptions.get(i);
      message += "\nCheck #" + (i+1) + " is " + result + " because " + description;
    }
    return message;
  }
}