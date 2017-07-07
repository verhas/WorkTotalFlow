package javax0.workflow;

import javax0.workflow.exceptions.ValidatorFailed;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Supplier;

/**
 *
 * @param <K> see {@link Workflow} for documentation
 * @param <V> see {@link Workflow} for documentation
 * @param <R> see {@link Workflow} for documentation
 * @param <T> see {@link Workflow} for documentation
 * @param <C> see {@link Workflow} for documentation
 */
public interface Action<K, V, R, T, C> extends Named<R> {

    /**
     * When an action is automatic it is executed immediately without waiting from external trigger. Note that this
     * feature is experimental.
     *
     * @return true if the action is automatic
     */
    boolean isAuto();

    /**
     * Get the step that this action is executed from. Every action starts from
     * a step in the workflow and moves the workflow into one step or into multiple workflow.
     * This method returns the step from which the action can be executed from.
     *
     * @return the step this action can be executed from
     */
    Step<K, V, R, T, C> getStep();

    /**
     * @return true if the action can be processed. This usually true when the condition
     * associated to the action evaluates to true. Some implementation may put exctra conditions on some
     * actions in addition to the condition result.
     */
    boolean available();


    default Supplier<? extends Result<K, V, R, T, C>> result(R key) {
        return getStep().getWorkflow().result(this, key);
    }

    /**
     * Execute the pre-function, validator and post-function for the specific
     * action and moves the workflow to the target step or target workflow
     * It is multipled workflow in case
     * the action with the result returned by the post function is a split
     * action.
     * <p>
     * This method is called when an action can be performed automatically
     * without intermediate user input.
     * <p>
     * The method also alters the workflow of the work flow object, so the
     * next call to {@code getSteps()} should return the new workflow.
     *
     * @return the step or workflow that the workflow is after performing the
     * action.
     * @throws ValidatorFailed if there is a validation error
     */
    default void perform() throws ValidatorFailed {
        T transientObject = performPre();
        performPost(transientObject, null);
    }

    /**
     * Execute the pre-function of the action.
     * <p>
     * This method is called when the action should be performed in two phases.
     * The first phase is the pre-function and gathering user input. The second
     * phase is to execute the validator and the post-function.
     * <p>
     * When the action is performed in two phase the pre-function returns a
     * {@code transientState} object that the post function and the validator
     * may use to perform their methods.
     *
     * @return the transient state object the pre-function creates
     */
    T performPre();

    /**
     * Execute the post-function of the action.
     * <p>
     * This method is called when the action should be performed in two phases
     * as described in the documentation of the method {@link #performPre()}
     * <p>
     * The method should also alter the workflow of the work flow object, so the
     * next call to {@code getSteps()} should return the workflow that the work
     * flow is after the execution of the action.
     *
     * @param transientObject the transient state object returned by the pre-function
     * @param userInput       the user-input that was gathered after calling pre-function
     * @throws ValidatorFailed if the validation has failed
     */
   void performPost(T transientObject, Parameters<K,V> userInput)
            throws ValidatorFailed;

    /**
     * Join the workflow listed in the argument list into this action.
     * <p>
     * This method can be used to implement a join programmatically. The post
     * function may call this method to list all the workflow that have to be
     * joined to the actual flow. Joining means that the workflow will not be in
     * the workflow listed in the argument any more, but only in the workflow in which the
     * actual action performing leads to.
     * <p>
     * More precisely the the method {@code join(Collection<StepImpl> workflow)} will remove the
     * listed workflow from the set of workflow that the workflow is in currently.
     * Other workflow that are not removed will still belong to the set. The new
     * set of workflow will be the ones that the currently executing action leads
     * to and the workflow that were not joined.
     * <p>
     * The actual implementation should be sloppy in the sense that the caller
     * may specify workflow that the workflow is not currently in.
     * <p>
     * This is a complimentary method that can be implemented using only the
     * interface methods, and the implementation in the package
     * {@code com.verhas.workflow.simple} is one that does not depend on the
     * actual implementation details.
     *
     * @param steps the workflow to join.
     */
    default void join(Collection<Step<K, V, R, T, C>> steps) {
        final Collection<Step<K, V, R, T, C>> stepSet = new HashSet<>();
        final Workflow<K, V, R, T, C> workflow = getStep().getWorkflow();
        stepSet.addAll(workflow.getSteps());
        stepSet.removeAll(steps);
        workflow.setSteps(stepSet);
    }

    Parameters<K, V> getParameters();
}
