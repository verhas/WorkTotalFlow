package com.verhas.workflow.simple.builder;

import com.verhas.workflow.Action;
import com.verhas.workflow.ActionDefinition;
import com.verhas.workflow.Step;
import com.verhas.workflow.WorkflowDefinition;
import com.verhas.workflow.WorkflowEngine;
import com.verhas.workflow.exceptions.InvalidStateException;
import com.verhas.workflow.exceptions.ValidatorFailedException;
import com.verhas.workflow.simple.SimpleWorkflow;
import com.verhas.workflow.simple.SimpleWorkflowEngine;
import java.util.Map;
import junit.framework.TestCase;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;

/**
 *
 * @author Peter Verhas
 */
public class ConfigBuilderTest extends TestCase {

  public void testA() throws ConfigurationException,
          ValidatorFailedException,
          InvalidStateException,
          ClassNotFoundException,
          IllegalAccessException,
          InstantiationException {
    ConfigurationFactory cf = new ConfigurationFactory("config.xml");
    Configuration conf = cf.getConfiguration();
    ConfigBuilder cb = new ConfigBuilder();
    cb.setConfiguration(conf);
    WorkflowEngine workflowEngine = new SimpleWorkflowEngine();
    WorkflowDefinition wd = cb.build(workflowEngine);
    assertNotNull(wd);
    SimpleWorkflow sw = new SimpleWorkflow();
    sw.setDefinition(wd);
    // get the start step
    Step[] steps = sw.getSteps();
    // since we just start, there should be no more than one step
    assertEquals(1, steps.length);
    // and it has to be initalStep because that is in the
    // workflow definition file
    assertEquals("initialStep", steps[0].getDefinition().getName());
    // we get the step
    Step step = steps[0];
    // we get the actions from the step
    ActionDefinition[] actionDefinitions = step.getDefinition().getActionDefinitions();
    // there are three actions defined in the workflow for this step:
    // start, cutOff and finish
    assertEquals(3, actionDefinitions.length);
    // get the actions that meet their condition (condition functions are
    // executed and only those are collected here that return true)
    // or if there is no condition
    Action[] actions = step.getActions();
    // only action 'start' can be performed because the 'canBePerformed'
    // method of 'SampleCondition' returns true iff the action is 'start'
    assertEquals(1, actions.length);
    // get the action 'start'
    Action action = actions[0];
    assertEquals("start", action.getDefinition().getName());
    // get the parameters for this action in this step
    Map<String, String> params = action.getParameters();
    // there are two parameters defined for the action 'start'
    // in step 'initialStep'
    assertEquals(3, params.keySet().size());
    // execute the pre function and get the transient object
    Object trans = action.performPre();
    assertNull(trans);
    // execute the post function
    // SamplePostFunction method "postFunction" returns a result "OK"
    // that leads to 'secondStep'
    action.performPost(trans, null);
    steps = sw.getSteps();
    assertEquals(1, steps.length);
    step = steps[0];
    assertEquals("secondStep", step.getDefinition().getName());
    actions = step.getAllActions();
    assertEquals(1, actions.length);
    action = actions[0];
    assertEquals("finish", action.getDefinition().getName());
    // we do not care about conditions, but execute the only one action we
    // selected
    action.perform();
    // get the steps we got into
    steps = sw.getSteps();
    assertEquals(2, steps.length);
  }
}
