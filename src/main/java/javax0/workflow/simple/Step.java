package javax0.workflow.simple;


import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Peter Verhas
 */
public class Step<K, V, R, T> implements javax0.workflow.Step<K, V, R, T> {

    private final javax0.workflow.Workflow<K, V, R, T> workflow;
    private final Collection<javax0.workflow.Action<K, V, R, T>> actions;

    public Step(javax0.workflow.Workflow<K, V, R, T> workflow, Collection<javax0.workflow.Action<K, V, R, T>> actions) {
        this.workflow = workflow;
        this.actions = actions;
    }

    public javax0.workflow.Workflow<K, V, R, T> getWorkflow() {
        return workflow;
    }

    @Override
    public Collection<javax0.workflow.Action<K, V, R, T>> getActions() {
        return actions.stream().filter(javax0.workflow.Action::available).collect(Collectors.toSet());
    }
}
