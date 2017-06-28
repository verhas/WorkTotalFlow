package javax0.workflow.simple.builder;

import javax0.workflow.Step;

public class StepBlds<K, V, R, T> extends BldMap<R,StepBld<K, V, R, T>> {

    @Override
    StepBld<K, V, R, T> factory() {
        return new StepBld();
    }

    Step<K, V, R, T> step(R name){
        return get(name).step;
    }
}
