package javax0.workflow.simple;


import javax0.workflow.Action;
import javax0.workflow.Step;
import javax0.workflow.Workflow;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * @author Peter Verhas
 */
public class StepImpl<K, V, R, T> extends Named<R> implements Step<K, V, R, T> {

    private final Workflow<K, V, R, T> workflow;
    final Collection<Action<K, V, R, T>> actions;

    public StepImpl(Workflow<K, V, R, T> workflow) {
        this.workflow = workflow;
        this.actions = new HashSet<>();
    }

    public Workflow<K, V, R, T> getWorkflow() {
        return workflow;
    }

    @Override
    public Collection<Action<K, V, R, T>> getActions() {
        return actions.stream().filter(Action::available).collect(Collectors.toSet());
    }
    @Override
    public String toString(){
        return "Step["+getName()+"]";
    }

}
