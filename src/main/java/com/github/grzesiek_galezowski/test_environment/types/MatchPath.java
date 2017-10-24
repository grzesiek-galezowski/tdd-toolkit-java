package com.github.grzesiek_galezowski.test_environment.types;

import com.google.common.base.Joiner;
import lombok.Value;
import org.assertj.core.util.Lists;

import java.util.List;


@Value
public class MatchPath implements Comparable<MatchPath> {
  private final List<String> value;

  public MatchPath(final List<String> value) {
    this.value = value;
  }

  public MatchPath copy() {
    return new MatchPath(Lists.newArrayList(getValue()));
  }

  boolean addMatch(final String fieldName) {
    return getValue().add(fieldName);
  }

  public List<String> getValue() {
    return value;
  }

  @Override
  public String toString() {
    return Joiner.on("->").join(value);
  }

  @Override
  public int compareTo(final MatchPath o) {
    return toString().compareTo(o.toString());
  }

  public boolean isSupersetOf(final MatchPath x) {
    return toString().contains(x.toString());
  }
}
