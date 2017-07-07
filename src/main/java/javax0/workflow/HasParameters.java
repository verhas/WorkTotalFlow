package javax0.workflow;

/**
 * Anything that has parameters can implement this interface to provide the parameters to interested parties.
 * @param <K> see {@link Workflow} for documentation
 * @param <V> see {@link Workflow} for documentation
 */
@FunctionalInterface
interface HasParameters<K,V> {
    /**
     * Get the parameters that were assigned to this object. This method is usually called by the implementation
     * of pre, post and other function attached to an action.
     *
     * @return the parameters of the object
     */
    Parameters<K, V> getParameters();
}
