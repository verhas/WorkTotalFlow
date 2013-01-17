package javax0.wtf;

/**
 * This interface defines the methods that are available to manage the work flow
 * of an item.
 *
 * @author Peter Verhas
 */
public interface Workflow extends HasDefinition<WorkflowDefinition>{

  /**
   * Get the steps that the work flow is currently in.
   */
  public Step[] getSteps();

  /**
   * Set the steps the workflow is currently in. This is used to restore a
   * workflow from persistence. This is the only persistence that CVW needs.
   * All other state should be handled outside the workflow.
   */
  public void setSteps(Step[] steps);

  /**
   * Set the workflow to be in the steps for which the names are given. This is a
   * complimentary method that helps applications that persist the workflow
   * state.
   *
   * @param name the name of the step
   */
  public void setSteps(String[] name);

  /**
   * Get the names of the steps that the workflow is in. This is a compilentary
   * method that helps the applications that persist the workflow.
   * 
   * @return the array of the step names
   */
  public String[] getStepNames();

}
