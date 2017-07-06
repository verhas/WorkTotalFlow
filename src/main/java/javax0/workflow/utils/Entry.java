package javax0.workflow.utils;

import java.util.Map;

/**
 * A simple entry implementation for Java 8. When using Java 9 use the built-in {@link Map#entry(Object, Object)} method.
 * @param <K>
 * @param <V>
 */
@Deprecated
public class Entry<K, V> implements Map.Entry<K, V> {
    final K key;
    V value;

    private Entry(K key) {
        this.key = key;
    }

    public static <K, V> Entry entry(K k, V v) {
        Entry<K, V> e = new Entry<>(k);
        e.setValue(v);
        return e;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }
}
