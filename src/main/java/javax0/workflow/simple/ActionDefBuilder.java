package javax0.workflow.simple;

import javax0.workflow.Functions;
import javax0.workflow.Workflow;

import java.util.HashMap;
import java.util.Map;

public class ActionDefBuilder<K, V, R, T, C> {
    final R name;
    private final Map<K, V> parameters = new HashMap<>();
    private Functions.Pre<K, V, R, T, C> pre = action -> null;
    private Functions.Post<K, V, R, T, C> post;
    private Functions.Condition<K, V, R, T, C> condition = action -> true;
    private Functions.Validator<K, V, R, T, C> validator = (action, t, user) -> true;

    public ActionDefBuilder(Workflow<K, V, R, T, C> workflow, R defaultResult, R name) {
        this.name = name;
        post = (action, t, user) -> workflow.result(action, defaultResult).get();
    }

    public void build(ActionDefs<K, V, R, T, C> actions) {
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

    @SafeVarargs
    public final ActionDefBuilder<K, V, R, T, C> parameters(Map.Entry<K, V>... entries) {
        for (final Map.Entry<K, V> entry : entries) {
            parameters.put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public ActionDefBuilder<K, V, R, T, C> parameter(K k0, V v0) {
        parameters.put(k0, v0);
        return this;
    }

    public ActionDefBuilder parameters(K k0, V v0, K k1, V v1) {
        parameters.put(k0, v0);
        parameters.put(k1, v1);
        return this;
    }

    public ActionDefBuilder parameters(K k0, V v0,
                                       K k1, V v1,
                                       K k2, V v2
    ) {
        parameters.put(k0, v0);
        parameters.put(k1, v1);
        parameters.put(k2, v2);
        return this;
    }

    public ActionDefBuilder parameters(K k0, V v0,
                                       K k1, V v1,
                                       K k2, V v2,
                                       K k3, V v3
    ) {
        parameters.put(k0, v0);
        parameters.put(k1, v1);
        parameters.put(k2, v2);
        parameters.put(k3, v3);
        return this;
    }

    public ActionDefBuilder parameters(K k0, V v0,
                                       K k1, V v1,
                                       K k2, V v2,
                                       K k3, V v3,
                                       K k4, V v4
    ) {
        parameters.put(k0, v0);
        parameters.put(k1, v1);
        parameters.put(k2, v2);
        parameters.put(k3, v3);
        parameters.put(k4, v4);
        return this;
    }

    public ActionDefBuilder parameters(K k0, V v0,
                                       K k1, V v1,
                                       K k2, V v2,
                                       K k3, V v3,
                                       K k4, V v4,
                                       K k5, V v5
    ) {
        parameters.put(k0, v0);
        parameters.put(k1, v1);
        parameters.put(k2, v2);
        parameters.put(k3, v3);
        parameters.put(k4, v4);
        parameters.put(k5, v5);
        return this;
    }

    public ActionDefBuilder parameters(K k0, V v0,
                                       K k1, V v1,
                                       K k2, V v2,
                                       K k3, V v3,
                                       K k4, V v4,
                                       K k5, V v5,
                                       K k6, V v6
    ) {
        parameters.put(k0, v0);
        parameters.put(k1, v1);
        parameters.put(k2, v2);
        parameters.put(k3, v3);
        parameters.put(k4, v4);
        parameters.put(k5, v5);
        parameters.put(k6, v6);
        return this;
    }

    public ActionDefBuilder parameters(K k0, V v0,
                                       K k1, V v1,
                                       K k2, V v2,
                                       K k3, V v3,
                                       K k4, V v4,
                                       K k5, V v5,
                                       K k6, V v6,
                                       K k7, V v7
    ) {
        parameters.put(k0, v0);
        parameters.put(k1, v1);
        parameters.put(k2, v2);
        parameters.put(k3, v3);
        parameters.put(k4, v4);
        parameters.put(k5, v5);
        parameters.put(k6, v6);
        parameters.put(k7, v7);
        return this;
    }

    public ActionDefBuilder parameters(K k0, V v0,
                                       K k1, V v1,
                                       K k2, V v2,
                                       K k3, V v3,
                                       K k4, V v4,
                                       K k5, V v5,
                                       K k6, V v6,
                                       K k7, V v7,
                                       K k8, V v8
    ) {
        parameters.put(k0, v0);
        parameters.put(k1, v1);
        parameters.put(k2, v2);
        parameters.put(k3, v3);
        parameters.put(k4, v4);
        parameters.put(k5, v5);
        parameters.put(k6, v6);
        parameters.put(k7, v7);
        parameters.put(k8, v8);
        return this;
    }

    public ActionDefBuilder parameters(K k0, V v0,
                                       K k1, V v1,
                                       K k2, V v2,
                                       K k3, V v3,
                                       K k4, V v4,
                                       K k5, V v5,
                                       K k6, V v6,
                                       K k7, V v7,
                                       K k8, V v8,
                                       K k9, V v9) {
        parameters.put(k0, v0);
        parameters.put(k1, v1);
        parameters.put(k2, v2);
        parameters.put(k3, v3);
        parameters.put(k4, v4);
        parameters.put(k5, v5);
        parameters.put(k6, v6);
        parameters.put(k7, v7);
        parameters.put(k8, v8);
        parameters.put(k9, v9);
        return this;
    }
}
