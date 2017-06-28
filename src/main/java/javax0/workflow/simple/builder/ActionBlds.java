package javax0.workflow.simple.builder;

public class ActionBlds<K, V, R, T> extends BldMap<R,ActionBld<K, V, R, T>> {
    @Override
    ActionBld<K, V, R, T> factory() {
        return new ActionBld();
    }
}
