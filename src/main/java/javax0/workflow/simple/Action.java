package javax0.workflow.simple;

import javax0.workflow.*;
import javax0.workflow.exceptions.ValidatorFailed;

import java.util.*;

/**
 * @author Peter Verhas
 */
public class Action<K,V,R,T> implements javax0.workflow.Action<K,V,R,T> {

    private final javax0.workflow.Step<K, V, R, T> step;
    private final Parameters parameters;
    private final Functions.Condition<K,V,R,T> condition;
    private final Functions.Pre<K,V,R,T> pre;
    private final Functions.Validator<K,V,R,T> validator;
    private final Functions.Post<K,V,R,T> post;

    public Action(javax0.workflow.Step<K,V,R,T> step,
                  Parameters parameters,
                  Functions.Pre pre,
                  Functions.Condition condition,
                  Functions.Pre preFunction,
                  Functions.Validator validator,
                  Functions.Post post) {
        this.step = step;
        this.parameters = parameters;
        this.condition = condition;
        this.pre = preFunction;
        this.validator = validator;
        this.post = post;
    }

    @Override
    public javax0.workflow.Step<K, V, R, T> getStep() {
        return step;
    }

    @Override
    public boolean available() {
        return condition == null || condition.test(this);
    }

    /**
     * Get the parameters from the action definition as well as from the action
     * declaration, and merge the two and return the result. In case key
     * has value in both of the parameters then the one specified for the actual Action
     * is ruling overwriting the one in the ActionDefinition.
     *
     * @return the merged parameters
     */
    @Override
    public Parameters getParameters() {
        return parameters;
    }

    @Override
    public T performPre() {
        if (pre != null) {
            return pre.apply(this);
        } else {
            return null;
        }
    }

    /**
     * Perform the post action of the workflow from step {@code step}
     * through the action {@code action}.
     * <p>
     * After the post function
     *
     * @param transientObject
     * @param userInput
     * @return
     * @throws ValidatorFailed
     */
    @Override
    public Collection<javax0.workflow.Step<K, V, R, T>> performPost(T transientObject,
                                                                    Parameters userInput) throws ValidatorFailed {

        if (validator != null
                && !validator.test(this, transientObject, userInput)) {
            throw new ValidatorFailed();
        }

        final javax0.workflow.Result result;
        if (post != null) {
            result = post.apply(this, transientObject, userInput);
        } else {
            result = () -> Collections.singletonList(getStep());
        }
        Collection<javax0.workflow.Step<K, V, R, T>> steps = result.getSteps();
        mergeSteps(steps);
        return steps;
    }

    /**
     * Merge the steps into the existing array of steps that the
     * work flow is in currently.
     * <p>
     * This method is called after a post function is executed.
     *
     * @param steps that replace the current steps the workflow is in
     */
    private void mergeSteps(Collection<javax0.workflow.Step<K, V, R, T>> steps) {
        Set<javax0.workflow.Step<K, V, R, T>> stepSet = new HashSet<>();
        Collection<javax0.workflow.Step<K, V, R, T>> sourceSteps = getStep().getWorkflow().getSteps();
        if (sourceSteps != null) {
            stepSet.addAll(sourceSteps);
            stepSet.remove(step);
        }
        stepSet.addAll(steps);
        getStep().getWorkflow().setSteps(stepSet);
    }

    /**
     * Joins the steps passed as argument.
     *
     * @param steps
     */
    @Override
    public void join(Collection<javax0.workflow.Step> steps) {
        final Collection<javax0.workflow.Step> stepSet = new HashSet<>();
        final javax0.workflow.Workflow workflow = getStep().getWorkflow();
        stepSet.addAll(workflow.getSteps());
        stepSet.removeAll(steps);
        workflow.setSteps(stepSet);
    }
}
