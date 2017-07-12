package javax0.workflow.simple;

class ResultFactory<K, V, I, R, T, C> extends NamedFactory<R, ResultImpl<K, V, I, R, T, C>> {
    @Override
    ResultImpl<K, V, I, R, T, C> factory() {
        return new ResultImpl<>();
    }
}
