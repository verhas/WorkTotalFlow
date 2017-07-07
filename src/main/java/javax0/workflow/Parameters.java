package javax0.workflow;

/**
 * Actions can be enriched with parameters and also the user input in the workflow is modelled as parameters.
 *
 * @param <K> see {@link Workflow} for documentation
 * @param <V> see {@link Workflow} for documentation
 */
@FunctionalInterface
public interface Parameters<K,V> {
    V get(K key);
}
