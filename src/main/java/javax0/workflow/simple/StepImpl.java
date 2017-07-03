package javax0.workflow.simple;


import javax0.workflow.Action;
import javax0.workflow.Step;
import javax0.workflow.Workflow;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author Peter Verhas
 */
public class StepImpl<K, V, R, T, C> extends Named<R> implements Step<K, V, R, T, C> {

    final Collection<Action<K, V, R, T, C>> actions;
    private final Workflow<K, V, R, T, C> workflow;

    public StepImpl(Workflow<K, V, R, T, C> workflow) {
        this.workflow = workflow;
        this.actions = new HashSet<>();
    }

    public Workflow<K, V, R, T, C> getWorkflow() {
        return workflow;
    }

    @Override
    public Stream<Action<K, V, R, T, C>> getActions(Predicate<Action<K, V, R, T, C>> p) {
        return actions.stream().filter(p).filter(Action::available);
    }

    @Override
    public String toString() {
        return "Step[" + getName() + "]";
    }

}
