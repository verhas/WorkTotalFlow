package com.verhas.workflow;

/**
 * This interface has to be implemented by conditions.
 *
 * @author Peter Verhas
 */
public interface Condition extends Function {

    /**
     * Decides if the action can be executed. This method is called by the
     * workflow execution whenever the program needs to know if the action
     * can be executed or not.
     * <p>
     * The actual method should be fast and simple and should not alter any
     * persistence or even non-persistence state of the enviroment. The workflow
     * execution may call this method several times beafore calling the
     * prefunction of the action.
     * <p>
     * The typical use of the method is to decide if the action is to be
     * displayed on the user screen so that the user can select the action for
     * execution. This can happen any number of times before the user selects
     * the action to be executed. The workflow environment may call this method
     * directly before the pre function to ensure that the action can really be
     * executed even after the user selected the action. (This is because some
     * user interfaces may be ricked to execute and action that may not be
     * allowed by the condition or because the conditions may have changed
     * in the time between the action was listed for execution and the
     * action was selected by the user.)
     *
     * @param workflow the work flow the action is to be performed on
     * @param step the step from which the action starts
     * @param action the action that is to be executed
     * @return true if the action can be performed.
     */
    public boolean canBePerformed(Action action);
}
