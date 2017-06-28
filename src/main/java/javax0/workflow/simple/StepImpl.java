package javax0.workflow.simple;


import javax0.workflow.Action;
import javax0.workflow.Workflow;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Peter Verhas
 */
public class StepImpl<K, V, R, T> implements javax0.workflow.Step<K, V, R, T> {

    private final Workflow<K, V, R, T> workflow;
    private final Collection<? extends Action<K, V, R, T>> actions;

    public StepImpl(Workflow<K, V, R, T> workflow, Collection<? extends Action<K, V, R, T>> actions) {
        this.workflow = workflow;
        this.actions = actions;
    }

    public javax0.workflow.Workflow<K, V, R, T> getWorkflow() {
        return workflow;
    }

    @Override
    public Collection<Action<K, V, R, T>> getActions() {
        return actions.stream().filter(Action::available).collect(Collectors.toSet());
    }
}
