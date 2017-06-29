package javax0.workflow.utils;

import javax0.workflow.Action;
import javax0.workflow.Step;
import javax0.workflow.Workflow;
import javax0.workflow.exceptions.ValidatorFailed;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class WorkflowWrapper<K, V, R, T> {
    private final Workflow<K, V, R, T> workflow;
    private Consumer<String> logger = (s) -> {
    };
    private R lastQueriedStepId = null;
    private Step<K, V, R, T> lastQueriedStep = null;

    public WorkflowWrapper(Workflow<K, V, R, T> workflow) {
        this.workflow = workflow;
    }

    public static <K, V, R, T> Collection<R> stepsOf(Workflow<K, V, R, T> workflow) {
        return workflow.getSteps().stream().map(Step::getName).collect(Collectors.toSet());
    }

    public static <K, V, R, T> Collection<R> actionsOf(Step<K, V, R, T> step) {
        return step.getActions().stream().map(Action::getName).collect(Collectors.toSet());
    }

    public Executor from(Step<K, V, R, T> step) {
        return new Executor(step);
    }

    public void setLogger(Consumer<String> logger) {
        this.logger = logger;
    }

    public boolean isOnlyIn(R step) {
        lastQueriedStepId = step;
        lastQueriedStep = null;
        Collection<R> stepNames = stepsOf(workflow);
        return stepNames.size() == 1 && stepNames.contains(step);
    }

    public boolean notOnlyIn(R step) {
        return !isOnlyIn(step);
    }

    public boolean isIn(R step) {
        lastQueriedStepId = step;
        lastQueriedStep = null;
        Collection<R> stepNames = stepsOf(workflow);
        return stepNames.contains(step);
    }

    public boolean notIn(R step) {
        return !isIn(step);
    }

    private R getLastQueriedActionId = null;
    public boolean canExecute(R action) {
        if( lastQueriedStep == null && lastQueriedStepId != null ){
            lastQueriedStep = step();
        }
        getLastQueriedActionId = action;
        return actionsOf(lastQueriedStep).contains(action);
    }

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

    @SafeVarargs
    public final boolean notIn(R... steps) {
        return !isIn(steps);
    }

    public Step<K, V, R, T> step() {
        return step(lastQueriedStepId);
    }

    public Step<K, V, R, T> step(R id) {
        return lastQueriedStep = workflow.getSteps().stream().filter(s -> s.getName().equals(id)).findAny().orElse(null);
    }


    public void execute(R actionId) throws ValidatorFailed {
        getLastQueriedActionId = actionId;
        execute();
    }

    public void execute() throws ValidatorFailed {
        if( lastQueriedStep == null && lastQueriedStepId != null ){
            lastQueriedStep = step();
        }
        from(lastQueriedStep).execute(getLastQueriedActionId);
    }

    public class Executor {
        private final Step<K, V, R, T> step;

        public Executor(Step<K, V, R, T> step) {
            this.step = step;
        }

        public void execute(R id) throws ValidatorFailed {
            Action<K, V, R, T> action = step.getActions().stream()
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
