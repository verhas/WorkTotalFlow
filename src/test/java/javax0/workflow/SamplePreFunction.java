package javax0.workflow;

import javax0.workflow.Action;
import javax0.workflow.Functions;

/**
 * A sample pre function that does nothig but return {@ode null} as
 * a {@code transientObject}.
 * 
 * @author Peter Verhas
 */
public class SamplePreFunction implements Functions.PreFunction {

  public Object preFunction(Action action) {
    return null;
  }
}
