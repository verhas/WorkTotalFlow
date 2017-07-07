package javax0.workflow.simple;

class ResultFactory<K, V, R, T, C> extends NamedFactory<R, ResultImpl<K, V, R, T, C>> {
    @Override
    ResultImpl<K, V, R, T, C> factory() {
        return new ResultImpl<>();
    }
}
