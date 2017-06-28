package javax0.workflow.simple.builder;

import javax0.workflow.Functions;
import javax0.workflow.Workflow;

import java.util.HashMap;
import java.util.Map;

public class ActionBuilder<K, V, R, T> {
    final R name;
    private final Workflow<K, V, R, T> workflow;
    private final R defaultResult;
    private Map<K, V> parameters = new HashMap<>();
    private Functions.Pre<K, V, R, T> pre = action -> null;
    private Functions.Post<K, V, R, T> post;
    private Functions.Condition<K, V, R, T> condition = action -> true;
    private Functions.Validator<K, V, R, T> validator = (action, t, user) -> true;

    public void build(ActionBlds<K,V,R,T> actions){
        ActionBld<K,V,R,T> actionBld = actions.get(name);
        actionBld.condition = condition;
        actionBld.parameters = k -> parameters.get(k);
        actionBld.post = post;
        actionBld.pre = pre;
        actionBld.validator = validator;
    }

    public ActionBuilder(Workflow<K, V, R, T> workflow, R defaultResult, R name) {
        this.workflow = workflow;
        this.defaultResult = defaultResult;
        this.name = name;
        post = (action, t, user) -> workflow.result(action, defaultResult).get();
    }

    public ActionBuilder validator(Functions.Validator<K, V, R, T> validator) {
        this.validator = validator;
        return this;
    }

    public ActionBuilder condition(Functions.Condition<K, V, R, T> condition) {
        this.condition = condition;
        return this;
    }

    public ActionBuilder preFunction(Functions.Pre<K, V, R, T> pre) {
        this.pre = pre;
        return this;
    }

    public ActionBuilder postFunction(Functions.Post<K, V, R, T> post) {
        this.post = post;
        return this;
    }

    public ActionBuilder parameters(Map.Entry<K, V>... entries) {
        for (final Map.Entry<K, V> entry : entries) {
            parameters.put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public ActionBuilder parameter(K k0, V v0) {
        parameters.put(k0, v0);
        return this;
    }

    public ActionBuilder parameters(K k0, V v0, K k1, V v1) {
        parameters.put(k0, v0);
        parameters.put(k1, v1);
        return this;
    }

    public ActionBuilder parameters(K k0, V v0,
                                    K k1, V v1,
                                    K k2, V v2
    ) {
        parameters.put(k0, v0);
        parameters.put(k1, v1);
        parameters.put(k2, v2);
        return this;
    }

    public ActionBuilder parameters(K k0, V v0,
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

    public ActionBuilder parameters(K k0, V v0,
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

    public ActionBuilder parameters(K k0, V v0,
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

    public ActionBuilder parameters(K k0, V v0,
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

    public ActionBuilder parameters(K k0, V v0,
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

    public ActionBuilder parameters(K k0, V v0,
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

    public ActionBuilder parameters(K k0, V v0,
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
