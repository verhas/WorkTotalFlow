package com.verhas.workflow.simple.builder;

import com.verhas.workflow.ActionDefinition;
import com.verhas.workflow.ResultDefinition;
import com.verhas.workflow.StepDefinition;
import com.verhas.workflow.WorkflowBuilder;
import com.verhas.workflow.WorkflowDefinition;
import com.verhas.workflow.WorkflowEngine;
import com.verhas.workflow.simple.SimpleActionDeclaration;
import com.verhas.workflow.simple.SimpleActionDefinition;
import com.verhas.workflow.simple.SimpleConditionDefinition;
import com.verhas.workflow.simple.SimplePostFunctionDefinition;
import com.verhas.workflow.simple.SimplePreFunctionDefinition;
import com.verhas.workflow.simple.SimpleResultDefinition;
import com.verhas.workflow.simple.SimpleStepDefinition;
import com.verhas.workflow.simple.SimpleValidatorDefinition;
import com.verhas.workflow.simple.SimpleWorkflowDefinition;
import com.verhas.workflow.simple.SimpleWorkflowEngine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

/**
 * A very simple work flow builder that builds up a work flow from
 * an Apache Commons Configuration file.
 *
 * @author Peter Verhas
 */
public class ConfigBuilder implements WorkflowBuilder {

