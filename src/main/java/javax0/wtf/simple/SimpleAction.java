package javax0.wtf.simple;

import java.util.Arrays;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax0.wtf.Action;
import javax0.wtf.ActionDefinition;
import javax0.wtf.PostFunction;
import javax0.wtf.PostFunctionDefinition;
import javax0.wtf.PreFunction;
import javax0.wtf.Result;
import javax0.wtf.ResultDefinition;
import javax0.wtf.Step;
import javax0.wtf.Validator;
import javax0.wtf.ValidatorDefinition;
import javax0.wtf.Workflow;
import javax0.wtf.exceptions.ValidatorFailedException;

/**
 *
 * @author Peter Verhas
 */
public class SimpleAction implements Action {

  private Step step;

  public void setStep(Step step) {
    this.step = step;
  }

  public Step getStep() {
    return step;
  }

  /**
   * Copy the content of the map {@code a} to the map {@code b}. The original
   * key/value pairs of the map {@code b} will remain in the map (they will
   * not be deleted before the copy, so this is a metge copy, but for any
   * key that present in both of the maps the value will be overwritten by the
   * value of the map {@code a} for the same key.
   *
   * @param a
   * @param b
   */
  private void copyAtoB(Map<String, String> a, Map<String, String> b) {
    if (a != null) {
      for (String key : a.keySet()) {
        String value = a.get(key);
        b.put(key, value);
      }
    }
  }

  /**
   * Merge maps {@code a} and {@code b} and return a new HashMap that contains
   * the key of both {@code a} and {@code b}. If both {@code a} and {@code b}
   * contain a key then the value will be the one that is present in {@code b}.
   *
   * @param a
   * @param b
   * @return
   */
  private Map<String, String> mergeMaps(Map<String, String> a,
          Map<String, String> b) {
    Map<String, String> m = new HashMap<String, String>();
    copyAtoB(a, m);
    copyAtoB(b, m);
    return m;
  }

  /**
   * Get the parameters from the action definition as well as from the action
   * declaration, and merge the two maps and return the result. In case key
   * has value in both of the maps then the one specified for the definitin
   * (under the XML tag of the step) is ruling overwrtinng the other one.
   * @return
   */
  public Map<String, String> getParameters() {
    SimpleActionDeclaration declaration =
            ((SimpleActionDefinition) getDefinition()).getActionDeclaration();
    Map<String, String> genericParameters = declaration.getParameters();
    Map<String, String> specificParameters =
            ((SimpleActionDefinition) getDefinition()).getParameters();
    return mergeMaps(genericParameters, specificParameters);
  }

  /**
   * Perform the pre and the post operations of the action. The implementation
   * calls the {@code performPre()} method and then the {@code performPost()}
   * method. The transient object returned by the method {@code performPre()}
   * is passed to the method {@code performPost()}.
   * <p>
   * Since calling this method there is no way for a user to specify any user
   * input between the pre and the post actions the user input parameter
   * passed to the method {@code performPost()} is {@code null}.
   * @return
   * @throws ValidatorFailedException
   */
  public Step[] perform() throws ValidatorFailedException,
          ClassNotFoundException,
          InstantiationException,
          IllegalAccessException {
    Object transientObject = performPre();
    return performPost(transientObject, null);
  }

  public Object performPre() throws ClassNotFoundException,
          InstantiationException,
          IllegalAccessException {
    PreFunction preFunction =
            getDefinition().getPreFunctionDefinition().getFunction();
    Object transientObject = null;
    if (preFunction != null) {
      transientObject = preFunction.preFunction(this);
    }
    return transientObject;
  }

