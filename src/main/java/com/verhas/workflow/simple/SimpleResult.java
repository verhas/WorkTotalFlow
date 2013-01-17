package com.verhas.workflow.simple;

import com.verhas.workflow.Action;
import com.verhas.workflow.Result;
import com.verhas.workflow.ResultDefinition;
import com.verhas.workflow.Step;
import com.verhas.workflow.StepDefinition;

/**
 *
 * @author Peter Verhas
 */
public class SimpleResult implements Result {

    private Action action;

    public void setAction(Action action) {
        this.action = action;
    }

    public Action getAction() {
        return action;
    }
    private Step[] steps;

    public void setSteps(Step[] steps) {
        this.steps = steps;
    }

    public Step[] getSteps() {
        if (steps == null) {
            StepDefinition[] stepDefinitions =
                getDefinition().getStepDefinitions();
            SimpleWorkflow workflow =
                ((SimpleWorkflow)getAction().getStep().getWorkflow());
            steps = new SimpleStep[stepDefinitions.length];
            for (int i = 0; i < steps.length; i++) {
                steps[i] = workflow.getStep(stepDefinitions[i]);
            }
        }
        return steps;
    }
    ResultDefinition resultDefinition;

    public void setDefinition(ResultDefinition resultDefinition) {
        this.resultDefinition = resultDefinition;
    }

    public ResultDefinition getDefinition() {
        return resultDefinition;
    }
}
