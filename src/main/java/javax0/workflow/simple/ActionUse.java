package javax0.workflow.simple;

import javax0.workflow.*;
import javax0.workflow.exceptions.ValidatorFailed;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implement the functionality of the {@link Action} interface that is tied to a specific transition. An action is
 * composed of an {@link ActionDef} and an {@link ActionUse}. The {@link ActionUse} implements the interface and
 * delegates some of the functionality to {@link ActionDef}.
 * <p>
 * One action can be used to initiate transition from many steps. This is implemented in a way that the part of the
 * functionality that is tied to a specific transition is in {@link ActionUse}. Several {@link ActionUse}
 * objects may refer to the same {@link ActionDef}.
 * </p>
 *
 * @param <K> see {@link Workflow} for documentation
 * @param <V> see {@link Workflow} for documentation
 * @param <I> see {@link Workflow} for documentation
 * @param <R> see {@link Workflow} for documentation
 * @param <T> see {@link Workflow} for documentation
 * @param <C> see {@link Workflow} for documentation
 */
public class ActionUse<K, V, I, R, T, C> implements Action<K, V, I, R, T, C> {
    final ActionDef<K, V, I, R, T, C> actionDef;
    private final Step<K, V, I, R, T, C> step;
    private final boolean auto;
    private Result<K, V, I, R, T, C> autoResult;
    private Map<K, V> parameters;
    private Parameters<K, V> merged;

    /**
     * Create a new action implemented as {@link ActionUse} that starts from the step {@code step} and uses the
     * definition of {@code actionDef}.
     *
     * @param step      the step from which this action starts from
     * @param actionDef the definition of the action
     */
    private ActionUse(Step<K, V, I, R, T, C> step, ActionDef<K, V, I, R, T, C> actionDef) {
        this(step, actionDef, null, false);
    }

    /**
     * Create a new action implemented as {@link ActionUse}.
     *
     * @param step       see {@link #ActionUse(Step, ActionDef)}
     * @param actionDef  see {@link #ActionUse(Step, ActionDef)}
     * @param parameters the parameters of the action. Note that the {@link ActionDef} can also have parameters and the
     *                   parameters of the action are the merge of the two.
     */
    ActionUse(Step<K, V, I, R, T, C> step, ActionDef<K, V, I, R, T, C> actionDef, Parameters<K, V> parameters) {
        this(step, actionDef, parameters, false);
    }

    /**
     * @param step       see {@link #ActionUse(Step, ActionDef)}
     * @param actionDef  see {@link #ActionUse(Step, ActionDef)}
     * @param parameters see {@link #ActionUse(Step, ActionDef, Parameters)}
     * @param autoResult the result that this action has. This is used when the action does not have post function that
     *                   would return a result. In that case the workflow is transferred to the steps specified by the
     *                   autoresult. The model does not even use a result in this case, but the implementation is
     *                   that there is an autoResult which is used if there is no other source of result.
     */
    ActionUse(Step<K, V, I, R, T, C> step, ActionDef<K, V, I, R, T, C> actionDef, Parameters<K, V> parameters, Result<K, V, I, R, T, C> autoResult) {
        this(step, actionDef, parameters, true);
        this.autoResult = autoResult;
    }

    /**
     * Create an action.
     *
     * @param step       see {@link #ActionUse(Step, ActionDef)}
     * @param actionDef  see {@link #ActionUse(Step, ActionDef)}
     * @param parameters see {@link #ActionUse(Step, ActionDef, Parameters)}
     * @param auto       true if the action has to be executed automatically. (In that case there can not be any result, the
     *                   result is "autoResult" (see {@link #ActionUse(Step, ActionDef, Parameters, Result)})
     */
    private ActionUse(Step<K, V, I, R, T, C> step, ActionDef<K, V, I, R, T, C> actionDef, Parameters<K, V> parameters, boolean auto) {
        this.step = step;
        this.actionDef = actionDef;
        this.merged = new MergedParameters<>(parameters, actionDef.parameters);
        this.auto = auto;
    }

    @Override
    public boolean isAuto() {
        return auto;
    }

