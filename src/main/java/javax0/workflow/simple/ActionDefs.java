package javax0.workflow.simple;

class ActionDefs<K, V, R, T, C> extends BldMap<R,ActionDef<K, V, R, T, C>> {
    @Override
    ActionDef<K, V, R, T, C> factory() {
        return new ActionDef<>();
    }
}
