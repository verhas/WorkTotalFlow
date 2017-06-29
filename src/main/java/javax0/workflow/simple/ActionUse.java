package javax0.workflow.simple;

import javax0.workflow.*;
import javax0.workflow.exceptions.ValidatorFailed;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ActionUse<K, V, R, T> implements Action<K, V, R, T> {
    final ActionDef<K, V, R, T> actionDef;
    private final Step<K, V, R, T> step;
    private final boolean auto;
    private Result<K, V, R, T> autoResult;

    public ActionUse(Step<K, V, R, T> step, ActionDef<K, V, R, T> actionDef) {
        this(step, actionDef, false);
    }
    public ActionUse(Step<K, V, R, T> step, ActionDef<K, V, R, T> actionDef,Result<K, V, R, T> autoResult) {
        this(step, actionDef, true);
        this.autoResult = autoResult;
    }

    private ActionUse(Step<K, V, R, T> step, ActionDef<K, V, R, T> actionDef, boolean auto) {
        this.step = step;
        this.actionDef = actionDef;
        this.auto = auto;
    }

    @Override
    public boolean isAuto() {
        return auto;
    }

    @Override
    public Step<K, V, R, T> getStep() {
        return step;
    }

    @Override
    public boolean available() {
        return actionDef.condition == null || actionDef.condition.test(this);
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
    public Parameters<K, V> getParameters() {
        return actionDef.parameters;
    }

    @Override
    public T performPre() {
        if (actionDef.pre != null) {
            return actionDef.pre.apply(this);
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
    public void performPost(T transientObject, Parameters<K, V> userInput) throws ValidatorFailed {

        validatePostAndMerge(transientObject, userInput);
        Action<K, V, R, T> autoAction;
        Collection<Action<K, V, R, T>> actions;
        while (getStep().getWorkflow().getSteps().size() == 1 &&
                (actions = getStep().getWorkflow().getSteps().stream().findAny().get().getActions()).size() == 1 &&
                (autoAction = actions.stream().findAny().get()).isAuto()) {
            transientObject = autoAction.performPre();
            ((ActionUse) autoAction).validatePostAndMerge(transientObject, null);
        }
    }

    private void validatePostAndMerge(T transientObject, Parameters<K, V> userInput) throws ValidatorFailed {
        if (actionDef.validator != null
                && !actionDef.validator.test(this, transientObject, userInput)) {
            throw new ValidatorFailed();
        }

        final Result<K, V, R, T> result;
        if (actionDef.post != null) {
            result = actionDef.post.apply(this, transientObject, userInput);
        } else {
            result = autoResult;
        }
        Collection<Step<K, V, R, T>> steps = result.getSteps();
        mergeSteps(steps);
    }

    /**
     * Merge the workflow into the existing array of workflow that the
     * work flow is in currently.
     * <p>
     * This method is called after a post function is executed.
     *
     * @param steps that replace the current workflow the workflow is in
     */
    private void mergeSteps(Collection<? extends Step<K, V, R, T>> steps) {
        Set<Step<K, V, R, T>> stepSet = new HashSet<>();
        Collection<? extends Step<K, V, R, T>> sourceSteps = getStep().getWorkflow().getSteps();
        if (sourceSteps != null) {
            stepSet.addAll(sourceSteps);
            stepSet.remove(step);
        }
        stepSet.addAll(steps);
        getStep().getWorkflow().setSteps(stepSet);
    }

    /**
     * Joins the workflow passed as argument.
     *
     * @param steps
     */
    @Override
    public void join(Collection<Step<K, V, R, T>> steps) {
        final Collection<Step<K, V, R, T>> stepSet = new HashSet<>();
        final Workflow<K, V, R, T> workflow = getStep().getWorkflow();
        stepSet.addAll(workflow.getSteps());
        stepSet.removeAll(steps);
        workflow.setSteps(stepSet);
    }

    @Override
    public String toString() {
        return "Action[" + getName() + "]";
    }

    @Override
    public R getName() {
        return actionDef.getName();
    }
}
