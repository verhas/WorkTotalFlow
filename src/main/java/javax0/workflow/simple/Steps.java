package javax0.workflow.simple;

import javax0.workflow.Step;
import javax0.workflow.Workflow;

class Steps<K, V, R, T, C> extends BldMap<R,StepImpl<K, V, R, T, C>> {
    private final Workflow<K, V, R, T, C> workflow;

    public Steps(Workflow<K, V, R, T, C> workflow) {
        this.workflow = workflow;
    }

    @Override
    StepImpl<K, V, R, T, C> factory() {
        return new StepImpl<>(workflow);
    }

    Step<K, V, R, T, C> step(R name){
        return get(name);
    }
}
