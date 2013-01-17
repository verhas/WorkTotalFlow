package javax0.wtf.exceptions;

/**
 * This exception may be thrown by a workflow implementation when the list of
 * actions available for execution are queried for a step that the workflow
 * is currently not in.
 *
 * @author Peter Verhas
 */
@SuppressWarnings("serial")
public class InvalidStateException extends WorkflowException {
}
