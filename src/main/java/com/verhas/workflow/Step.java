package com.verhas.workflow;

/**
 *
 * @author Peter Verhas
 */
public interface Step extends HasDefinition<StepDefinition> {

    /**
     * Get the workflow instance that this step belongs to.
     *
     * @return the workflow instance.
     */
    public Workflow getWorkflow();

    /**
     * Get the actions that can be performed from this step. The returned array
     * contains only the actions for which the condition method
     * {@code canBePerformed} returns true.
     *
     * @return the array of executable actions
     */
    public Action[] getActions() throws ClassNotFoundException,
                                        IllegalAccessException,
                                        InstantiationException;

    /**
     * Get the actions that can be performed from this step. The returned array
     * contains all the actions regardless of the result of the conditions
     * returned by the condition method
     * {@code canBePerformed}.
     * <p>
     * The implementation may call and ignore the result of the condition or
     * may simply not call the condition at all.
     *
     * @return the array of executable actions
     */
    public Action[] getAllActions();
}
