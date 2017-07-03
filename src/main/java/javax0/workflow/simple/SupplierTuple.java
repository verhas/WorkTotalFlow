package javax0.workflow.simple;

import javax0.workflow.Step;

import java.util.Objects;

class SupplierTuple<K, V, R, T, C> {

    private final Step<K, V, R, T, C> step;
    private final ActionDef<K, V, R, T, C> action;
    private final R name;

    private SupplierTuple(Step<K, V, R, T, C> step, ActionDef<K, V, R, T, C> action, R name) {
        this.step = step;
        this.action = action;
        this.name = name;
    }


    static <K, V, R, T, C> SupplierTuple<K, V, R, T, C> tuple(Step<K, V, R, T, C> step, ActionDef<K, V, R, T, C> action, R name) {
        return new SupplierTuple<>(step, action, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupplierTuple<?, ?, ?, ?, ?> that = (SupplierTuple<?, ?, ?, ?, ?>) o;
        return Objects.equals(step, that.step) &&
                Objects.equals(action, that.action) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(step, action, name);
    }
}
