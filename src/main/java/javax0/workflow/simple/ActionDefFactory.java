package javax0.workflow.simple;

class ActionDefFactory<K, V, R, T, C> extends NamedFactory<R,ActionDef<K, V, R, T, C>> {
    @Override
    ActionDef<K, V, R, T, C> factory() {
        return new ActionDef<>();
    }
}
