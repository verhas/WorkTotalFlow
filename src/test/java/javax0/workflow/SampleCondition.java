package javax0.workflow;

import javax0.workflow.Action;
import javax0.workflow.Functions;
import javax0.workflow.Parameters;

/**
 * A sample condition that retrieves the parameter
 * {@code genericActionParameterName} and asserts that it is
 * {@code genericActionParameterValue}. Otherwise throws a runtime exception,
 * which is a bad practice, but this code is only for unit test.
 * <p>
 * The condition is only {@code true} if the action is "start".
 * @author Peter Verhas
 */
public class SampleCondition implements Functions.Condition {

  public boolean canBePerformed(Action action) {
    Parameters actionParameters = action.getParameters();
    String genericActionParameterValue = actionParameters.get("genericActionParameterName");
    if (action.getDefinition().getName().equals("start") && !genericActionParameterValue.equals("genericActionParameterValue")) {
      throw new RuntimeException("The genericActionParameter has bad value: '" + genericActionParameterValue + "'");
    }
    return action.getDefinition().getName().equals("start");
  }
}
