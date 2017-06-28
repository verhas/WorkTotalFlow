package javax0.workflow.simple.builder;

import javax0.workflow.Action;
import javax0.workflow.Functions;
import javax0.workflow.Parameters;
import javax0.workflow.simple.ActionImpl;

public class ActionBld<K, V, R, T> extends Named<R> {
    R step;
    Parameters parameters;
    Functions.Condition<K, V, R, T> condition;
    Functions.Pre<K, V, R, T> pre;
    Functions.Validator<K, V, R, T> validator;
    Functions.Post<K, V, R, T> post;
    Action<K, V, R, T> action;

    void createAction(StepBlds<K, V, R, T> steps) {
        action = new ActionImpl<K, V, R, T>(steps.get(step).step, parameters, pre, condition, validator, post);
    }
}
