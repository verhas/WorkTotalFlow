package javax0.wtf.simple;

import javax0.wtf.ResultDefinition;
import javax0.wtf.StepDefinition;

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
