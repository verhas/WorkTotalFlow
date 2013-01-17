package javax0.wtf;

import java.util.Map;

import javax0.wtf.exceptions.ValidatorFailedException;

/**
 * 
 * @author verhas
 */
interface Action extends HasDefinition<ActionDefinition> {

	/**
	 * Get the step that this action is executed from. Every action starts from
	 * a step in the workflow and transients the workflow into step or steps.
	 * This method returns the step from which the action can be executed from.
	 * 
	 * @return the step
	 */
	Step getStep();

	/**
	 * Get the parameter map that belongs to the action.
	 * <p>
	 * Actions are defined in a workflow and an action can be performed
	 * starting from many steps. The definition of the work flow may define
	 * parameters separately at each such use.
	 * <p>
	 * Some implementations, for example {@code SimpleWorkflow} allows actions
	 * to have parameters that are generic for all steps, no matter where the
	 * action is used. In that case the returned map will return the merged set.
	 * 
	 * @return the parameter Map
	 */
	Map<String, String> getParameters();

	/**
	 * Get a result based on its name that belongs to the action.
	 * 
	 * @param name
	 * @throws InvalidResultException
	 *             if there is no result for the action with the given name.
	 * 
	 * @return the result object
	 */
	Result getResult(String name);

	/**
	 * Get all the possible results for this action that are defined in the
	 * workflow definition.
	 * 
	 * @return
	 */
	Result[] getResults();

	/**
	 * Execute the pre-function, validator and post-function for the specific
	 * action and moves the workflow to the target step or target steps in case
	 * the action with the result returned by the post function is a split
	 * action.
	 * <p>
	 * This method is called when an action can be performed automatically
	 * without user input.
	 * <p>
	 * The method should also alter the steps of the work flow object, so the
	 * next call to {@code getSteps()} should return the steps.
	 * 
	 * @throws ValidatorFailedException
	 * @return the step or steps that the work flow is after performing the
	 *         action. This replaces the step 'step' in the argument in the
	 *         array of steps which is returned by getSteps().
	 */
	Step[] perform() throws ValidatorFailedException, ClassNotFoundException,
			InstantiationException, IllegalAccessException;

	/**
	 * Execute the pre-function of the action.
	 * <p>
	 * This method is called when the action should be performed in two phases.
	 * The first phase is the pre-function and gathering user input. The second
	 * phase is to execute the validator and the post-function.
	 * <p>
	 * When the action is performed in two phase the pre-function returns a
	 * {@code transientState} object that the post function and the validator
	 * may use to perform their methods.
	 * 
	 * @return the transient state object the pre-function creates
	 */
	Object performPre() throws ClassNotFoundException, InstantiationException,
			IllegalAccessException;

	/**
	 * Execute the post-function of the action.
	 * <p>
	 * This method is called when the action should be performed in two phases
	 * as described in the documentation of the method {@see
	 * #doPreAction(StepDefinition step, ActionDefinition action)}.
	 * <p>
	 * The method should also alter the steps of the work flow object, so the
	 * next call to {@code getSteps()} should return the steps that the work
	 * flow is after the execution of the action.
	 * 
	 * @param transientObject
	 *            the transient state object returned by the pre-function
	 * @param userInput
	 *            the user-input that was gathered after calling pre-function
	 * @return the steps to which the action transfers from the old step. (Not
	 *         all the steps the work flow is in after the action, only the ones
	 *         that replace the step we transit from.) It returns {@code null}
	 *         if the action postFunction returned null. In that case the steps
	 *         the work flow is in remain the same.
	 * @throws ValidatorFailedException
	 */
	Step[] performPost(Object transientObject, Map<String, String> userInput)
			throws ValidatorFailedException, ClassNotFoundException,
			InstantiationException, IllegalAccessException;

	/**
	 * Join the steps listed in the argument list into this action.
	 * <p>
	 * This method can be used to implement a join programmatically. The post
	 * function may call this method to list all the steps that have to be
	 * joined to the actual flow. Joining means that the workflow will not be in
	 * the steps listed in the argument, but only in the steps in which the
	 * actual action performing leads to.
	 * <p>
	 * More precisely the the method {@code join(Step[] steps)} will remove the
	 * listed steps from the set of steps that the workflow is in currently.
	 * Other steps that are not removed will still belong to the set. The new
	 * set of steps will be the ones that the currently executing action leads
	 * to and the steps that were not joined.
	 * <p>
	 * The actual implementation should be sloppy in the sense that the caller
	 * may specify steps that the workflow is not currently in.
	 * <p>
	 * This is a complimentary method that can be implemented using only the
	 * interface methods, and the implementation in the package
	 * {@code com.verhas.workflow.simple} is one that does not depend on the
	 * actual implementation details.
	 * 
	 * @param steps
	 *            the steps to join.
	 */
	void join(Step[] steps);

	/**
	 * Join the steps listed in the argument list into this action. The steps
	 * are listed by their names and not the actual steps. For more explanation
	 * {@see #join(Step[] steps)}.
	 * 
	 * @param steps
	 *            the names of the steps that are to be joined.
	 */
	void join(String[] steps);
}
