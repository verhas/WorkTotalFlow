package javax0.wtf;

/**
 * ResultDefinition object is returned by the method {@see PostFunction#postFunction()}.
 * The actual result will help the workflow execution environment to select the
 * steps that it will get into after the post function was executed in an
 * action.
 * <p>
 * The workflow builder creates the {@code ResultDefinition} objects and the workflow
 * execution should not create new instances. To get access one of the already
 * created {@code ResultDefinition} objects the post function should call
 * {@see StepDefinition#getResultDefinition(Action action, String name)}.
 *
 * @author Peter Verhas
 */
public interface ResultDefinition extends Definition {

    /**
     * Get the definition of the steps where this result will lead if returned
     * may an actions post function.
     *
     * @return array of the step definitions
     */
    public StepDefinition[] getStepDefinitions();
}
