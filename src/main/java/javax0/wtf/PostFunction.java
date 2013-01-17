package javax0.wtf;

import java.util.Map;

/**
 * Classes implementing post function should implement this  interface.
 * @author Peter Verhas
 */
public interface PostFunction extends Function {

    /**
     * Execute the post function.
     *
     * @param workflow the work flow currently executed
     * @param step the step the action starts from
     * @param action the action currently executed
     * @param transientObject the transient object as returned
     *        by the pre function
     * @param userInput the user input
     * @return the result of the action
     */
    public Result postFunction(Action action, Object transientObject,
                               Map<String, String> userInput);
}
