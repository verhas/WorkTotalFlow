package javax0.workflow.simple;

class Results<K, V, R, T> extends BldMap<R, ResultImpl<K, V, R, T>> {
    @Override
    ResultImpl<K, V, R, T> factory() {
        return new ResultImpl<>();
    }
}
