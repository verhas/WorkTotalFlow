package javax0.wtf;

import java.util.Map;

/**
 * Classes implementing a validator has to imlement this interface. Validators
 * are used to validate the user input before the post function of the action
 * is invoked.
 * <p>
 * The implementation of the validator should be fast and should not alter any
 * state.
 * <p>
 * There is no guarantee that a validator is called only once
 * performing an action. An application may forget the result of the validator
 * and call it several times.
 *
 * @author Peter Verhas
 */
public interface Validator extends Function {

    /**
     * Decides whether the user input is valid for the action.
     *
     * @param workflow the work flow that is executed
     * @param step the step from which the action is started
     * @param action the action actually performed
     * @param transientObject the transient object returned by the pre-action
     * @param userInput the user input provided by the user
     * @return true if the user input is acceptable and false if it is erroneous
     */
    public boolean valid(Action action, Object transientObject,
                         Map<String, String> userInput);
}
