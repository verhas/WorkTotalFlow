package com.verhas.workflow.simple;

import com.verhas.workflow.StepDefinition;
import com.verhas.workflow.WorkflowDefinition;
import com.verhas.workflow.WorkflowEngine;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Peter Verhas
 */
public class SimpleWorkflowDefinition extends SimpleNamedEntity implements WorkflowDefinition {

    private WorkflowEngine engine;

    public WorkflowEngine getEngine() {
        return engine;
    }

    public void setEngine(WorkflowEngine engine) {
        this.engine = engine;
    }
    // the array of all the steps that are in the workflow
    private StepDefinition[] stepDefinitions;

    public StepDefinition[] getStepDefinitions() {
        return stepDefinitions;
    }
    private StepDefinition startStep;

    public StepDefinition getStartStep() {
        return startStep;
    }

    public void setStartStep(StepDefinition startStep) {
        this.startStep = startStep;
    }
    private Map<String, StepDefinition> stepDefinitionMap = null;

    public void setStepDefinitions(StepDefinition[] stepDefinitions) {
        this.stepDefinitions = stepDefinitions;
        stepDefinitionMap = new HashMap<String, StepDefinition>();
        for (StepDefinition stepDefinition : stepDefinitions) {
            stepDefinitionMap.put(stepDefinition.getName(), stepDefinition);
        }
    }

    public StepDefinition getStepDefinition(String name) {
        if (stepDefinitionMap.containsKey(name)) {
            return stepDefinitionMap.get(name);
        } else {
            return null;
        }
    }
}