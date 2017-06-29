package javax0.workflow.simple;

import javax0.workflow.Step;
import javax0.workflow.Workflow;

class Steps<K, V, R, T> extends BldMap<R,StepImpl<K, V, R, T>> {
    private final Workflow<K, V, R, T> workflow;

    public Steps(Workflow<K, V, R, T> workflow) {
        this.workflow = workflow;
    }

    @Override
    StepImpl<K, V, R, T> factory() {
        return new StepImpl<>(workflow);
    }

    Step<K, V, R, T> step(R name){
        return get(name);
    }
}
