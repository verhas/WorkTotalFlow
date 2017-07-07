package javax0.workflow.simple;

import java.util.HashMap;
import java.util.Map;

/**
 * Builder classes that support parameter addition extend this class.
 *
 * @param <CLASS> should be the actual class that extends this abstract class. This type is used as the return type
 *               of the fluent functions. It is possible to provide a different class that is also extending
 *               this abstract class, but the methods return 'this' cast to (CLASS) and in that case it will cause
 *               class cast exception. The restriction that the actual parameter type for CLASS has to be the class
 *               that actually extends this abstract class can not be expressed with generics in Java, thus such a
 *               coding error can only be discovered only during run-time.
 * @param <K>     the key for the parameters
 * @param <V>     the value for the parameters
 */
abstract class ParametersBuilder<CLASS extends ParametersBuilder<CLASS, K, V>, K, V> {
    protected final Map<K, V> parameters = new HashMap<>();


    @SafeVarargs
    public final CLASS parameters(Map.Entry<K, V>... entries) {
        for (final Map.Entry<K, V> entry : entries) {
            parameters.put(entry.getKey(), entry.getValue());
        }
        return (CLASS) this;
    }

    public CLASS parameter(K k0, V v0) {
        parameters.put(k0, v0);
        return (CLASS) this;
    }

    public CLASS parameters(K k0, V v0, K k1, V v1) {
        parameters.put(k0, v0);
        parameters.put(k1, v1);
        return (CLASS) this;
    }

    public CLASS parameters(K k0, V v0,
                            K k1, V v1,
                            K k2, V v2
    ) {
        parameters.put(k0, v0);
        parameters.put(k1, v1);
        parameters.put(k2, v2);
        return (CLASS) this;
    }

    public CLASS parameters(K k0, V v0,
                            K k1, V v1,
                            K k2, V v2,
                            K k3, V v3
    ) {
        parameters.put(k0, v0);
        parameters.put(k1, v1);
        parameters.put(k2, v2);
        parameters.put(k3, v3);
        return (CLASS) this;
    }

    public CLASS parameters(K k0, V v0,
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
        return (CLASS) this;
    }

    public CLASS parameters(K k0, V v0,
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
        return (CLASS) this;
    }

    public CLASS parameters(K k0, V v0,
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
        return (CLASS) this;
    }

    public CLASS parameters(K k0, V v0,
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
        return (CLASS) this;
    }

    public CLASS parameters(K k0, V v0,
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
        return (CLASS) this;
    }

    public CLASS parameters(K k0, V v0,
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
        return (CLASS) this;
    }
}
