package javax0.workflow.simple;

import javax0.workflow.*;
import javax0.workflow.exceptions.ValidatorFailed;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ActionUse<K, V, R, T, C> implements Action<K, V, R, T, C> {
    final ActionDef<K, V, R, T, C> actionDef;
    private final Step<K, V, R, T, C> step;
    private final boolean auto;
    private Result<K, V, R, T, C> autoResult;

    public ActionUse(Step<K, V, R, T, C> step, ActionDef<K, V, R, T, C> actionDef) {
        this(step, actionDef, false);
    }

    public ActionUse(Step<K, V, R, T, C> step, ActionDef<K, V, R, T, C> actionDef, Result<K, V, R, T, C> autoResult) {
        this(step, actionDef, true);
        this.autoResult = autoResult;
    }

    private ActionUse(Step<K, V, R, T, C> step, ActionDef<K, V, R, T, C> actionDef, boolean auto) {
        this.step = step;
        this.actionDef = actionDef;
        this.auto = auto;
    }

    @Override
    public boolean isAuto() {
        return auto;
    }

    @Override
    public Step<K, V, R, T, C> getStep() {
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
     * @throws ValidatorFailed
     */
    @Override
    public void performPost(T transientObject, Parameters<K, V> userInput) throws ValidatorFailed {

        validatePostAndMerge(transientObject, userInput);
        executeAutoActions();
    }

    private void executeAutoActions() throws ValidatorFailed {
        T transientObject;
        Action<K, V, R, T, C> autoAction;
        Collection<Action<K, V, R, T, C>> actions;
        while (workflowIsInASingleStep() &&
                (autoAction = oneSingleAutoStepFromThere()) != null) {
            transientObject = autoAction.performPre();
            ((ActionUse<K, V, R, T, C>) autoAction).validatePostAndMerge(transientObject, null);
        }
    }

    private Action<K, V, R, T, C> oneSingleAutoStepFromThere() {
        Stream<Action<K, V, R, T, C>> actions = actionsStream().limit(2);
        return actions.collect(Collectors.collectingAndThen(Collectors.toList(),
                (List<Action<K, V, R, T, C>> list) ->
                        list.size() == 1 && list.get(0).isAuto() ? list.get(0) : null
        ));
    }

    private Stream<Action<K, V, R, T, C>> actionsStream() {
        return getStep().getWorkflow().getSteps().stream().findAny().get().getActions();
    }

    private boolean workflowIsInASingleStep() {
        return getStep().getWorkflow().getSteps().size() == 1;
    }

    private void validatePostAndMerge(T transientObject, Parameters<K, V> userInput) throws ValidatorFailed {
        if (actionDef.validator != null
                && !actionDef.validator.test(this, transientObject, userInput)) {
            throw new ValidatorFailed();
        }

        final Result<K, V, R, T, C> result;
        if (actionDef.post != null) {
            result = actionDef.post.apply(this, transientObject, userInput);
        } else {
            result = autoResult;
        }
        Collection<Step<K, V, R, T, C>> steps = result.getSteps();
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
    private void mergeSteps(Collection<? extends Step<K, V, R, T, C>> steps) {
        Set<Step<K, V, R, T, C>> stepSet = new HashSet<>();
        Collection<? extends Step<K, V, R, T, C>> sourceSteps = getStep().getWorkflow().getSteps();
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
    public void join(Collection<Step<K, V, R, T, C>> steps) {
        final Collection<Step<K, V, R, T, C>> stepSet = new HashSet<>();
        final Workflow<K, V, R, T, C> workflow = getStep().getWorkflow();
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
