package javax0.wtf;

/**
 * This interface has to be implemented by classes that serve as workflow
 * defintions. A workflow definition as the name implies defines a workflow
 * and with other objects can be used by workflow instances to get the rules
 * to follow when the workflow is executed.
 *
 * @author Peter Verhas
 */
public interface WorkflowDefinition extends Definition {

    /**
     * Get the step definitions the workflow has.
     */
    public StepDefinition[] getStepDefinitions();

    /**
     *  Get the start step in which a workflow is in when it is started.
     */
    public StepDefinition getStartStep();

}
