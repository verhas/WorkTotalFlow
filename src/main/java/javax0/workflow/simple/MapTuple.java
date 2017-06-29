package javax0.workflow.simple;

import java.util.Objects;

class MapTuple<K, V, R, T> {

    final StepImpl<K, V, R, T> step;
    final ActionDef<K, V, R, T> action;
    final ResultImpl<K, V, R, T> result;

    private MapTuple(StepImpl<K, V, R, T> step, ActionDef<K, V, R, T> action, ResultImpl<K, V, R, T> result) {
        this.step = step;
        this.action = action;
        this.result = result;
    }

    static <K, V, R, T> MapTuple<K, V, R, T> tuple(StepImpl<K, V, R, T> step, ActionDef<K, V, R, T> action, ResultImpl<K, V, R, T> result) {
        return new MapTuple<>(step, action, result);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapTuple<?, ?, ?, ?> tuple = (MapTuple<?, ?, ?, ?>) o;
        return Objects.equals(step, tuple.step) &&
                Objects.equals(action, tuple.action) &&
                Objects.equals(result, tuple.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(step, action, result);
    }
}
