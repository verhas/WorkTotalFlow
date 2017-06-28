package javax0.workflow.simple.builder;

import java.util.Objects;

abstract class Named<R> {
    R name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Named<R> named = (Named<R>) o;
        return Objects.equals(name, named.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
