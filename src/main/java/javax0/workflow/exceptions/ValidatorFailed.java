package javax0.workflow.exceptions;

/**
 * This exception is thrown by a workflow method {@code doAction} and
 * {@code doPostAction} when the action can not be executed fully because the
 * validator returned false.
 *
 * @author Peter Verhas
 */
public class ValidatorFailed extends RuntimeException {
}
