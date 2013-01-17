package javax0.wtf;

/**
 * A workflow StepDefinition defines the state of a workflow (more or less). A workflow
 * during the execution is in one or more steps. A workflow implementation
 * should have a class that implements this interface.
 *
 * @author Peter Verhas
 */
public interface StepDefinition extends Definition {

    /**
     * Get all the actions that may conditionally be executed when the
     * work flow is in the actual step. Note that this method does NOT call
     * any condition. It returns all the actions that could ever be started from
     * this step. To get the actions that can be executed at the very moment
     * the workflow object has to be consulted calling the method
     * {@see Workflow#getActionDefinitions(StepDefinition step)}.
     */
    public ActionDefinition[] getActionDefinitions();
}