  private static Logger logger = Logger.getLogger(ConfigBuilder.class);
  private Configuration configuration;

  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }
  private SimpleWorkflowEngine engine;
  /**
   * This map contains all the step definitions that were read or referenced,
   * but not yet read from the configuration. Note that some of the steps in
   * this map are not ready (need to be filled with the apropriate values).
   * Specifically those that are not yet read from the configuration but were
   * referenced already.
   */
  private Map<String, SimpleStepDefinition> stepDefinitionMap;
  /**
   * This map contains all the step definition objects that were referenced but
   * not yet read from the configuration. Note that all the objects are under
   * processing which are in this set.
   */
  private Set<SimpleStepDefinition> stepsToRead;
  /**
   * This map contains the action declarations that were already defined. The
   * action definitions share the action declarations that specify the
   * functions and the name for the action.
   */
  private Map<String, SimpleActionDeclaration> actionDeclarationMap;

  private StepDefinition[] getAllStepDefinitions() {
    return stepDefinitionMap.values().toArray(new StepDefinition[0]);
  }

  /**
   * Get a step object based on its name.
   * <p>
   * If it does not yet exist then the method creates a new
   * {@code SimpleStepDefinition}
   * object and adds it to the {@code stepMap} map and also to the
   * set {@code stepsToRead}.
   * <p>
   * This way the set {@code stepsToRead} gets each step that was created
   * as an object but was not read from the XML file.
   *
   * @param name of the step
   * @return the step object
   */
  private SimpleStepDefinition getStepFromName(String name) {
    SimpleStepDefinition step;
    if (!stepDefinitionMap.containsKey(name)) {
      step = new SimpleStepDefinition();
      step.setName(name);
      stepDefinitionMap.put(name, step);
      stepsToRead.add(step);
    }
    step = stepDefinitionMap.get(name);
    return step;
  }

  /**
   * Build a simple action declaration based on its name.
   * <p>
   * The method creates a new action declaration object, sets its name and
   * reads the parameters from the configuration XML file and sets it to the
   * declaration calling its setter.
   * <p>
   * The method creates the four function definitionsn and sets their
   * class names.
   *
   * @param name the name of the action
   * @return the action declaration
   */
  private SimpleActionDeclaration buildActionDeclaration(String name) {
    SimpleActionDeclaration actionDeclaration =
            new SimpleActionDeclaration();
    actionDeclaration.setName(name);
    Integer actionSerial = getActionSerialBy(name);
    Map<String, String> parameters = getActionParameters(actionSerial);
    actionDeclaration.setParameters(parameters);
    // Condition
    SimpleConditionDefinition conditionDefinition =
            new SimpleConditionDefinition();
    final String conditionClass =
            getActionFunction(actionSerial, "condition");
    conditionDefinition.setClassName(conditionClass);
    conditionDefinition.setEngine(engine);
    actionDeclaration.setConditionDefinition(conditionDefinition);
    // PreFunction
    SimplePreFunctionDefinition preFunctionDefinition =
            new SimplePreFunctionDefinition();
    final String preFunctionClass =
            getActionFunction(actionSerial, "preFunction");
    preFunctionDefinition.setClassName(preFunctionClass);
    preFunctionDefinition.setEngine(engine);
    actionDeclaration.setPreFunctionDefinition(preFunctionDefinition);
    // Validator
    SimpleValidatorDefinition validatorDefinition =
            new SimpleValidatorDefinition();
    final String validatorClass =
            getActionFunction(actionSerial, "validator");
    validatorDefinition.setClassName(validatorClass);
    validatorDefinition.setEngine(engine);
    actionDeclaration.setValidatorDefinition(validatorDefinition);
    // PostFunction
    SimplePostFunctionDefinition postFunctionDefinition =
            new SimplePostFunctionDefinition();
    final String postFunctionClass =
            getActionFunction(actionSerial, "postFunction");
    postFunctionDefinition.setClassName(postFunctionClass);
    postFunctionDefinition.setEngine(engine);
    actionDeclaration.setPostFunctionDefinition(postFunctionDefinition);
    return actionDeclaration;
  }

  /**
   * Get an action declaration based on its name.
   * <p>
   * If it does not yet exist then the method creates a new
   * {@code SimpleActionDeclaration} object and adds it to the map
   * {@code actionDeclarationMap}.
   *
   * @param name
   * @return the actionDeclaration
   */
  private SimpleActionDeclaration getActionDeclarationFromName(String name) {
    SimpleActionDeclaration actionDeclaration;
    if (!actionDeclarationMap.containsKey(name)) {
      actionDeclaration = buildActionDeclaration(name);
      actionDeclarationMap.put(name, actionDeclaration);
    }
    actionDeclaration = actionDeclarationMap.get(name);
    return actionDeclaration;
  }

  /**
   * Get the serial number of an action in the configuration file based on its
   * name. The actions are configured as
   *
   * <pre>
   * &lt;actions&gt<br/>
   *   &lt;action name="actionname1" /&gt<br>
   *   &lt;action name="actionname2" /&gt<br>
   *   &lt;action name="actionname3" /&gt<br>
   *  ...<br>
   * &lt;/actions&gt<br>
   * </pre>
   * The serial number of an action in the configuration starts with zero.
   * @param name the name of the action as given in the XML file
   * @return the serial number of the action or {@code null} if the action is
   * not configured
   */
  private Integer getActionSerialBy(String name) {
    return getSomethingSerialBy("actions.action", name);
  }

  /**
   * Get the serial number of a step in the configuration file based on its
   * name. The steps are configured as
   *
   * <pre>
   * &lt;steps&gt<br/>
   *   &lt;step name="actionname" /&gt<br>
   *   &lt;step name="actionname" /&gt<br>
   *   &lt;step name="actionname" /&gt<br>
   *  ...<br>
   * &lt;/steps&gt<br>
   * </pre>
   * The serial number of a step in the configuration starts with zero.
   * @param name the name of the step as given in the XML file
   * @return the serial number of the step or {@code null} if the step is
   * not configured
   */
  private Integer getStepSerialBy(String name) {
    return getSomethingSerialBy("steps.step", name);
  }

  /**
   * Get the serial number of some configuration parameter based on the name
   * of the parameter as specified in the attribute {@code name="..."} and
   * based on the XML path prefix.
   *
   * @param prefix
   * @param name
   * @return
   */
  private Integer getSomethingSerialBy(String prefix, String name) {
    Integer i = 0;
    while (true) {
      String somethingName =
              configuration.getString(prefix + "(" + i + ")[@name]");
      if (somethingName == null) {
        return null;
      }
      if (somethingName.equals(name)) {
        return i;
      }
      i++;
    }

  }

  /**
   * Get the name of the class of a function for a given action and the
   * given function name.
   * @param actionSerial the serial of the action
   * @param configSubKey the configuration key for function (e.g.: "validator",
   * "preFunction", "condition", "postFunction")
   * @return
   */
  private String getActionFunction(Integer actionSerial,
          String configSubKey) {
    String actionClassName =
            configuration.getString("actions.action(" + actionSerial + ")[@"
            + configSubKey + "]");
    return actionClassName;
  }

  /**
   * Read the parameters that are under a certain prefix.
   * <p>
   * The configuration may contain at several points parameters given as
   * <pre>
   *  ...
   *      &lt;parameter name="scriptLanguage" value="groovy"/&gt;
   *      &lt;parameter name="script&gt;myGroovyScript.groovy&lt;/parameter&gt;
   *  ...
   * &lt;/step&gt;
   * </pre>
   * These are usually for actions (generally), and for specific use of
   * actions in a step.
   * <p>
   * The name of the parameter is given in the attribute 'name' and the
   * value in the attribute 'value'. If the attribute is not defined then
   * the value of the XML tag (the text element) will be used as parameter
   * value. If neither of the parameter values are defined that the value
   * will be {@code null}.
   * <p>
   * This method parses the XML configuration and builds up the map that
   * contains the parameters and the values.
   *
   * @param prefix is the string that leads to the specific part of the
   * configuration where the parameters are. This can be something like
   * {@code actions.action(i).} or {@code steps.step(j).actions.action(j).}.
   * Note that there is a dot at the end of the prefix.
   * @return the map of parameters for the specified (step,action) pair.
   */
  private Map<String, String> getParametersAfterPrefix(String prefix) {
    Map<String, String> parameters = new HashMap<String, String>();
    for (int i = 0; true; i++) {
      String parameter =
              configuration.getString(prefix + "parameter(" + i
              + ")[@name]");
      if (parameter == null) {
        break;
      }
      String value =
              configuration.getString(prefix + "parameter(" + i + ")[@value]");
      if (value == null) {
        value =
                configuration.getString(prefix + "parameter(" + i + ")");
      }
      if (value != null) {
        parameters.put(parameter, value);
      }
    }
    return parameters;
  }

  /**
   * Read the action parameters for a step, action pair.
   * <p>
   * The configuration may contain
   * <pre>
   *
   * &lt;step name="name of the step"&gt;
   *  &lt;actions&gt;
   *    &lt;action name="start"&gt;
   *      &lt;parameter name="scriptLanguage" value="groovy"/&gt;
   *      &lt;parameter name="script&gt;myGroovyScript.groovy&lt;/parameter&gt;
   *    &lt;/action&gt;
   *  ...
   * &lt;/step&gt;
   * </pre>
   * to specify all parameters that are given for an action when used in a step.
   * <p>
   * See also {@see #getParametersAfterPrefix(String prefix)}, which is used
   * by this method.
   * <p>
   * This method parses the XML configuration and builds up the map that
   * contains the parameters and the values.
   *
   * @param step the step in which the action are used
   * @param action the action for which the parameters are to be built up
   * @return the map of parameters for the specified (step,action) pair.
   */
  private Map<String, String> getActionParameters(Integer stepSerial,
          Integer actionSerial) {
    String prefix =
            "steps.step(" + stepSerial + ").actions.action(" + actionSerial
            + ").";
    return getParametersAfterPrefix(prefix);
  }

  /**
   * Read the parameters for an action. These parameters are those that were
   * specified in the action declaration and stand for all use of the action.
   *
   * @param actionSerial the serial number of the action
   * @return the parameters map
   */
  private Map<String, String> getActionParameters(Integer actionSerial) {
    String prefix = "actions.action(" + actionSerial + ").";
    return getParametersAfterPrefix(prefix);
  }

  /**
   * Get he names of the results for a specific action in a specific step.
   *
   * @param stepSerial the serial number of the step in the configuration
   * @param actionSerial the serial number of the action inside the step in the
   * XML configuration
   * @return the names of the results
   */
  private String[] getResultNamesForStepAndAction(Integer stepSerial,
          Integer actionSerial) {
    return configuration.getStringArray("steps.step(" + stepSerial
            + ").actions.action("
            + actionSerial + ").result[@name]");
  }

  /**
   * Get the names of the actions that can be performed from the action given
   * for the argument {@code stepSerial}.
   *
   * @param stepSerial the serial number of the step in the XML file.
   * @return the array of names.
   */
  private String[] getActionNamesForStep(Integer stepSerial) {
    return configuration.getStringArray("steps.step(" + stepSerial
            + ").actions.action[@name]");
  }

  private String[] getStepNamesForStepAndActionAndResult(Integer stepSerial,
          Integer actionSerial,
          Integer resultSerial) {
    return configuration.getStringArray("steps.step(" + stepSerial
            + ").actions.action("
            + actionSerial + ").result("
            + resultSerial + ").step");
  }

  /**
   * Get the name of an action for the given step serial number and the
   * action serial.
   *
   * @param stepSerial
   * @param actionSerial
   * @return the name of the action
   */
  private String getActionNameForStepAndAction(Integer stepSerial,
          Integer actionSerial) {
    return configuration.getString("steps.step(" + stepSerial
            + ").actions.action(" + actionSerial
            + ")[@name]");
  }

  /**
   * Get the name of a result for the given step serial, action serial and
   * result serial.
   *
   * @param stepSerial
   * @param actionSerial
   * @param resultSerial
   * @return
   */
  private String getResultNameForStepAndActionAndResult(Integer stepSerial,
          Integer actionSerial,
          Integer resultSerial) {
    return configuration.getString("steps.step(" + stepSerial
            + ").actions.action(" + actionSerial
            + ").result(" + resultSerial
            + ")[@name]");
  }

  /**
   * Build up a result definition.
   * <p>
   * Note that the argument {@code stepSerial} and {@code actionSerial} are
   * redundant, they could be calculated from the argument
   * {@code actionDefinition}. However the calculation would require a search in
   * the XML file and these serial numbers are available alread on the called.
   *
   * @param actionDefinition the action that may return this result
   * @param stepSerial the serial of the step the action belongs to
   * in the XML file
   * @param actionSerial the serial number of the action in the XML file
   * @param resultSerial the serial number of the result in the XML file
   * @return
   */
  private ResultDefinition buildResultDefinition(Integer stepSerial,
          Integer actionSerial,
          Integer resultSerial) {
    SimpleResultDefinition resultDefinition = new SimpleResultDefinition();
    String name =
            getResultNameForStepAndActionAndResult(stepSerial, actionSerial,
            resultSerial);
    resultDefinition.setName(name);
    String[] stepNames =
            getStepNamesForStepAndActionAndResult(stepSerial, actionSerial,
            resultSerial);
    SimpleStepDefinition[] stepDefinitions =
            new SimpleStepDefinition[stepNames.length];
    for (int i = 0; i < stepDefinitions.length; i++) {
      stepDefinitions[i] = getStepFromName(stepNames[i]);
    }
    resultDefinition.setStepDefinitions(stepDefinitions);
    return resultDefinition;
  }

  /**
   * Build up an action definition.
   * <p>
   * Note tha the argument {@code stepSerial} is redundand. However this is the
   * result of a search and is readily available at the called therefore it
   * would not be wise to repeat the search.
   *
   * @param stepDefinition the step definition from where
   * the action can be executed.
   * @param stepSerial the serial number of the step in the cofiguration
   * @param actionSerial the serial number of the action in the stepcific step
   * @return the new action definition
   */
  private SimpleActionDefinition buildActionDefinition(SimpleStepDefinition stepDefinition,
          Integer stepSerial,
          Integer actionSerial) {
    SimpleActionDefinition actionDefinition = new SimpleActionDefinition();
    actionDefinition.setStepDefinition(stepDefinition);
    SimpleActionDeclaration actionDeclaration =
            getActionDeclarationFromName(getActionNameForStepAndAction(stepSerial,
            actionSerial));
    actionDefinition.setActionDeclaration(actionDeclaration);

    Map<String, String> actionParameters =
            getActionParameters(stepSerial, actionSerial);
    actionDefinition.setParameters(actionParameters);

    String[] resultNames =
            getResultNamesForStepAndAction(stepSerial, actionSerial);
    ResultDefinition[] resultDefinitions =
            new ResultDefinition[resultNames.length];
    for (int i = 0; i < resultDefinitions.length; i++) {
      resultDefinitions[i] =
              buildResultDefinition(stepSerial, actionSerial, i);
    }
    actionDefinition.setResultDefinitions(resultDefinitions);
    return actionDefinition;
  }

  /**
   * Build up a single step. When a step is ready remove it from the set
   * {@code stepsToRead}.
   * <p>
   * A step has to be configured in the configuration file with all the
   * actions that can be executed from that step.
   * <p>
   * The method reads the actions, and then for each action the parameters and
   * the possible results and the target steps attached to the result.
   *
   * @param stepDefinition the step to build up.
   * @throws IllegalAccessException when a step is referenced by name, but the
   * step is not defined in the XML file
   */
  private void buildStepDefinition(SimpleStepDefinition stepDefinition) throws ClassNotFoundException,
          InstantiationException,
          IllegalAccessException {
    Integer stepSerial = getStepSerialBy(stepDefinition.getName());
    if (stepSerial == null) {
      throw new IllegalAccessException("There is no step configured with name '"
              + stepDefinition.getName() + "'");
    }

    String[] actionNames = getActionNamesForStep(stepSerial);
    ActionDefinition[] actionDefinitions =
            new ActionDefinition[actionNames.length];
    stepDefinition.setActionDefinitions(actionDefinitions);
    for (int i = 0; i < actionDefinitions.length; i++) {
      actionDefinitions[i] =
              buildActionDefinition(stepDefinition, stepSerial, i);
    }
    stepsToRead.remove(stepDefinition);
  }

  /**
   * Build all steps and put them into the {@code stepMap} map. The method
   * iterates through the elements of the set {@code stepsToRead} which
   * contains all the steps that were created as objects but were not read
   * from the configuration XML. When a step is read from the XML it may
   * reference other steps that were not read yet. These will get into the set
   * {@code stepToRead}, therefore the iteration through the steps to read
   * is continued until the set gets empty.
   */
  private void buildSteps() throws ClassNotFoundException,
          InstantiationException,
          IllegalAccessException {
    while (!stepsToRead.isEmpty()) {
      SimpleStepDefinition step = stepsToRead.iterator().next();
      buildStepDefinition(step);
    }
  }

  /**
   * Initialize the internal maps and sets of the builder.
   */
  private void initMaps() {
    stepDefinitionMap = new HashMap<String, SimpleStepDefinition>();
    stepsToRead = new HashSet<SimpleStepDefinition>();
    actionDeclarationMap = new HashMap<String, SimpleActionDeclaration>();
  }

  /**
   * Build a work flow definition and return the built up structure.
   * <p>
   * The method gets the initial step and then follow the reachable steps
   * using their names and building up a closure finally
   * that contains all steps. The steps that are defined in the XML but
   * cannot be reached via actions from the start step will be ignored.
   * @params engine has to be an instance of {@code SimpleWorkflowEngine}.
   * @return the work flow definition.
   */
  public WorkflowDefinition build(WorkflowEngine engine) {
    try {
      this.engine = (SimpleWorkflowEngine) engine;
      initMaps();
      String startStepName = configuration.getString("[@start]");
      SimpleStepDefinition startStep = getStepFromName(startStepName);
      buildSteps();
      SimpleWorkflowDefinition workflowDefinition =
              new SimpleWorkflowDefinition();
      workflowDefinition.setStepDefinitions(getAllStepDefinitions());
      workflowDefinition.setStartStep(startStep);
      this.engine.register(workflowDefinition);
      return workflowDefinition;
    } catch (Exception ex) {
      logger.error("The workflow was not loaded.", ex);
      return null;
    }
  }
}
