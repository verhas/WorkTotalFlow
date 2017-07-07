package javax0.workflow.simple;

import javax0.workflow.Functions;
import javax0.workflow.Workflow;

public class ActionDefBuilder<K, V, R, T, C> extends ParametersBuilder<ActionDefBuilder<K, V, R, T, C>, K, V> {
    final R name;
    private Functions.Pre<K, V, R, T, C> pre = action -> null;
    private Functions.Post<K, V, R, T, C> post;
    private Functions.Condition<K, V, R, T, C> condition = action -> true;
    private Functions.Validator<K, V, R, T, C> validator = (action, t, user) -> true;

    public ActionDefBuilder(Workflow<K, V, R, T, C> workflow, R defaultResult, R name) {
        this.name = name;
        post = (action, t, user) -> workflow.result(action, defaultResult).get();
    }

    public void build(ActionDefFactory<K, V, R, T, C> actions) {
        ActionDef<K, V, R, T, C> def = actions.get(name);
        def.condition = condition;
        def.parameters = parameters::get;
        def.post = post;
        def.pre = pre;
        def.validator = validator;
    }

    public ActionDefBuilder<K, V, R, T, C> validator(Functions.Validator<K, V, R, T, C> validator) {
        this.validator = validator;
        return this;
    }

    public ActionDefBuilder<K, V, R, T, C> condition(Functions.Condition<K, V, R, T, C> condition) {
        this.condition = condition;
        return this;
    }

    public ActionDefBuilder<K, V, R, T, C> preFunction(Functions.Pre<K, V, R, T, C> pre) {
        this.pre = pre;
        return this;
    }

    public ActionDefBuilder<K, V, R, T, C> postFunction(Functions.Post<K, V, R, T, C> post) {
        this.post = post;
        return this;
    }

}
