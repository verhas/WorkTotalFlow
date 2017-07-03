package javax0.workflow.simple;

class Results<K, V, R, T, C> extends BldMap<R, ResultImpl<K, V, R, T, C>> {
    @Override
    ResultImpl<K, V, R, T, C> factory() {
        return new ResultImpl<>();
    }
}
