package javax0.workflow.simple;

import javax0.workflow.Functions;
import javax0.workflow.Workflow;

/**
 * An {@code ActionDefBuilder} as the name imposes can be used to create an {@link ActionDef} (the definition
 * of an action). An instance of the builder is created by the {@link WorkflowBuilder} and it immediately puts the
 * created {@link ActionDefBuilder} on a list, which is processed later when the {@link WorkflowBuilder} finishes.
 *
 * @param <K> see {@link Workflow} for documentation
 * @param <V> see {@link Workflow} for documentation
 * @param <R> see {@link Workflow} for documentation
 * @param <T> see {@link Workflow} for documentation
 * @param <C> see {@link Workflow} for documentation
 */
public class ActionDefBuilder<K, V, R, T, C> extends ParametersBuilder<ActionDefBuilder<K, V, R, T, C>, K, V> {
    final R name;
    private Functions.Pre<K, V, R, T, C> pre = action -> null;
    private Functions.Post<K, V, R, T, C> post;
    private Functions.Condition<K, V, R, T, C> condition = action -> true;
    private Functions.Validator<K, V, R, T, C> validator = (action, t, user) -> true;

    ActionDefBuilder(Workflow<K, V, R, T, C> workflow, R defaultResult, R name) {
        this.name = name;
        post = (action, t, user) -> workflow.result(action, defaultResult).get();
    }

    /**
     * Build the {@link ActionDef} using the actionDefFactory. Note that this method is not supposed to be part of the
     * builder API, it is called from the {@link WorkflowBuilder}.
     *
     * @param actionDefFactory the factory to use for the creation of the {@link ActionDef}
     */
    void build(ActionDefFactory<K, V, R, T, C> actionDefFactory) {
        ActionDef<K, V, R, T, C> def = actionDefFactory.get(name);
        def.condition = condition;
        def.parameters = parameters::get;
        def.post = post;
        def.pre = pre;
        def.validator = validator;
    }

    /**
     * Define the validator for this action.
     *
     * @param validator the validator
     * @return this to chain fluent API
     */
    public ActionDefBuilder<K, V, R, T, C> validator(Functions.Validator<K, V, R, T, C> validator) {
        this.validator = validator;
        return this;
    }

    /**
     * Define condition for this action
     *
     * @param condition the condition
     * @return this to chain fluent API
     */
    public ActionDefBuilder<K, V, R, T, C> condition(Functions.Condition<K, V, R, T, C> condition) {
        this.condition = condition;
        return this;
    }

    /**
     * Define pre function for this action.
     * @param pre the pre function
     * @return this to chain fluent API
     */
    public ActionDefBuilder<K, V, R, T, C> preFunction(Functions.Pre<K, V, R, T, C> pre) {
        this.pre = pre;
        return this;
    }

    /**
     * Define post function for this action.
     * @param post the pre function
     * @return this to chain fluent API
     */
    public ActionDefBuilder<K, V, R, T, C> postFunction(Functions.Post<K, V, R, T, C> post) {
        this.post = post;
        return this;
    }

}
