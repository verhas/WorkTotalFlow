package javax0.workflow;

import java.util.Collection;

/**
 * A result that transitions the workflow from one of the current steps into one or more steps.
 *
 * @param <K> see {@link Workflow} for documentation
 * @param <V> see {@link Workflow} for documentation
 * @param <I> see {@link Workflow} for documentation
 * @param <R> see {@link Workflow} for documentation
 * @param <T> see {@link Workflow} for documentation
 * @param <C> see {@link Workflow} for documentation
 */
public interface Result<K, V, I, R, T, C> {

    /**
     * Get the steps that this result will bring the workflow into.
     * Note that a single result can "split the state" of the workflow
     * and may bring the workflow into multiple steps simultaneously.
     *
     * @return the collections of the steps
     */
    Collection<Step<K, V, I, R, T, C>> getSteps();
}
