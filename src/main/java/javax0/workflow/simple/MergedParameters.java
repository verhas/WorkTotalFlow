package javax0.workflow.simple;

import javax0.workflow.Parameters;

public class MergedParameters<K, V> implements Parameters<K, V> {

    final private Parameters<K, V> a;
    private final Parameters<K, V> b;

    public MergedParameters(Parameters<K, V> a, Parameters<K, V> b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public V get(K k) {
        V v = a.get(k);
        if (v == null) {
            v = b.get(k);
        }
        return v;
    }
}
