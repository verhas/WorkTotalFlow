package javax0.workflow.simple;

import javax0.workflow.Step;
import javax0.workflow.Workflow;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WorkflowBuilder<K, V, R, T> {

    private final ResultSupplier<K, V, R, T> resultSupplier = new ResultSupplier<>();
    private final Workflow<K, V, R, T> workflow =
            new WorkflowImpl<>(resultSupplier::supplier);
    R defaultResult;
    ActionDefs<K, V, R, T> actions = new ActionDefs<>();
    Steps<K, V, R, T> steps = new Steps<>(workflow);
    private final Results<K, V, R, T> results = new Results<>();
    ResultMap<K, V, R, T> resultMapping = new ResultMap<>(actions, results, steps);
    private Map<R, ActionDefBuilder<K, V, R, T>> pendingActionBuilders = new HashMap<>();

    public WorkflowBuilder(R defaultResult) {
        this.defaultResult = defaultResult;
    }

    public WorkflowBuilder() {
        this.defaultResult = null;
    }

    @SafeVarargs
    public final Workflow<K, V, R, T> start(R... startSteps) {
        checkActionsAreDefined();
        buildActions();
        createSteps();
        prepareResultSupplier();
        workflow.setSteps(Arrays.stream(startSteps).map(steps::step).collect(Collectors.toSet()));
        cleanUpBuilderData();
        return workflow;
    }

    private void prepareResultSupplier() {
        for (MapTuple<K, V, R, T> key : resultMapping.keySet()) {
            ActionDef<K, V, R, T> action = actions.get(key.action.name);
            Step<K, V, R, T> step = steps.get(key.step.name);
            ResultImpl<K, V, R, T> result = new ResultImpl<>();
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
        for (final ActionDefBuilder<K, V, R, T> builder : pendingActionBuilders.values()) {
            builder.build(actions);
        }
    }

    private void createSteps() {
        for (final R stepId : steps.keySet()) {
            steps.get(stepId);
        }
    }

    private void checkActionsAreDefined() {
        for (final ActionDefBuilder<K, V, R, T> builder : pendingActionBuilders.values()) {
            if (!actions.containsKey(builder.name)) {
                throw new IllegalArgumentException(String.format("Action '%s' defined but not used.", builder.name));
            }
        }
        for (R actionName : actions.keySet()) {
            if (!pendingActionBuilders.containsKey(actionName)) {
                throw new IllegalArgumentException(String.format("Action '%s' used but not defined.", actionName));
            }
        }
    }

    private void cleanUpBuilderData() {
        resultMapping = null;
        steps = null;
        actions = null;
        defaultResult = null;
        pendingActionBuilders = null;
    }

    public TransitionBuilder<K, V, R, T> from(R name) {
        steps.get(name);
        return new TransitionBuilder<>(this, name);
    }

    public ActionDefBuilder<K, V, R, T> action(R name) {
        final ActionDefBuilder<K, V, R, T> ab = new ActionDefBuilder<>(workflow, defaultResult, name);
        pendingActionBuilders.put(name, ab);
        return ab;
    }

}
