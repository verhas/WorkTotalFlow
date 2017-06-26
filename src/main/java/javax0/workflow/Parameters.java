package javax0.workflow;

@FunctionalInterface
public interface Parameters<K,V> {
    V get(K key);
}
