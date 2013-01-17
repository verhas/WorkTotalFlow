package javax0.wtf.simple;

import javax0.wtf.ActionDefinition;
import javax0.wtf.StepDefinition;

/**
 *
 * @author Peter Verhas
 */
public class SimpleStepDefinition extends SimpleNamedEntity implements StepDefinition {

    // array of actionDefinitions that start transition from this step
    private ActionDefinition[] actionDefinitions;

    public ActionDefinition[] getActionDefinitions() {
        return actionDefinitions;
    }

    public void setActionDefinitions(ActionDefinition[] actionDefinitions) {
        this.actionDefinitions = actionDefinitions;
    }

    @Override
    public String toString() {
        return getName();
    }
}