  /**
   * Perform the post action of the workflow from step {@code step}
   * through the action {@code action}.
   * <p>
   * After the post function
   * @param step
   * @param action
   * @param transientObject
   * @param userInput
   * @return
   * @throws ValidatorFailedException
   */
  public Step[] performPost(Object transientObject,
          Map<String, String> userInput) throws ValidatorFailedException,
          ClassNotFoundException,
          InstantiationException,
          IllegalAccessException {
    ValidatorDefinition validatorDefinition =
            getDefinition() == null ? null
            : getDefinition().getValidatorDefinition();
    Validator validator =
            validatorDefinition == null ? null
            : validatorDefinition.getFunction();
    if (validator != null
            && !validator.valid(this, transientObject, userInput)) {
      throw new ValidatorFailedException();
    }
    PostFunctionDefinition postFunctionDefinition =
            getDefinition() == null ? null
            : getDefinition().getPostFunctionDefinition();
    PostFunction postFunction =
            postFunctionDefinition == null ? null
            : postFunctionDefinition.getFunction();
    Result result = null;
    if (postFunction != null) {
      result = postFunction.postFunction(this, transientObject, userInput);
    } else {
      result = getResults()[0];
    }
    Step[] newSteps = result.getSteps();
    mergeSteps(step, newSteps);
    return newSteps;
  }

  /**
   * Merge the steps newSteps into the existing array of steps that the
   * work flow is in currently.
   * <p>
   * This method is called after a post function is executed.
   *
   * @param step the step from which we transfer the work flow. This will
   * be removed from the current set of steps the work flow is in and will
   * only be added if it is contained by the argument {@code newSteps}.
   * @param newSteps the new steps that replace {@code step}
   */
  private void mergeSteps(Step step, Step[] newSteps) {
    Set<Step> stepSet = new HashSet<Step>();
    Step[] sourceSteps = getStep().getWorkflow().getSteps();
    if (sourceSteps != null) {
      stepSet.addAll(Arrays.asList(sourceSteps));
      stepSet.remove(step);
    }
    stepSet.addAll(Arrays.asList(newSteps));
    getStep().getWorkflow().setSteps(stepSet.toArray(new Step[0]));
  }
  private ActionDefinition actionDefinition = null;

  public void setDefinition(ActionDefinition actionDefinition) {
    this.actionDefinition = actionDefinition;
  }

  public ActionDefinition getDefinition() {
    return actionDefinition;
  }
  private Result[] results = null;

  /**
   * Get the array of results that belong to this action. If it was created before
   * and is available through the private field {@code results} then returns it.
   * If it was not created before then the field is created. The method
   * gets the action definition that belongs to this action and gets the
   * array of the result definitions. After that it creates an array that has
   * the same length as the definition and fills it with simple result objects.
   * <p>
   * Finally the simple result objects will all point to their corresponding
   * result definition so that later they can perform the method
   * {@code getDefinition()}
   *
   * @return the array of results.
   */
  public Result[] getResults() {
    if (results == null) {
      ResultDefinition[] resultDefinitions =
              getDefinition().getResultDefinitions();
      results = new Result[resultDefinitions.length];
      for (int i = 0; i < results.length; i++) {
        ResultDefinition resultDefinition = resultDefinitions[i];
        SimpleWorkflow simpleWorkflow =
                (SimpleWorkflow) getStep().getWorkflow();
        SimpleResult result =
                simpleWorkflow.getResult(resultDefinition);
        result.setAction(this);
        results[i] = result;
      }
    }
    return results;
  }

  /**
   * Return the result that has the name 'name'.
   * <p>
   * The implementation gets the array of possible results (this creates the
   * result array if it did not exist) and searches for a result in the array
   * that bears a definition that has the name. When it is found it is returned.
   * <p>
   * If no result is found for the name then {@code null} is returned.
   * @param name the name of the result we need
   * @return the result object
   */
  public Result getResult(String name) {
    for (Result result : getResults()) {
      if (name.equals(result.getDefinition().getName())) {
        return result;
      }
    }
    return null;
  }

  /**
   * Joins the steps passed as argument.
   *
   * @param steps
   */
  public void join(Step[] steps) {
    Workflow workflow = getStep().getWorkflow();
    Step[] originalSteps = workflow.getSteps();
    Set<Step> stepSet = new HashSet<Step>(originalSteps.length);
    for (Step s : originalSteps) {
      stepSet.add(s);
    }
    for (Step s : steps) {
      stepSet.remove(s);
    }
    Step[] targetStepSet = stepSet.toArray(originalSteps);
    workflow.setSteps(targetStepSet);
  }

  public void join(String[] stepNames) {
    Workflow workflow = getStep().getWorkflow();
    SimpleWorkflow simpleWorkflow = (SimpleWorkflow) workflow;
    join(simpleWorkflow.getSteps(stepNames));
  }
}
