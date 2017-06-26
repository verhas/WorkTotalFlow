package javax0.workflow;

import javax0.workflow.*;

/**
 *
 * @author Peter Verhas
 */
public class SamplePostFunction implements Functions.PostFunction {

  public Result postFunction(Action action,
          Object transientObject,
          Parameters userInput) {
    return action.getResult("OK");
  }
}
