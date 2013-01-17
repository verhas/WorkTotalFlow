package javax0.wtf.simple;

import javax0.wtf.Action;
import javax0.wtf.Result;
import javax0.wtf.ResultDefinition;
import javax0.wtf.Step;
import javax0.wtf.StepDefinition;

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
