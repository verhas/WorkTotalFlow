package javax0.workflow.simple;

import javax0.workflow.Step;
import javax0.workflow.Workflow;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WorkflowBuilder<K, V, R, T, C> {

    private final ResultSupplier<K, V, R, T, C> resultSupplier = new ResultSupplier<>();
    private final Workflow<K, V, R, T, C> workflow =
            new WorkflowImpl<>(resultSupplier::supplier);
    private final ResultFactory<K, V, R, T, C> results = new ResultFactory<>();
    R defaultName;
    ActionDefFactory<K, V, R, T, C> actions = new ActionDefFactory<>();
    StepFactory<K, V, R, T, C> steps = new StepFactory<>(workflow);
    ResultTargetStepsMap<K, V, R, T, C> resultMapping = new ResultTargetStepsMap<>(actions, results, steps);
    private Map<R, ActionDefBuilder<K, V, R, T, C>> pendingActionBuilders = new HashMap<>();

    public WorkflowBuilder(R defaultName) {
        this.defaultName = defaultName;
    }

    public WorkflowBuilder() {
        this.defaultName = null;
    }

    @SafeVarargs
    public final Workflow<K, V, R, T, C> start(R... startSteps) {
        checkActionsAreDefined();
        buildActions();
        createSteps();
        prepareResultSupplier();
        workflow.setSteps(Arrays.stream(startSteps).map(steps::step).collect(Collectors.toSet()));
        cleanUpBuilderData();
        return workflow;
    }

    private void prepareResultSupplier() {
        for (MapTuple<K, V, R, T, C> key : resultMapping.keySet()) {
            ActionDef<K, V, R, T, C> action = actions.get(key.action.name);
            Step<K, V, R, T, C> step = steps.get(key.step.name);
            ResultImpl<K, V, R, T, C> result = new ResultImpl<>();
            result.getSteps().addAll(
                    resultMapping.get(key.step.name, key.action.name, key.result.name)
                            .stream()
                            .map(steps::step)
                            .collect(Collectors.toSet())
            );
            resultSupplier.put(step, action, key.result.name, result);
        }
    }

    private void buildActions() {
        for (final ActionDefBuilder<K, V, R, T, C> builder : pendingActionBuilders.values()) {
            builder.build(actions);
        }
    }

    private void createSteps() {
        for (final R stepId : steps.keySet()) {
            steps.get(stepId);
        }
    }

    private void checkActionsAreDefined() {
        for (final ActionDefBuilder<K, V, R, T, C> builder : pendingActionBuilders.values()) {
            if (!actions.containsKey(builder.name)) {
                throw new IllegalArgumentException(String.format("Action '%s' defined but not used.", builder.name));
            }
        }
        for (R actionName : actions.keySet()) {
            if (!actionName.equals(defaultName) &&
                    !pendingActionBuilders.containsKey(actionName)) {
                throw new IllegalArgumentException(String.format("Action '%s' used but not defined.", actionName));
            }
        }
    }

    private void cleanUpBuilderData() {
        resultMapping = null;
        steps = null;
        actions = null;
        defaultName = null;
        pendingActionBuilders = null;
    }

    public TransitionBuilder<K, V, R, T, C> from(R name) {
        steps.get(name);
        return new TransitionBuilder<>(this, name);
    }

    public ActionDefBuilder<K, V, R, T, C> action(R name) {
        final ActionDefBuilder<K, V, R, T, C> ab = new ActionDefBuilder<>(workflow, defaultName, name);
        pendingActionBuilders.put(name, ab);
        return ab;
    }

}
