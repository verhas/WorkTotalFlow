package javax0.workflow;

/**
 * Actions can be enriched with parameters and also the user input in the workflow is modelled as parameters.
 *
 * @param <K>
 * @param <V>
 */
@FunctionalInterface
public interface Parameters<K,V> {
    V get(K key);
}
