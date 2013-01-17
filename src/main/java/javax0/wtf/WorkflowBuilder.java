package javax0.wtf;

/**
 *
 * @author Peter Verhas
 */
public interface WorkflowBuilder {

    /**
     * Build the workflow definition from some source. The source can be
     * configuration XML
     * (this functionality is implemented in ConfigBuilder for the simple
     * workflow implementation.)
     *
     * @return the built up workflow definition.
     */
    public WorkflowDefinition build(WorkflowEngine workflowEngine);

}
