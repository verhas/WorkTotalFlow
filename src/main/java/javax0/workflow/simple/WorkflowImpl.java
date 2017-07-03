package javax0.workflow.simple;


import javax0.workflow.Action;
import javax0.workflow.Result;
import javax0.workflow.Step;
import javax0.workflow.Workflow;

import java.util.Collection;
import java.util.Collections;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * @author Peter Verhas
 */
public class WorkflowImpl<K, V, R, T, C> implements Workflow<K, V, R, T, C> {
    private final BiFunction<Action<K, V, R, T, C>, R, Supplier<Result<K, V, R, T, C>>> resultFactory;
    private Collection<Step<K, V, R, T, C>> steps = Collections.emptySet();
    private C context;

    public WorkflowImpl(BiFunction<Action<K, V, R, T, C>, R, Supplier<Result<K, V, R, T, C>>> resultFactory) {
        this.resultFactory = resultFactory;
    }

    @Override
    public Supplier<Result<K, V, R, T, C>> result(Action<K, V, R, T, C> action, R key) {
        return resultFactory.apply(action, key);
    }

    @Override
    public Collection<Step<K, V, R, T, C>> getSteps() {
        return steps;
    }

    @Override
    public void setSteps(Collection<Step<K, V, R, T, C>> steps) {
        this.steps = steps;
    }

    @Override
    public C getContext() {
        return context;
    }

    @Override
    public void setContext(C context) {
        this.context = context;
    }

}
