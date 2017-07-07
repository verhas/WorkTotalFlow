package javax0.workflow.simple;

import javax0.workflow.Workflow;

/**
 * Factory to create {@link ActionDef} objects.
 *
 * @param <K> see {@link Workflow} for documentation
 * @param <V> see {@link Workflow} for documentation
 * @param <R> see {@link Workflow} for documentation
 * @param <T> see {@link Workflow} for documentation
 * @param <C> see {@link Workflow} for documentation
 */
class ActionDefFactory<K, V, R, T, C> extends NamedFactory<R, ActionDef<K, V, R, T, C>> {
    @Override
    ActionDef<K, V, R, T, C> factory() {
        return new ActionDef<>();
    }
}
