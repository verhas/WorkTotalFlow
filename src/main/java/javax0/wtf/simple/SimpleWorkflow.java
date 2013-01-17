package javax0.wtf.simple;


import java.util.HashMap;
import java.util.Map;

import javax0.wtf.ActionDefinition;
import javax0.wtf.ResultDefinition;
import javax0.wtf.Step;
import javax0.wtf.StepDefinition;
import javax0.wtf.Workflow;
import javax0.wtf.WorkflowDefinition;

/**
 *
 * @author Peter Verhas
 */
public class SimpleWorkflow extends SimpleNamedEntity implements Workflow {
    // the steps the work flow instance is currently in

    private Map<StepDefinition, SimpleStep> stepCache =
        new HashMap<StepDefinition, SimpleStep>();

    protected SimpleStep getStep(StepDefinition stepDefinition) {
        SimpleStep step = null;
        if (!stepCache.containsKey(stepDefinition)) {
            step = new SimpleStep();
            step.setDefinition(stepDefinition);
            step.setWorkflow(this);
            stepCache.put(stepDefinition, step);
        }
        step = stepCache.get(stepDefinition);
        return step;
    }
    private Map<ActionDefinition, SimpleAction> actionCache =
        new HashMap<ActionDefinition, SimpleAction>();

    protected SimpleAction getAction(ActionDefinition actionDefinition) {
        SimpleAction action = null;
        if (!actionCache.containsKey(actionDefinition)) {
            action = new SimpleAction();
            action.setDefinition(actionDefinition);
            actionCache.put(actionDefinition, action);
        }
        action = actionCache.get(actionDefinition);
        return action;
    }
    private Map<ResultDefinition, SimpleResult> resultCache =
        new HashMap<ResultDefinition, SimpleResult>();

    protected SimpleResult getResult(ResultDefinition resultDefinition) {
        SimpleResult result = null;
        if (!resultCache.containsKey(resultDefinition)) {
            result = new SimpleResult();
            result.setDefinition(resultDefinition);
            resultCache.put(resultDefinition, result);
        }
        result = resultCache.get(resultDefinition);
        return result;
    }
    private WorkflowDefinition definition;

    public WorkflowDefinition getDefinition() {
        return definition;
    }

    public void setDefinition(WorkflowDefinition definition) {
        this.definition = definition;
    }
    private Step[] steps = null;

    public Step[] getSteps() {
        if (steps == null) {
            steps = new Step[1];
            StepDefinition stepDefinition = definition.getStartStep();
            SimpleStep startStep = getStep(stepDefinition);
            startStep.setWorkflow(this);
            steps[0] = startStep;
        }
        return steps;
    }

    /**
     * Get the steps for which the names are given.
     *
     * @param names the names of the steps
     * @return the array of the actual steps
     */
    protected Step[] getSteps(String[] names) {
        Step[] stepArray = new SimpleStep[names.length];
        SimpleWorkflowDefinition simpleWorkflowDefinition =
            (SimpleWorkflowDefinition)getDefinition();
         for (int i = 0; i < stepArray.length; i++) {
            StepDefinition stepDefinition =
                simpleWorkflowDefinition.getStepDefinition(names[i]);
            stepArray[i] = getStep(stepDefinition);
        }
        return stepArray;
    }

    public void setSteps(Step[] steps) {
        this.steps = steps;
    }

    /**
     * Sets the steps that the workflow is currently in using the helper method
     * {@code getStepDefinition(String name)}
     * of the {@see SimpleWorkflowImplementation} class.
     * <p>
     * Creates a new array for the steps and
     * @param names
     */
    public void setSteps(String[] names) {
        setSteps(getSteps(names));
    }

    /**
     * Returns the names of the steps that the workflow is currently in. The
     * method gathers the steps that it is currently in, and then copies the
     * names from the step definitions to a string array.
     * @return
     */
    public String[] getStepNames() {
        Step[] stepArray = getSteps();
        String[] stepNames = new String[stepArray.length];
        for (int i = 0; i < stepNames.length; i++) {
            stepNames[i] = stepArray[i].getDefinition().getName();
        }
        return stepNames;
    }
}
