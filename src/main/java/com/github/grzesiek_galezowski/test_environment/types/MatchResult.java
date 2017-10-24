package com.github.grzesiek_galezowski.test_environment.types;

import com.google.common.base.Joiner;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class MatchResult {
  private final MatchPath currentPath;
  private Set<MatchPath> matchPaths;
  private boolean lastAttemptedBranchInvalid = false;

  public MatchResult(
      final Set<MatchPath> matchPaths, final MatchPath currentMatchedFields1) {
    this.currentPath = currentMatchedFields1;
    this.matchPaths = matchPaths;
  }

  public static MatchResult createMatchResult() {
    return new MatchResult(Sets.newHashSet(), new MatchPath(Lists.newArrayList()));
  }

  public List<MatchPath> filterSublist(final List<MatchPath> lst) {
    List<MatchPath> uniq = new ArrayList<>(lst);
    lst.forEach(elem -> uniq.removeIf(x -> !x.equals(elem) && elem.isSupersetOf(x)));
    return uniq;
  }

  public String getBestBranchesString() {
    List<String> strings = Lists.newArrayList();

    List<MatchPath> matchPathsList = withoutDuplicates(matchPaths);
    matchPathsList = filterSublist(matchPathsList);

    for(int i = 0; i < matchPathsList.size(); i++) {
      strings.add(" " + (i+1) + ". " + matchPathsList.get(i));
    }
    return Joiner.on(System.lineSeparator()).join(strings);
  }

  public List<MatchPath> withoutDuplicates(final Set<MatchPath> matchPaths) {
    return matchPaths
        .stream().collect(toSet())
        .stream().sorted().collect(toList());
  }

  public boolean addMatched(final String fieldName) {
    return currentPath.addMatch(fieldName);
  }

  public MatchResult newBranch() {
    MatchPath pathCopy = currentPath.copy();
    matchPaths.add(pathCopy);
    return new MatchResult(matchPaths, pathCopy);
  }

}