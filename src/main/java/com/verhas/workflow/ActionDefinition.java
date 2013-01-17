package com.verhas.workflow;

/**
 * This interface represents an action. The getter methods each return either
 * an object that implement the interface (see the return value of the getters)
 * or may return {@code null} if there is no such object related to the action.
 * <p>
 * Note that several steps may use the same action to transfer the workflow
 * from one step to others and many actions may share conditions, validators
 * and pre/post- functions, therefore the classes implementing the interface
 * {@code Function} have to be thread safe.
 * <p>
 * Also note that these interfaces extending {@code Function} use different
 * method names to perform their task and thus a class may implement more than
 * one of these interfaces.
 *
 * @author Peter Verhas
 */
public interface ActionDefinition extends Definition {

    /**
     * @return the condition object of the action.
     */
    public ConditionDefinition getConditionDefinition();

    /**
     * @return the pre function object of the action.
     */
    public PreFunctionDefinition getPreFunctionDefinition();

    /**
     *
     * @return the validator object of the action.
     */
    public ValidatorDefinition getValidatorDefinition();

    /**
     *
     * @return the post function object of the action
     */
    public PostFunctionDefinition getPostFunctionDefinition();

    /**
     * Get the result definitions that the action working acording to this
     * action definition may execute.
     *
     * @return the array of result definitions
     */
    public ResultDefinition[] getResultDefinitions();

    /**
     * Get the step definition that this action definition belongs to.
     *
     * @return the step definition object
     */
    public StepDefinition getStepDefinition();
}