    @Override
    public Step<K, V, I, R, T, C> getStep() {
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
     * is ruling overwriting the one in the ActionDef.
     *
     * @return the merged parameters
     */
    @Override
    public Parameters<K, V> getParameters() {
        return merged;
    }

    public void setParameters(Parameters<K, V> parameters) {
        merged = new MergedParameters<>(parameters, actionDef.parameters);
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
     * After the post function the implementation checks if there is any eligible auto action to be executed and if
     * there is then it executes it.
     *
     * @param transientObject see the documentation of the interface {@link Action}
     * @param userInput  see the documentation of the interface {@link Action}
     * @throws ValidatorFailed  see the documentation of the interface {@link Action}
     */
    @Override
    public void performPost(T transientObject, I userInput) throws ValidatorFailed {

        validatePostAndMerge(transientObject, userInput);
        executeAutoActions();
    }

    /**
     * Execute auto actions in a loop so long as long there is auto action to advance the state of the workflow
     * or we get into an infinite loop.
     *
     * @throws ValidatorFailed see the documentation of the interface {@link Action}
     */
    private void executeAutoActions() throws ValidatorFailed {
        Set<Step<K, V, I, R, T, C>> visitedSteps = new HashSet<>();
        T transientObject;
        Action<K, V, I, R, T, C> autoAction;
        Collection<Action<K, V, I, R, T, C>> actions;
        while (workflowIsInASingleStep() &&
                (autoAction = oneSingleAutoStepFromThere()) != null) {
            Step<K, V, I, R, T, C> visitedStep = autoAction.getStep();
            checkInfiniteLoop(visitedSteps, visitedStep);
            transientObject = autoAction.performPre();
            ((ActionUse<K, V, I, R, T, C>) autoAction).validatePostAndMerge(transientObject, null);
        }
    }

    /**
     * Checks if we have already been in the step from which we want to step forward during transitioning along
     * auto actions. If w have already been here then this is an infinite loop.
     *
     * @param visitedSteps the set of the steps that we have already visited during this auto action traversal
     * @param visitedStep the current step from which we want to step forward
     */
    private void checkInfiniteLoop(Set<Step<K, V, I, R, T, C>> visitedSteps, Step<K, V, I, R, T, C> visitedStep) {
        if (visitedSteps.contains(visitedStep)) {
            throw new IllegalStateException("Probably infinite loop in auto actions");
        }
        visitedSteps.add(visitedStep);
    }

    /**
     * Get at most two elements from the stream of actions and return the first if there is only one. If there are
     * more than one elements then return null.
     *
     * @return the action to be executed automatically or null if there is no such action or there are more than one
     * actions (even if some of them is auto).
     */
    private Action<K, V, I, R, T, C> oneSingleAutoStepFromThere() {
        return actionsStream().limit(2).collect(Collectors.collectingAndThen(Collectors.toList(),
                (List<Action<K, V, I, R, T, C>> list) ->
                        list.size() == 1 && list.get(0).isAuto() ? list.get(0) : null
        ));
    }

    private Stream<Action<K, V, I, R, T, C>> actionsStream() {
        final Optional<Step<K, V, I, R, T, C>> step;
        return (step = getStep().getWorkflow().getSteps().stream().findAny()).isPresent() ?
                step.get().getActions() : Stream.empty();
    }

    private boolean workflowIsInASingleStep() {
        return getStep().getWorkflow().getSteps().size() == 1;
    }

    private void validatePostAndMerge(T transientObject, I userInput) throws ValidatorFailed {
        if (actionDef.validator != null
                && !actionDef.validator.test(this, transientObject, userInput)) {
            throw new ValidatorFailed();
        }

        final Result<K, V, I, R, T, C> result;
        if (actionDef.post != null) {
            result = actionDef.post.apply(this, transientObject, userInput);
        } else {
            result = autoResult;
        }
        Collection<Step<K, V, I, R, T, C>> steps = result.getSteps();
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
    private void mergeSteps(Collection<? extends Step<K, V, I, R, T, C>> steps) {
        Set<Step<K, V, I, R, T, C>> stepSet = new HashSet<>();
        Collection<? extends Step<K, V, I, R, T, C>> sourceSteps = getStep().getWorkflow().getSteps();
        if (sourceSteps != null) {
            stepSet.addAll(sourceSteps);
            stepSet.remove(step);
        }
        stepSet.addAll(steps);
        getStep().getWorkflow().setSteps(stepSet);
    }

    @Override
    public void join(Collection<Step<K, V, I, R, T, C>> steps) {
        final Collection<Step<K, V, I, R, T, C>> stepSet = new HashSet<>();
        final Workflow<K, V, I, R, T, C> workflow = getStep().getWorkflow();
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
