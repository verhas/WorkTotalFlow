package javax0.wtf.simple;


import java.util.ArrayList;

import javax0.wtf.Action;
import javax0.wtf.ActionDefinition;
import javax0.wtf.Condition;
import javax0.wtf.ConditionDefinition;
import javax0.wtf.Step;
import javax0.wtf.StepDefinition;
import javax0.wtf.Workflow;

/**
 *
 * @author Peter Verhas
 */
public class SimpleStep implements Step {

    private Workflow workflow = null;

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public Workflow getWorkflow() {
        return workflow;
    }
    StepDefinition stepDefinition = null;

    public void setDefinition(StepDefinition stepDefinition) {
        if (this.stepDefinition != null) {
            throw new RuntimeException("Step definition is reassigned.");
        }
        this.stepDefinition = stepDefinition;
    }

    public StepDefinition getDefinition() {
        return stepDefinition;
    }
    private Action[] actions = null;

    public Action[] getActions() throws ClassNotFoundException,
                                        IllegalAccessException,
                                        InstantiationException {
        final ArrayList<Action> actionList = new ArrayList<Action>();
        for (Action action : getAllActions()) {
            ActionDefinition actionDefinition = action.getDefinition();
            final ConditionDefinition conditionDefinition =
                actionDefinition.getConditionDefinition();
            final Condition condition = conditionDefinition == null ? null : conditionDefinition.getFunction();
            //      final SimpleWorkflow simpleWorkflow = (SimpleWorkflow) getWorkflow();
            //      final SimpleAction simpleAction = simpleWorkflow.getAction(actionDefinition);
            //      simpleAction.setStep(this);
            if (condition == null || condition.canBePerformed(action)) {
                actionList.add(action);
            }
        }
        final Action[] actionArray = actionList.toArray(new Action[0]);
        return actionArray;
    }

    public Action[] getAllActions() {
        if (actions == null) {
            final ActionDefinition[] actionDefinitions =
                getDefinition().getActionDefinitions();
            actions = new Action[actionDefinitions.length];
            for (int i = 0; i < actionDefinitions.length; i++) {
                ActionDefinition actionDefinition = actionDefinitions[i];
                final SimpleWorkflow simpleWorkflow =
                    (SimpleWorkflow)getWorkflow();
                final SimpleAction action =
                    simpleWorkflow.getAction(actionDefinition);
                action.setStep(this);
                actions[i] = action;
            }
        }
        return actions;
    }
}
