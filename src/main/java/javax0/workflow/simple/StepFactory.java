package javax0.workflow.simple;

import javax0.workflow.Step;
import javax0.workflow.Workflow;

class StepFactory<K, V, R, T, C> extends NamedFactory<R,StepImpl<K, V, R, T, C>> {
    private final Workflow<K, V, R, T, C> workflow;

    StepFactory(Workflow<K, V, R, T, C> workflow) {
        this.workflow = workflow;
    }

    int limit(){
        return keySet().size();
    }

    @Override
    StepImpl<K, V, R, T, C> factory() {
        return new StepImpl<>(workflow);
    }

    Step<K, V, R, T, C> step(R name){
        return get(name);
    }
}
