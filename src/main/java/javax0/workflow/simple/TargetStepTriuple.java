package javax0.workflow.simple;

import javax0.workflow.Workflow;

/**
 * {@link TargetStepTriuple} holds a {@link StepImpl}, an {@link ActionDef} and a {@link ResultImpl} to be used in a
 * map as a key to lead to a collection of target steps.
 *
 * @param <K> see {@link Workflow} for documentation
 * @param <V> see {@link Workflow} for documentation
 * @param <R> see {@link Workflow} for documentation
 * @param <T> see {@link Workflow} for documentation
 * @param <C> see {@link Workflow} for documentation
 */
class TargetStepTriuple<K, V, R, T, C> extends Triuple<StepImpl<K, V, R, T, C>, ActionDef<K, V, R, T, C>, ResultImpl<K, V, R, T, C>> {
    final StepImpl<K, V, R, T, C> step;
    final ActionDef<K, V, R, T, C> action;
    final ResultImpl<K, V, R, T, C> result;

    private TargetStepTriuple(StepImpl<K, V, R, T, C> step, ActionDef<K, V, R, T, C> action, ResultImpl<K, V, R, T, C> result) {
        super(step, action, result);
        this.step = a;
        this.action = b;
        this.result = c;
    }

    static <K, V, R, T, C> TargetStepTriuple<K, V, R, T, C> tuple(StepImpl<K, V, R, T, C> step, ActionDef<K, V, R, T, C> action, ResultImpl<K, V, R, T, C> result) {
        return new TargetStepTriuple<>(step, action, result);
    }
}
