package com.verhas.workflow;

import javax0.wtf.Action;
import javax0.wtf.PreFunction;

/**
 * A sample pre function that does nothig but return {@ode null} as
 * a {@code transientObject}.
 * 
 * @author Peter Verhas
 */
public class SamplePreFunction implements PreFunction {

  public Object preFunction(Action action) {
    return null;
  }
}
