package javax0.workflow.utils;

import javax0.workflow.Action;
import javax0.workflow.Step;
import javax0.workflow.Workflow;
import javax0.workflow.exceptions.ValidatorFailed;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class WorkflowWrapper<K, V, R, T, C> {
    private final Workflow<K, V, R, T, C> workflow;
    private Consumer<String> logger = (s) -> {
    };
    private R lastQueriedStepId = null;
    private Step<K, V, R, T, C> lastQueriedStep = null;
    private R getLastQueriedActionId = null;

    public WorkflowWrapper(Workflow<K, V, R, T, C> workflow) {
        this.workflow = workflow;
    }

    /**
     * Get the collection of the names of the steps that the workflow is in.
     *
     * @param workflow which we examine
     * @param <K>      type parameter for the workflow.
     * @param <V>      type parameter for the workflow.
     * @param <R>      the type of the names, usually String in most of the applications.
     * @param <T>      type parameter for the workflow.
     * @param <C>      type parameter for the workflow.
     * @return the name collection of the steps.
     */
    public static <K, V, R, T, C> Collection<R> stepsOf(Workflow<K, V, R, T, C> workflow) {
        return workflow.getSteps().stream().map(Step::getName).collect(Collectors.toSet());
    }

    /**
     * Get the collection of the names of the actions that can be executed from the given step.
     *
     * @param step from which the actions start.
     * @param <K>  type parameter for the workflow.
     * @param <V>  type parameter for the workflow.
     * @param <R>  the type of the names, usually String in most of the applications.
     * @param <T>  type parameter for the workflow.
     * @param <C>  type parameter for the workflow.
     * @return the name collection of the actions.
     */
    public static <K, V, R, T, C> Collection<R> actionsOf(Step<K, V, R, T, C> step) {
        return step.getActions().map(Action::getName).collect(Collectors.toSet());
    }

    /**
     * Start an execution from the given step. The method can be used in fluent call like
     * <pre>
     *     from(step).execute(action)
     * </pre>
     *
     * @param step from which the execution of the action can start
     * @return the executor to be used in the fluent call
     */
    public Executor from(Step<K, V, R, T, C> step) {
        return new Executor(step);
    }

    /**
     * Set a logger consumer where log messages will be sent.
     * <p>
     * This functionality will be depreated in later releases and System.getLogger will be used for Java 9.
     * </p>
     *
     * @param logger
     */
    public void setLogger(Consumer<String> logger) {
        this.logger = logger;
    }

    /**
     * Checks that the workflow is, is not in the actual step and in other steps.
     * <p>
     * To support simpler call API the wrapper also remembers the step that was queried and subsequent
     * calls to method like {@link #canExecute(Object)} does not need to specify the again.
     * </p>
     *
     * @param step the name of the step we want to check that the workflow is in it.
     * @return true if the workflow is only in one single step at the moment and that step has the name {@code step}
     */
    public boolean isOnlyIn(R step) {
        lastQueriedStepId = step;
        lastQueriedStep = null;
        Collection<R> stepNames = stepsOf(workflow);
        return stepNames.size() == 1 && stepNames.contains(step);
    }

    /**
     * Checks that the workflow is, is not in the actual step and in other steps.
     * <p>
     * To support simpler call API the wrapper also remembers the step that was queried and subsequent
     * calls to method like {@link #canExecute(Object)} does not need to specify the again.
     * </p>
     *
     * @param step the name of the step we want to check that the workflow is not (only) in it.
     * @return true if the workflow is not in this step, or it is in this step but also in other steps. This
     * method is essentially {@code !isOnlyIn(step)}
     */
    public boolean notOnlyIn(R step) {
        return !isOnlyIn(step);
    }

    /**
     * Checks that the workflow is, is not in the actual step and in other steps.
     * <p>
     * To support simpler call API the wrapper also remembers the step that was queried and subsequent
     * calls to method like {@link #canExecute(Object)} does not need to specify the again.
     * </p>
     *
     * @param step the name of the step we want to check that the workflow is in it.
     * @return true if the workflow is in this step. It may also be in other steps.
     */
    public boolean isIn(R step) {
        lastQueriedStepId = step;
        lastQueriedStep = null;
        Collection<R> stepNames = stepsOf(workflow);
        return stepNames.contains(step);
    }

    /**
     * Checks that the workflow is, is not in the actual step and in other steps.
     * <p>
     * To support simpler call API the wrapper also remembers the step that was queried and subsequent
     * calls to method like {@link #canExecute(Object)} does not need to specify the again.
     * </p>
     *
     * @param step the name of the step we want to check that the workflow is not in it.
     * @return true if the workflow is not in this step.
     */
    public boolean notIn(R step) {
        return !isIn(step);
    }

    /**
     * Checks that the action can be executed from the step of the last step that was examined.
     *
     * @param action that is checked for ability to execute
     * @return true if the action can be executed.
     */
    public boolean canExecute(R action) {
        if (lastQueriedStep == null && lastQueriedStepId != null) {
            lastQueriedStep = step();
        }
        getLastQueriedActionId = action;
        return lastQueriedStep.getActions(a -> a.getName().equals(action)).count() == 1;
    }

    /**
     * Checks that the workflow is in the steps.
     *
     * @param steps in which the workflow should be in
     * @return true of the workflow is in the steps. The return value is still trues if the workflow is in other
     * steps as well not listed in the argument, so this check does not require that the workflow is exactly in these
     * steps.
     */
    @SafeVarargs
    public final boolean isIn(R... steps) {
        Collection<R> stepNames = stepsOf(workflow);
        for (final R step : steps) {
            if (!stepNames.contains(step)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check that workflow is not in the steps.
     *
     * @param steps in all of which the workflow should not be in.
     * @return true when calling {@link #isIn(Object[])} would return false.
     */
    @SafeVarargs
    public final boolean notIn(R... steps) {
        return !isIn(steps);
    }

    /**
     * @return the step that was queries last time.
     */
    public Step<K, V, R, T, C> step() {
        return step(lastQueriedStepId);
    }

    /**
     * @param id of the step
     * @return the step for the id
     */
    public Step<K, V, R, T, C> step(R id) {
        return lastQueriedStep = workflow.getSteps().stream().filter(s -> s.getName().equals(id)).findAny().orElse(null);
    }


    /**
     * Execute the action specified.
     * <p>
     * This method also stores the actionId as the last action that was queries to ease the subsequent API calls.
     * </p>
     *
     * @param actionId is the name of the action.
     * @throws ValidatorFailed when the validator defined in the action fails.
     */
    public void execute(R actionId) throws ValidatorFailed {
        getLastQueriedActionId = actionId;
        execute();
    }

    /**
     * Execute the last queried action.
     * <p>
     * This method can be called after the action was checked calling {@link #canExecute(Object)}. The
     * method {@link #canExecute(Object)} stores the last queried action so this is not required to
     * specify again the id of the action.
     * </p>
     *
     * @throws ValidatorFailed
     */
    public void execute() throws ValidatorFailed {
        if (lastQueriedStep == null && lastQueriedStepId != null) {
            lastQueriedStep = step();
        }
        from(lastQueriedStep).execute(getLastQueriedActionId);
    }

    /**
     * An inner class returned by some methods so that fluent api can be used.
     * See the documentation of the method {@link #from(Step)}
     */
    public class Executor {
        private final Step<K, V, R, T, C> step;

        public Executor(Step<K, V, R, T, C> step) {
            this.step = step;
        }

        /**
         * Execute the action
         * @param id is the name of the action
         * @throws ValidatorFailed when the validator defined in the action fails.
         */
        public void execute(R id) throws ValidatorFailed {
            Action<K, V, R, T, C> action = step.getActions()
                    .filter(a -> a.getName().equals(id)).findAny().orElse(null);
            logger.accept(String.format("From step '%s' action '%s' is '%s'", step.getName(), id, action));
            if (action == null) {
                throw new IllegalArgumentException(
                        String.format("Action '%s' can not be executed from step '%s'.", id, step.getName()));
            }
            action.perform();
        }
    }


}
