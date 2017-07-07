package javax0.workflow.simple;

/**
 * Anything that has a name in the simple workflow implementation extends this abstract class.
 *
 * @param <R> the type of the name, usually {@link String}
 */
abstract class Named<R> {
    R name;

    public R getName() {
        return name;
    }
}
