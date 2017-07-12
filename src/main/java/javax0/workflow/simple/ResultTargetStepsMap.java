package javax0.workflow.simple;

import javax0.workflow.Workflow;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The ResultTargetStepsMap maps the target steps the workflow transitions to for each (step, action, result) triplet.
 * In other words the {@code ResultMap} can tell us what steps the workflow is tranistioning if the action
 * '{@code action}' was executed from '{@code step}' and the result of the action was '{@code result}'.
 *
 * @param <K> see {@link Workflow} for documentation
 * @param <V> see {@link Workflow} for documentation
 * @param <I> see {@link Workflow} for documentation
 * @param <R> see {@link Workflow} for documentation
 * @param <T> see {@link Workflow} for documentation
 * @param <C> see {@link Workflow} for documentation
 */
class ResultTargetStepsMap<K, V, I, R, T, C> {
    private final Map<TargetStepTriuple<K, V, I, R, T, C>, Collection<R>> resultMap = new HashMap<>();
    private final ActionDefFactory<K, V, I, R, T, C> actions;
    private final ResultFactory<K, V, I, R, T, C> results;
    private final StepFactory<K, V, I, R, T, C> steps;

    ResultTargetStepsMap(ActionDefFactory<K, V, I, R, T, C> actions, ResultFactory<K, V, I, R, T, C> results, StepFactory<K, V, I, R, T, C> steps) {
        this.actions = actions;
        this.results = results;
        this.steps = steps;
    }

    private TargetStepTriuple<K, V, I, R, T, C> key(R step, R action, R result) {
        StepImpl<K, V, I, R, T, C> stepImpl = steps.get(step);
        ActionDef<K, V, I, R, T, C> actionDef = actions.get(action);
        ResultImpl<K, V, I, R, T, C> resultImpl = results.get(result);
        return TargetStepTriuple.tuple(stepImpl, actionDef, resultImpl);
    }


    Set<TargetStepTriuple<K, V, I, R, T, C>> keySet() {
        return resultMap.keySet();
    }

    boolean contains(R step, R action, R result) {
        return resultMap.containsKey(key(step, action, result));
    }

    Collection<R> get(R step, R action, R result) {
        return resultMap.get(key(step, action, result));
    }

    @SafeVarargs
    final void put(R step, R action, R result, R... steps) {
        TargetStepTriuple<K, V, I, R, T, C> complexKey = key(step, action, result);
        if (resultMap.containsKey(complexKey)) {
            throw new IllegalArgumentException(
                    String.format("Mapping (a:'%s',r:'%s') -> s:['%s'] already exists.", action, result,
                            String.join(",", Arrays.stream(steps).map(Objects::toString).collect(Collectors.toList()))));
        }
        resultMap.put(complexKey, Arrays.asList(steps));
    }
}
