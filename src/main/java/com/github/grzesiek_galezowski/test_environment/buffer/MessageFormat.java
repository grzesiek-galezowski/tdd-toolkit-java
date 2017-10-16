package com.github.grzesiek_galezowski.test_environment.buffer;

import com.github.grzesiek_galezowski.test_environment.buffer.interfaces.MatchCountCondition;
import org.assertj.core.api.Condition;

import java.util.List;

/**
 * Created by grzes on 11.07.2017.
 */
public class MessageFormat<T> {

  private final MismatchMessageBuilder<T> message;

  public MessageFormat() {
    message = new MismatchMessageBuilder<T>();
  }

  public String getMessage(
      final Condition<T> condition,
      final List<String> matchingDescriptions,
      final List<Boolean> matchingResult,
      final MatchCountCondition matchCountCondition) {
    message.addExpectedDescription(condition, matchCountCondition);
    message.addSpace();
    message.addFailureAnnouncement();
    message.addNewLine();
    message.addResultsHeader();
    message.addResults(matchingDescriptions, matchingResult);
    message.addNewLine();
    message.addMoreInfoFooter();
    message.addNewLine();
    message.addConditionImplementationGuidance();
    return message.build();
  }

}
