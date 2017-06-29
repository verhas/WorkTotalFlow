package javax0.workflow.simple;

class ActionDefs<K, V, R, T> extends BldMap<R,ActionDef<K, V, R, T>> {
    @Override
    ActionDef<K, V, R, T> factory() {
        return new ActionDef<>();
    }
}
