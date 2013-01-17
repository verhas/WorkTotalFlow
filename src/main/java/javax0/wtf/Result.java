package javax0.wtf;

/**
 *
 * @author Peter Verhas
 */
public interface Result extends HasDefinition<ResultDefinition> {

    /**
     * Get the action of which this result belongs to.
     *
     * @return the action that was currently executed and created this result
     */
    public Action getAction();

    /**
     * Get the steps that this result will bring the workflow into.
     * @return
     */
    public Step[] getSteps();
}
