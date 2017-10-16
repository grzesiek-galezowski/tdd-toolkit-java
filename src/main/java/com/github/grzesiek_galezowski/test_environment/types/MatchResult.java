package com.github.grzesiek_galezowski.test_environment.types;

import com.google.common.base.Joiner;
import org.assertj.core.util.Lists;

import java.util.List;

public class MatchResult {
  private List<String> currentMatchedFields = Lists.newArrayList();
  private List<List<String>> matchPaths = Lists.newArrayList();
  private boolean lastAttemptedBranchInvalid = false;

  public void revertBranchIfInvalidTo(final int currentIndex) {
    if(this.lastAttemptedBranchInvalid) {
      revertMatchesTo(currentIndex);
      this.lastAttemptedBranchInvalid = false;
    }
  }

  public void invalidateCurrentBranch() {
    this.lastAttemptedBranchInvalid = true;
  }

  public String getBestBranchesString() {
    List<String> strings = Lists.newArrayList();

    for(int i = 0; i < this.matchPaths.size(); i++) {
      strings.add(" " + (i+1) + ". " + Joiner.on("->").join(this.matchPaths.get(i)));
    }
    return Joiner.on(System.lineSeparator()).join(strings);
  }

  public boolean addMatched(final String fieldName) {
    return this.currentMatchedFields.add(fieldName);
  }

  public void revertMatchesTo(final int currentIndex) {
    rewindTo(currentIndex);
    newBranchAttempt();
  }

  public boolean newBranchAttempt() {
    return this.matchPaths.add(this.currentMatchedFields);
  }

  private void rewindTo(final int currentIndex) {
    this.currentMatchedFields = Lists.newArrayList(this.currentMatchedFields.subList(0, currentIndex));
  }
}