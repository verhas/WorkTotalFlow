package com.verhas.workflow;

/**
 *
 * Classes implementing pre-functions should implement this interface.
 *
 * @author Peter Verhas
 */
public interface PreFunction extends Function {

    /**
     * Execute the pre-function and return the {@code transientObject}. The
     * {@code transientObject} is saved by the workflow execution environment
     * and is passed to the post function as it was returned by this method.
     * <p>
     * Although this is a transient object that exists only during the execution
     * of a workflow action it is supposed to implement the interface
     * {@code Serializable} if the environment is a servlet container. This is
     * because the pre function may be used to display a user screen to enter
     * input and the post function may only be executed after the user screen
     * was submitted. They two invocations of the methods in this case belong
     * to different http hits. The {@code transientObject} may be stored in the
     * servlet container session and if the environment is clustered the object
     * may not travel from one node to the other.
     *
     * @param workflow the work flow currently executed
     * @param step the step the action starts from
     * @param action the action currently performed
     * @return the transient object used later by the validator and the
     * post-function
     */
    public Object preFunction(Action action);
}
