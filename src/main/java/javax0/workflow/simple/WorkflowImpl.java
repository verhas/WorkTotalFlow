package javax0.workflow.simple;


import javax0.workflow.Action;
import javax0.workflow.Result;
import javax0.workflow.Step;
import javax0.workflow.Workflow;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * @author Peter Verhas
 */
public class WorkflowImpl<K, V, R, T> implements Workflow<K, V, R, T> {
    private final BiFunction<Action<K, V, R, T>, R, Supplier<Result<K, V, R, T>>> resultFactory;
    private Collection<? extends Step<K, V, R, T>> steps = null;

    public WorkflowImpl(BiFunction<Action<K, V, R, T>, R, Supplier<Result<K, V, R, T>>> resultFactory) {
        this.resultFactory = resultFactory;
    }

    @Override
    public Supplier<Result<K, V, R, T>> result(Action<K, V, R, T> action, R key) {
        return resultFactory.apply(action,key);
    }

    @Override
    public Collection<? extends Step<K, V, R, T>> getSteps() {
        return steps;
    }

    @Override
    public void setSteps(Collection<? extends Step<K, V, R, T>> steps) {
        this.steps = steps;
    }

}
