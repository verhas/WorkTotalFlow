package com.verhas.workflow.simple.builder;
//
//import com.verhas.workflow.ActionDefinition;
//import com.verhas.workflow.Condition;
//import com.verhas.workflow.Function;
//import com.verhas.workflow.PostFunction;
//import com.verhas.workflow.PreFunction;
//import com.verhas.workflow.ResultDefinition;
//import com.verhas.workflow.StepDefinition;
//import com.verhas.workflow.Validator;
import com.verhas.workflow.WorkflowBuilder;
import com.verhas.workflow.WorkflowDefinition;
import com.verhas.workflow.WorkflowEngine;
//import com.verhas.workflow.simple.SimpleActionDefinition;
//import com.verhas.workflow.simple.SimpleResultDefinition;
//import com.verhas.workflow.simple.SimpleStepDefinition;
//import com.verhas.workflow.simple.SimpleWorkflowDefinition;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Properties;
//import java.util.Set;
//
///**
// * A very simple work flow builder that builds up a work flow from
// * a files from a directory.
// *<p>
// * Note that this version was never tested. Not even a unit test was created
// * for this version. (unless it is already a later version and I only forgot to
// * delete this note)
// * @author Peter Verhas
// */
public class FileBuilder implements WorkflowBuilder {
    //
    //  private File directory;
    //  private File workflowFile;
    //
    //  public void setWorkflowFile(File file) {
    //    workflowFile = file;
    //    this.directory = file.getParentFile();
    //  }
    //  private Map<String, SimpleActionDefinition> actionMap;
    //  private Map<String, SimpleStepDefinition> stepMap;
    //  private Map<String, SimpleResultDefinition> resultMap;
    //  private Set<SimpleStepDefinition> stepsToRead;
    //  private String[] parameters;
    //  private String[] resultNames;
    //  // this map collects the created object instances
    //  // the keys in the map are the fully qualified class name
    //  private Map<String, Function> functionMap;
    //  private Map<String, Properties> fileProperties = null;
    //
    //  /**
    //   * Get a result object based on its name. If it does not exist then it
    //   * creates a {@code SimpleResultDefinition} and places it into the
    //   * {@code resultMap}
    //   *
    //   * @param name the name of the step
    //   * @param result used to help overloading. Supposed to be (ResultDefinition)null.
    //   * @return the result
    //   */
    //  private SimpleResultDefinition getFromName(String name, SimpleResultDefinition result) {
    //    if (!resultMap.containsKey(name)) {
    //      result = new SimpleResultDefinition();
    //      result.setName(name);
    //      resultMap.put(name, result);
    //    }
    //    result = resultMap.get(name);
    //    return result;
    //  }
    //
    //  /**
    //   * Get a step object based on its name. If it does not exist then the
    //   * method creates a new {@code SimpleStepDefinition} object and adds it to the
    //   * {@code stepMap} map and also to the set {@code stepsToRead}.
    //   *
    //   * @param name
    //   * @param step
    //   * @return
    //   */
    //  private SimpleStepDefinition getFromName(String name, SimpleStepDefinition step) {
    //    if (!stepMap.containsKey(name)) {
    //      step = new SimpleStepDefinition();
    //      step.setName(name);
    //      stepMap.put(name, step);
    //      stepsToRead.add(step);
    //    }
    //    step = stepMap.get(name);
    //    return step;
    //  }
    //
    //  /**
    //   * Get the properties based on file name.
    //   * If the file was already parsed and loaded then return the cached value.
    //   *
    //   * @param fileName the name of the file inside the directory
    //   * @return
    //   */
    //  private Properties getProperties(String fileName) {
    //    Properties props = null;
    //    if (fileProperties == null) {
    //      fileProperties = new HashMap<String, Properties>();
    //    }
    //    if (fileProperties.containsKey(fileName)) {
    //      props = fileProperties.get(fileName);
    //    } else {
    //      File file = new File(directory.getAbsolutePath()
    //              + File.pathSeparator
    //              + fileName);
    //      props = new Properties();
    //      InputStream is = null;
    //      try {
    //        is = new FileInputStream(file);
    //        props.load(is);
    //        is.close();
    //        fileProperties.put(fileName, props);
    //      } catch (Exception e) {
    //      } finally {
    //        if (is != null) {
    //          try {
    //            is.close();
    //          } catch (Exception e) {
    //          }
    //        }
    //      }
    //    }
    //    return props;
    //  }
    //
    //  private String getActionFunction(SimpleActionDefinition action, String configSubKey) {
    //    Properties props = getProperties(action.getName());
    //    if (props != null) {
    //      return props.getProperty(configSubKey);
    //    } else {
    //      return null;
    //    }
    //  }
    //
    //  /**
    //   * Get an action object based on its name. If the object does not exist
    //   * it creates it and places it into the {@code actionMap}. When an action
    //   * is created it also reads the condition, pre-function, validator and
    //   * post-function of the action from the configuration. These may be
    //   * defined as follows:
    //   * <p>
    //   * <pre>
    //   * forAction.'action name'.conditionIs = com.verhas.workflow.sampleCondition
    //   * forAction.'action name'.preFunctionIs = com.verhas.workflow.samplePreFunction
    //   * forAction.'action name'.validatorIs = com.verhas.workflow.sampleValidator
    //   * forAction.'action name'.postFunctionIs = com.verhas.workflow.samplePostFunction
    //   * </pre>
    //   * @param name
    //   * @param action
    //   * @return
    //   */
    //  private SimpleActionDefinition getFromName(String name, SimpleActionDefinition action)
    //          throws ClassNotFoundException,
    //          InstantiationException,
    //          InstantiationException,
    //          IllegalAccessException {
    //    if (!actionMap.containsKey(name)) {
    //      action = new SimpleActionDefinition();
    //      action.setName(name);
    //      actionMap.put(name, action);
    //      String functionName;
    //      functionName = getActionFunction(action, "condition");
    //      if (functionName != null) {
    //        action.setCondition((Condition) getFromName(functionName,
    //                (Function) null));
    //      }
    //      functionName = getActionFunction(action, "validator");
    //      if (functionName != null) {
    //        action.setValidator((Validator) getFromName(functionName,
    //                (Function) null));
    //      }
    //      functionName = getActionFunction(action, "preFunction");
    //      if (functionName != null) {
    //        action.setPreFunction((PreFunction) getFromName(functionName,
    //                (Function) null));
    //      }
    //      functionName = getActionFunction(action, "postFunction");
    //      if (functionName != null) {
    //        action.setPostFunction((PostFunction) getFromName(functionName,
    //                (Function) null));
    //      }
    //    }
    //    action = actionMap.get(name);
    //    return action;
    //  }
    //
    //  /**
    //   * Get a function by its fully qualified class name. If the function
    //   * class is not instantiated for this work flow then instantiate it
    //   * using the default constructor.
    //   *
    //   * @param className the fully qualified class name for the class
    //   * that implements the function
    //   * @param function parameter not used. It should be passed to help
    //   * overloading. It is supposed to be {@code (Function)null}.
    //   * @return the object instance
    //   * @throws ClassNotFoundException
    //   * @throws InstantiationException
    //   * @throws IllegalAccessException
    //   */
    //  private Function getFromName(String className, Function function)
    //          throws ClassNotFoundException,
    //          InstantiationException,
    //          IllegalAccessException {
    //    if (!functionMap.containsKey(className)) {
    //      function = (Function) Class.forName(className).newInstance();
    //      functionMap.put(className, function);
    //    }
    //    function = functionMap.get(className);
    //    return function;
    //  }
    //
    //  /**
    //   * Convert the string array {@code names} to a {@code SimpleStepDefinition[]}
    //   * array using the {@code stepMap}.
    //   *
    //   * @param names the names
    //   * @return the {@code StepDefinition[]} array converted
    //   */
    //  private SimpleStepDefinition[] convertNames(String[] names) {
    //    SimpleStepDefinition[] targetSteps = new SimpleStepDefinition[names.length];
    //    for (int i = 0; i < names.length; i++) {
    //      targetSteps[i] = getFromName(names[i], (SimpleStepDefinition) null);
    //    }
    //    return targetSteps;
    //  }
    //
    //  /**
    //   * Get the map of the results vs. steps that tells the work flow
    //   * which steps to step into after an action was executed from the
    //   * specific source step.
    //   * <p>
    //   * The possible results for an action (no matter what step was the source
    //   * step) has to be defined as
    //   * <pre>
    //   * forAction.'action name'.possibleResultsAre = result1, result2, ... , resultN
    //   * </pre>
    //   * <p>
    //   * The steps that follow the source step for an action with a certain result
    //   * has to be defined in the configuration as
    //   * <pre>
    //   * fromStep.'step name'.executingAction.'action name'.withResult.'result name'.willMoveTo = step1, step2, ..., stepN
    //   * </pre>
    //   *
    //   * @param step the source step
    //   * @param action the action which may give the results
    //   * @return the map
    //   */
    //  private Map<ResultDefinition, StepDefinition[]> getResultStepArrayMap(StepDefinition step,
    //          SimpleActionDefinition action) {
    //    Map<ResultDefinition, StepDefinition[]> resultStepMap =
    //            new HashMap<ResultDefinition, StepDefinition[]>();
    //    for (String resultName : resultNames) {
    //      SimpleResultDefinition result = getFromName(resultName, (SimpleResultDefinition) null);
    //      Properties props = getProperties(step.getName());
    //
    //      String[] targetStepNames = props.getProperty("actions."
    //              + action.getName() + "."
    //              + "results."
    //              + result.getName()).split(",");
    //      if (targetStepNames != null) {
    //        resultStepMap.put(result,
    //                convertNames(targetStepNames));
    //      }
    //    }
    //    return resultStepMap;
    //  }
    //
    //  /**
    //   * Read the action parameters for a step, action pair.
    //   * <p>
    //   * The configuration should contain a line
    //   * <pre>
    //   *     parameters=a,b,c,d ...
    //   * </pre>
    //   * that lists all possible parameters. If a parameter name is used but
    //   * is not declared here it will be ignored, because the builder will not
    //   * find it.
    //   * <p>
    //   * A parameter 'a' may have a value and can be defined in the configuration
    //   * as
    //   * <pre>
    //   *     inStep.'step name'.theAction.'action name'.hasParameter.'a' = value of a
    //   * </pre>
    //   *
    //   * @param step
    //   * @param action
    //   * @return the map of parameters for the specified (step,action) pair.
    //   */
    //  private Map<String, String> getActionParameters(SimpleStepDefinition step,
    //          SimpleActionDefinition action) {
    //    Map<String, String> actionParameters = new HashMap<String, String>();
    //    Properties props = getProperties(step.getName());
    //    for (String parameter : parameters) {
    //      String value = props.getProperty("actions."
    //              + action.getName() + "."
    //              + "parameters."
    //              + parameter);
    //      if (value != null) {
    //        actionParameters.put(parameter, value);
    //      }
    //    }
    //    return actionParameters;
    //  }
    //
    //  /**
    //   * Build up a single step. When a step is ready remove it from the
    //   * {@code stepsToRead} set.
    //   * <p>
    //   * A step has to be configured with all the
    //   * actions that can be executed from that step the following way:
    //   * <pre>
    //   *     actions = action1, action2, ..., actionN
    //   * </pre>
    //   * The method reads the actions, and then for each action the parameters and
    //   * the possible results and the target steps attached to the result.
    //   *
    //   * @param step the step to build up.
    //   */
    //  private void buildStep(SimpleStepDefinition step) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    //    Properties props = getProperties(step.getName());
    //    String[] keys =
    //            props.getProperty("actions").split("\\,");
    //    Set<String> actionNameSet = new HashSet<String>();
    //    for( String key : keys ) {
    //      actionNameSet.add(key);
    //    }
    //    String[] actionNames = actionNameSet.toArray(new String[0]);
    //    ActionDefinition[] actions = new ActionDefinition[actionNames.length];
    //    step.setActions(actions);
    //
    //    Map<ActionDefinition, Map<ResultDefinition, StepDefinition[]>> actionResultStepArrayMap =
    //            new HashMap<ActionDefinition, Map<ResultDefinition, StepDefinition[]>>();
    //    step.setActionResultStepArrayMap(actionResultStepArrayMap);
    //
    //    Map<ActionDefinition, Map<String, String>> actionParametersMap =
    //            new HashMap<ActionDefinition, Map<String, String>>();
    //    step.setActionParameters(actionParametersMap);
    //
    //    //
    //
    //    int i = 0;
    //    for (String actionName : actionNames) {
    //      SimpleActionDefinition action = getFromName(actionName, (SimpleActionDefinition) null);
    //      actions[i++] = action;
    //      actionResultStepArrayMap.put(action,
    //              getResultStepArrayMap(step, action));
    //      actionParametersMap.put(action, getActionParameters(step, action));
    //    }
    //    stepsToRead.remove(step);
    //  }
    //
    //  /**
    //   * Build all steps and put them into the {@code stepMap} map.
    //   */
    //  private void buildSteps() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    //    while (!stepsToRead.isEmpty()) {
    //      SimpleStepDefinition step = stepsToRead.iterator().next();
    //      buildStep(step);
    //    }
    //  }
    //
    //  /**
    //   * Initialize the internal maps and sets of the builder.
    //   */
    //  private void initMaps() {
    //    actionMap = new HashMap<String, SimpleActionDefinition>();
    //    stepMap = new HashMap<String, SimpleStepDefinition>();
    //    resultMap = new HashMap<String, SimpleResultDefinition>();
    //    functionMap = new HashMap<String, Function>();
    //    stepsToRead = new HashSet<SimpleStepDefinition>();
    //  }
    //
    //  /**
    //   * Build a work flow definition and return the built up structure.
    //   * @return the work flow definition.
    //   */
    //    public WorkflowDefinition build() {
    //    return build(null);
    //  }
    //

    public WorkflowDefinition build(WorkflowEngine workflowEngine) {
        //    try {
        //      initMaps();
        //      Properties props = getProperties(workflowFile.getName());
        //      String workflowName = props.getProperty("name");
        //      String startStepName = props.getProperty("startStep");
        //      parameters = props.getProperty("parameters").split("\\,");
        //      resultNames = props.getProperty("results").split("\\,");
        //      SimpleStepDefinition startStep = getFromName(startStepName, (SimpleStepDefinition) null);
        //      buildSteps();
        //      SimpleWorkflowDefinition workflowDefinition = new SimpleWorkflowDefinition();
        //      if (workflowEngine != null) {
        //        workflowDefinition.setEngine(workflowEngine);
        //        workflowEngine.register(workflowDefinition, workflowName);
        //      }
        //      workflowDefinition.setSteps(stepMap.values().toArray(new SimpleStepDefinition[0]));
        //      workflowDefinition.setStartStep(startStep);
        //      return workflowDefinition;
        //    } catch (Exception ex) {
        return null;
        //    }
    }
}
