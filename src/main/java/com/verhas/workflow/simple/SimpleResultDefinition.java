package com.verhas.workflow.simple;

import com.verhas.workflow.ResultDefinition;
import com.verhas.workflow.StepDefinition;

/**
 *
 * @author verhas
 */
public class SimpleResultDefinition extends SimpleNamedEntity implements ResultDefinition {


    @Override
    public String toString() {
        return getName();
    }

    private StepDefinition[] stepDefinitions;

    public void setStepDefinitions(StepDefinition[] stepDefinitions) {
        this.stepDefinitions = stepDefinitions;
    }

    public StepDefinition[] getStepDefinitions() {
        return stepDefinitions;
    }
}
