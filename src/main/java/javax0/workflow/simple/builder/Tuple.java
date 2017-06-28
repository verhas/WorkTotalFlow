package javax0.workflow.simple.builder;

import java.util.Objects;

class Tuple<K, V, R, T> {

    final ActionBld <K, V, R, T> action;
    final ResultBld<R> result;

    private Tuple(ActionBld<K, V, R, T> action, ResultBld<R> result) {
        this.action = action;
        this.result = result;
    }

    static <K, V, R, T> Tuple<K, V, R, T> tuple(ActionBld<K, V, R, T> action, ResultBld<R> result){
        return new Tuple<>(action,result);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<K, V, R, T> tuple = (Tuple<K, V, R, T>) o;
        return Objects.equals(action, tuple.action) &&
                Objects.equals(result, tuple.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, result);
    }
}
