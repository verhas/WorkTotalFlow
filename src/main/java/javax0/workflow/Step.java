package javax0.workflow;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * One step of the workflow.
 *
 * @param <K> see {@link Workflow} for documentation
 * @param <V> see {@link Workflow} for documentation
 * @param <R> see {@link Workflow} for documentation
 * @param <T> see {@link Workflow} for documentation
 * @param <C> see {@link Workflow} for documentation
 */
public interface Step<K, V, R, T, C> extends Named<R>, HasParameters<K,V> {

    /**
     * Get the workflow instance that this step belongs to.
     *
     * @return the workflow
     */
    Workflow<K, V, R, T, C> getWorkflow();

    /**
     * Get the actions that can be performed from this step. The returned stream
     * contains only the actions for which the condition is true
     *
     * @return the collection of executable actions
     */
    default Stream<Action<K, V, R, T, C>> getActions() {
        return getActions(a -> true);
    }

    /**
     * Get the actions that can be performed from this step. The returned stream
     * contains only the actions for which the condition is true and the predicate is also true.
     * <p>
     * NOTE: The default implementation of {@link #getActions()} calls this method with the predicate
     * {@code a -> true}. The actual implementation of this method better apply the predicate first
     * before checking the condition. This version of the method is defined in the interface to
     * provide optimization calling the conditions less times. It is quite feasible that some implementation
     * at certain point of time in the execution is not interested in all actions that can be executed
     * but only the one that the user selected. In that case the call
     * {@code getActions( a -> a.getName().equals(selectedActionName))} can be more effective than getting all
     * actions, calling all conditions and then looking at the final stream to see that the action is in there.
     * </p>
     *
     * @param p predicate to filter the actions.
     * @return the filtered stream of steps.
     */
    Stream<Action<K, V, R, T, C>> getActions(Predicate<Action<K, V, R, T, C>> p);
}
