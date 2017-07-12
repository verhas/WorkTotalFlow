package javax0.workflow.simple;


import javax0.workflow.Action;
import javax0.workflow.Parameters;
import javax0.workflow.Step;
import javax0.workflow.Workflow;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Simple implementation of the interface {@link Step}
 *
 * @param <K> see {@link Workflow} for documentation
 * @param <V> see {@link Workflow} for documentation
 * @param <I> see {@link Workflow} for documentation
 * @param <R> see {@link Workflow} for documentation
 * @param <T> see {@link Workflow} for documentation
 * @param <C> see {@link Workflow} for documentation
 */
public class StepImpl<K, V, I, R, T, C> extends Named<R> implements Step<K, V, I, R, T, C> {

    final Collection<Action<K, V, I, R, T, C>> actions;
    private final Workflow<K, V, I, R, T, C> workflow;
    Parameters<K, V> parameters =  k -> null ;

    StepImpl(Workflow<K, V, I, R, T, C> workflow) {
        this.workflow = workflow;
        this.actions = new HashSet<>();
    }

    @Override
    public Workflow<K, V, I, R, T, C> getWorkflow() {
        return workflow;
    }

    @Override
    public Stream<Action<K, V, I, R, T, C>> getActions(Predicate<Action<K, V, I, R, T, C>> p) {
        return actions.stream().filter(p).filter(Action::available);
    }

    @Override
    public String toString() {
        return "Step[" + getName() + "]";
    }

    @Override
    public Parameters<K, V> getParameters() {
        return parameters;
    }
}
