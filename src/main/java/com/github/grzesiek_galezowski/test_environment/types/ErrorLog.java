package com.github.grzesiek_galezowski.test_environment.types;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grzes on 17.07.2017.
 */

public class ErrorLog {
  private List<String> entries = new ArrayList<>();


  public void classContainsZeroFields(final BreadCrumbs breadCrumbs) {
    entries.add(breadCrumbs + ":" + "clazz contains zero fields");
  }

  public void nullObjectFound(final BreadCrumbs breadCrumbs) {
    entries.add(breadCrumbs + ":" + "null found");
  }

  public <T> void addError(final T sourceObject, final BreadCrumbs breadCrumbs, final Class expected) {
    entries.add(breadCrumbs + ": " + "expected " + expected.getSimpleName() + " but " + sourceObject.getClass().getSimpleName() + " found");
  }

  @Override
  public String toString() {
    String str = "";
    for(String entry : entries) {
      str += entry + "\n";
    }
    return str;
  }

  public void clear() {
    entries.clear();
  }
}
