package javax0.workflow.simple;

import javax0.workflow.Action;
import javax0.workflow.Result;
import javax0.workflow.Step;
import javax0.workflow.Workflow;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Maintain a map that maps results to actions and result names.
 *
 * @param <K> see {@link Workflow} for documentation
 * @param <V> see {@link Workflow} for documentation
 * @param <I> see {@link Workflow} for documentation
 * @param <R> see {@link Workflow} for documentation
 * @param <T> see {@link Workflow} for documentation
 * @param <C> see {@link Workflow} for documentation
 */
class ResultSupplier<K, V, I, R, T, C> {
    private final Map<SupplierTriuple<K, V, I, R, T, C>, Result<K, V, I, R, T, C>> stepMap = new HashMap<>();

    private SupplierTriuple<K, V, I, R, T, C> key(Step<K, V, I, R, T, C> step, ActionDef<K, V, I, R, T, C> actionDef, R name) {
        return SupplierTriuple.tuple(step, actionDef, name);
    }

    /**
     * Give a result supplier for the given action and the given result name.
     *
     * @param action the action (not action definition but the action itself) that is executed and we need
     *               the result based on the name. Note that the results are defined by name in the workflow
     *               builder but the actual result objects may be different depending on the action. The builder
     *               does not require that the results are named differently for different actions but on the
     *               object level a result belongs to a name and an action together. Two results may have the same name
     *               and still be two different results if they belong to different actions.
     * @param name   the name of the result.
     * @return returns the supplier that will get the result
     */
    Supplier<Result<K, V, I, R, T, C>> supplier(Action<K, V, I, R, T, C> action, R name) {
        return () -> stepMap.get(key(action.getStep(), ((ActionUse<K, V, I, R, T, C>) action).actionDef, name));
    }

    /**
     * Store the result assigned to the step, action definition and the result name.
     *
     * @param step      the step from which the action defined by actionDef starts from
     * @param actionDef the definition of the action
     * @param name      the name of the result
     * @param result    the result object to store in the map.
     */
    void put(Step<K, V, I, R, T, C> step, ActionDef<K, V, I, R, T, C> actionDef, R name, Result<K, V, I, R, T, C> result) {
        SupplierTriuple<K, V, I, R, T, C> complexKey = key(step, actionDef, name);
        if (stepMap.containsKey(complexKey)) {
            throw new IllegalArgumentException(
                    String.format("Mapping (s: '%s', a:'%s',r:'%s') -> supplier already exists.", step, actionDef, name));
        }
        stepMap.put(complexKey, result);
    }
}
