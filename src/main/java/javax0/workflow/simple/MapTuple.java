package javax0.workflow.simple;

class MapTuple<K, V, R, T, C> extends Triuple<StepImpl<K, V, R, T, C>, ActionDef<K, V, R, T, C>, ResultImpl<K, V, R, T, C>> {
    final StepImpl<K, V, R, T, C> step;
    final ActionDef<K, V, R, T, C> action;
    final ResultImpl<K, V, R, T, C> result;

    private MapTuple(StepImpl<K, V, R, T, C> step, ActionDef<K, V, R, T, C> action, ResultImpl<K, V, R, T, C> result) {
        super(step, action, result);
        this.step = a;
        this.action = b;
        this.result = c;
    }

    static <K, V, R, T, C> MapTuple<K, V, R, T, C> tuple(StepImpl<K, V, R, T, C> step, ActionDef<K, V, R, T, C> action, ResultImpl<K, V, R, T, C> result) {
        return new MapTuple<>(step, action, result);
    }
}
