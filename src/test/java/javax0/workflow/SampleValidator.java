package javax0.workflow;

import javax0.workflow.Action;
import javax0.workflow.Functions;
import javax0.workflow.Parameters;

/**
 * A sample validator that allways return true.
 * @author Peter Verhas
 */
public class SampleValidator implements Functions.Validator {

  public boolean valid(Action action, Object transientObject, Parameters userInput) {
    return true;
  }
}
