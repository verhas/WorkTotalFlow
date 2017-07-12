package javax0.workflow;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * There are four functional interfaces that are used in an action in the workflow.
 * <ol>
 * <li>
 * Before an action can be executed the {@link Condition} predicate is evaluated. If the method
 * {@link Condition#test(Object)} returns false the action can not be further executed at that
 * certain point.
 * </li>
 * <li>
 * If an action can be executed then the {@link Pre} function is evaluated. This function is used to execute code
 * that is needed to present the user the input he/she has to provide. This functionality could also be
 * implemented in the condition but this way the model is cleaner and it also inherently provides faster performance.
 * Conditions should focus on the executability of the action and nothing else. The pre function returns a
 * transient object. The workflow treats this object transparent. The workflow does not do anything with this
 * object, it only passes it on to the post function.
 * </li>
 * <li>
 * The validator {@link Validator#test(Action, Object, Object)} is executed before the post function. If there is
 * any invalidity in the parameters, the current action and the transient object the pre function returned then the
 * validator has to return false and in that case the action will not be performed.
 * </li>
 * <li>
 * The post functoin {@link Post#apply(Action, Object, Object)} is executed to make the transition from the
 * step that the action was starting from to the step or steps the post function result suggests. At the same time
 * if there is any database, back-end or any other action that has to be performed then the post function is the place
 * for that. If any of these back-end functionalities can not be performed then the post function should return a
 * result that will signal that. In the simplest case the result will replace the step from which the action was
 * started by itself. It is not recommended to perform the transactions in the validator and signal failure as a
 * validation error because workflow implementation may call validators multiple time. Post functions are invoked
 * only once for each action execution.
 * </li>
 * </ol>
 */
public interface Functions {
    /**
     * Functional interface mapping action, transient object and user input parameters to result.
     *
     * @param <K> see {@link Workflow} for documentation
     * @param <V> see {@link Workflow} for documentation
     * @param <I> see {@link Workflow} for documentation
     * @param <R> see {@link Workflow} for documentation
     * @param <T> see {@link Workflow} for documentation
     * @param <C> see {@link Workflow} for documentation
     */
    @FunctionalInterface
    interface Post<K, V, I, R, T, C> {

        /**
         * Execute the post function.
         *
         * @param action          the action currently executed
         * @param transientObject the transient object as returned
         *                        by the pre function
         * @param userInput       the user input
         * @return the result of the action
         */
        Result<K, V, I, R, T, C> apply(Action<K, V, I, R, T, C> action, T transientObject,
                                       I userInput);
    }

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
     * @param <K> see {@link Workflow} for documentation
     * @param <V> see {@link Workflow} for documentation
     * @param <I> see {@link Workflow} for documentation
     * @param <R> see {@link Workflow} for documentation
     * @param <T> see {@link Workflow} for documentation
     * @param <C> see {@link Workflow} for documentation
     */
    @FunctionalInterface
    interface Pre<K, V, I, R, T, C> extends Function<Action<K, V, I, R, T, C>, T> {

    }

    /**
     * Validators are used to validate the user input before the post function of the action
     * is invoked.
     * <p>
     * The implementation of the validator should be fast and should not alter any state.
     * <p>
     * There is no guarantee that a validator is called only once performing an action.
     * An application is allowed to forget the result of the validator and call it several times.
     *
     * @param <K> see {@link Workflow} for documentation
     * @param <V> see {@link Workflow} for documentation
     * @param <I> see {@link Workflow} for documentation
     * @param <R> see {@link Workflow} for documentation
     * @param <T> see {@link Workflow} for documentation
     * @param <C> see {@link Workflow} for documentation
     */
    @FunctionalInterface
    interface Validator<K, V, I, R, T, C> {

        /**
         * Decides whether the user input is valid for the action.
         *
         * @param action          the action actually performed
         * @param transientObject the transient object returned by the pre-action
         * @param userInput       the user input provided by the user
         * @return true if the user input is acceptable and false if it is erroneous
         */
        @SuppressWarnings("BooleanMethodIsAlwaysInverted")
        boolean test(Action<K, V, I, R, T, C> action, T transientObject,
                     I userInput);
    }

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
     * @param <K> see {@link Workflow} for documentation
     * @param <V> see {@link Workflow} for documentation
     * @param <I> see {@link Workflow} for documentation
     * @param <R> see {@link Workflow} for documentation
     * @param <T> see {@link Workflow} for documentation
     * @param <C> see {@link Workflow} for documentation
     */
    @FunctionalInterface
    interface Condition<K, V, I, R, T, C> extends Predicate<Action<K, V, I, R, T, C>> {

    }
}
