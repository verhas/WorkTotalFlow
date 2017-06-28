package javax0.workflow.simple.builder;

import javax0.workflow.Step;

import java.util.Collection;

class StepBld<K, V, R, T>  extends Named<R> {
    Step<K, V, R, T> step;
    Collection<R> actions;
}
