package javax0.workflow;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * This interface defines the methods that are available to manage the workflow
 * of an item.
 *
 * @author Peter Verhas
 */
public interface Workflow<K, V, R, T> {

    Supplier<Result<K, V, R, T>> result(Action<K, V, R, T> action, R key);
    /**
     * Get the steps that the work flow is currently in.
     */
    Collection<Step<K, V, R, T>> getSteps();

    /**
     * Set the steps that the work flow is currently in.
     */
    void setSteps(Collection<Step<K, V, R, T>> steps);

}
