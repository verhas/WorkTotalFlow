package javax0.workflow;

/**
 * Interface implemented by anything that has a name of type R.
 * @param <R> the type of a name, or id.
 */
public interface Named<R> {
    R getName();
}
