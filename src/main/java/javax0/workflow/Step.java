package javax0.workflow;

import java.util.Collection;

/**
 * @author Peter Verhas
 */
public interface Step<K, V, R, T> extends Named<R> {

    /**
     * Get the workflow instance that this step belongs to.
     *
     * @return the workflow
     */
    Workflow<K, V, R, T> getWorkflow();

    /**
     * Get the actions that can be performed from this step. The returned collection
     * contains only the actions for which the condition method.
     * <p>
     * {@code canBePerformed} returns true.
     *
     * @return the collection of executable actions
     */
    Collection<Action<K, V, R, T>> getActions();

}
