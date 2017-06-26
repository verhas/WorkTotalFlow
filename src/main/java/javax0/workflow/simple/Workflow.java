package javax0.workflow.simple;


import javax0.workflow.Action;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * @author Peter Verhas
 */
public class Workflow<K, V, R, T> implements javax0.workflow.Workflow<K, V, R, T> {
    private final BiFunction<javax0.workflow.Action<K, V, R, T>, R, Supplier<javax0.workflow.Result<K, V, R, T>>> resultFactory;
    private Collection<javax0.workflow.Step<K, V, R, T>> steps = null;

    public Workflow(BiFunction<javax0.workflow.Action<K, V, R, T>, R, Supplier<javax0.workflow.Result<K, V, R, T>>> resultFactory) {
        this.resultFactory = resultFactory;
    }

    @Override
    public Supplier<javax0.workflow.Result<K, V, R, T>> result(Action<K, V, R, T> action, R key) {
        return null;
    }

    public Collection<javax0.workflow.Step<K, V, R, T>> getSteps() {
        return steps;
    }

    public void setSteps(Collection<javax0.workflow.Step<K, V, R, T>> steps) {
        this.steps = steps;
    }

}
