package javax0.workflow;

import java.util.Collection;


public interface Result<K, V, R, T> {

    /**
     * Get the steps that this result will bring the workflow into.
     * Note that a single result can "split the state" of the workflow
     * and may bring the workflow into multiple steps simultaneous. (After all that is
     * the reason why steps are called step and not state.)
     *
     * @return the collections of the steps
     */
    Collection<Step<K, V, R, T>> getSteps();
}
