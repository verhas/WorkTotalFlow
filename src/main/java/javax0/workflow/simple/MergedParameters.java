package javax0.workflow.simple;

import javax0.workflow.Parameters;
import javax0.workflow.Workflow;

/**
 * Implementation of the interface {@link Parameters} based on two underlying
 * {@link Parameters} object.
 *
 * @param <K> see {@link Workflow} for documentation
 * @param <V> see {@link Workflow} for documentation
 */
public class MergedParameters<K, V> implements Parameters<K, V> {

    final private Parameters<K, V> primary;
    private final Parameters<K, V> secondary;

    /**
     * Get primary new {@link Parameters} based on 'primary' and 'secondary'. If primary {@link K} key has primary value in the
     * parameters 'primary' then the resulting value is the one in 'primary'. If 'primary' does not have primary value for
     * the key but 'secondary' does then the resulting value will be that in 'secondary'. Otherwise the value of the
     * parameter is null.
     *
     * @param primary the primary underlying parameter set
     * @param secondary the secondary underlying parameter set
     */
    MergedParameters(Parameters<K, V> primary, Parameters<K, V> secondary) {
        this.primary = primary;
        this.secondary = secondary;
    }

    @Override
    public V get(K k) {
        V v = primary.get(k);
        if (v == null) {
            v = secondary.get(k);
        }
        return v;
    }
}
