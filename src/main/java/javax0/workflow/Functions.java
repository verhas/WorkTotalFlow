package javax0.workflow;

import java.util.function.Function;
import java.util.function.Predicate;

public interface Functions {
    interface PostFunction<K, V, R, T> {

        /**
         * Execute the post function.
         *
         * @param action          the action currently executed
         * @param transientObject the transient object as returned
         *                        by the pre function
         * @param userInput       the user input
         * @return the result of the action
         */
        Result<K, V, R, T> apply(Action<K, V, R, T> action, T transientObject,
                     Parameters<K,V> userInput);
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
     */
    interface PreFunction<K, V, R, T> extends Function<Action<K, V, R, T>, T> {

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
     * @author Peter Verhas
     */
    interface Validator<K, V, R, T> {

        /**
         * Decides whether the user input is valid for the action.
         *
         * @param action          the action actually performed
         * @param transientObject the transient object returned by the pre-action
         * @param userInput       the user input provided by the user
         * @return true if the user input is acceptable and false if it is erroneous
         */
        public boolean test(Action<K, V, R, T> action, T transientObject,
                            Parameters<K, V> userInput);
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
     */
    interface Condition<K, V, R, T> extends Predicate<Action<K, V, R, T>> {

    }
}
