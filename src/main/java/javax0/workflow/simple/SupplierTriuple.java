package javax0.workflow.simple;

import javax0.workflow.Step;
import javax0.workflow.Workflow;

/**
 * A SupplierTriuple contains a step, an action definition and the name of a result. Such a triuple is used as a key
 * in the map that contains results as values.
 *
 * @param <K> see {@link Workflow} for documentation
 * @param <V> see {@link Workflow} for documentation
 * @param <R> see {@link Workflow} for documentation
 * @param <T> see {@link Workflow} for documentation
 * @param <C> see {@link Workflow} for documentation
 */
class SupplierTriuple<K, V, R, T, C> extends Triuple<Step<K, V, R, T, C>, ActionDef<K, V, R, T, C>, R> {

    private SupplierTriuple(Step<K, V, R, T, C> step, ActionDef<K, V, R, T, C> action, R name) {
        super(step, action, name);
    }

    static <K, V, R, T, C> SupplierTriuple<K, V, R, T, C> tuple(Step<K, V, R, T, C> step, ActionDef<K, V, R, T, C> action, R name) {
        return new SupplierTriuple<>(step, action, name);
    }
}
