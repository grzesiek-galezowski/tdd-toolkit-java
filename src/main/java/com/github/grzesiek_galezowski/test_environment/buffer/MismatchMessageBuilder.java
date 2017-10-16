package com.github.grzesiek_galezowski.test_environment.buffer;

import com.github.grzesiek_galezowski.test_environment.buffer.interfaces.MatchCountCondition;
import org.assertj.core.api.Condition;

import java.util.List;

public class MismatchMessageBuilder<T> {
  private final StringBuilder message = new StringBuilder();

  public MismatchMessageBuilder() {
  }

  public String build() {
    return message.toString();
  }

  public StringBuilder addResultsHeader() {
    return message.append("Details for all items:");
  }

  public StringBuilder addConditionImplementationGuidance() {
    return message.append("When implementing your conditions, be sure to use describeAs() every time both in case of success and failure.");
  }

  public StringBuilder addMoreInfoFooter() {
    return message.append("More information can be acquired by using observers.");
  }

  public StringBuilder addResults(final List<String> matchingDescriptions, final List<Boolean> matchingResult) {
    return message.append(correlate(matchingResult, matchingDescriptions));
  }

  public StringBuilder addNewLine() {
    return message.append("\n");
  }

  public StringBuilder addFailureAnnouncement() {
    return message.append("failed to match the specified condition.");
  }

  public StringBuilder addSpace() {
    return message.append(" ");
  }

  public StringBuilder addExpectedDescription(final Condition<T> condition, final MatchCountCondition matchCountCondition) {
    return message.append(expectedDescription(condition, matchCountCondition));
  }

  private String expectedDescription(final Condition<T> condition, final MatchCountCondition matchCountCondition) {
    return "<" + matchCountCondition.toString() + " " + condition.toString() + ">";
  }

  static String correlate(final List<Boolean> matchingResult, final List<String> matchingDescriptions) {
    String message = "";
    for (int i = 0; i < matchingResult.size(); ++i) {
      boolean result = matchingResult.get(i);
      String description = matchingDescriptions.get(i);
      message += "\nItem #" + (i + 1) + " yielded " + result + " because " + description;
    }
    return message;
  }
}