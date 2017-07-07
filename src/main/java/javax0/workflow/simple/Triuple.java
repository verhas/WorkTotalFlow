package javax0.workflow.simple;

import java.util.Objects;

/**
 * Triuple is a tuple that contains three things of types A, B and C. Triuple is usually used to form a compound key
 * for some Map instead of using a Map with values being Maps which again contain Maps.
 *
 * @param <A> type of the fist element in a triuple
 * @param <B> type of the second element in a triuple
 * @param <C> type of the third element in a triuple
 */
abstract class Triuple<A, B, C> {
    protected final A a;
    protected final B b;
    protected final C c;

    protected Triuple(A a, B b, C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triuple<?, ?, ?> that = (Triuple<?, ?, ?>) o;
        return Objects.equals(a, that.a) &&
                Objects.equals(b, that.b) &&
                Objects.equals(c, that.c);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c);
    }
}
