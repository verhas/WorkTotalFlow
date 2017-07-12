package javax0.workflow.simple;

import javax0.workflow.Step;
import javax0.workflow.Workflow;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WorkflowBuilder<K, V, I, R, T, C> {

    private final ResultSupplier<K, V, I, R, T, C> resultSupplier = new ResultSupplier<>();
    private final WorkflowImpl<K, V, I, R, T, C> workflow =
            new WorkflowImpl<>(resultSupplier::supplier);
    private final ResultFactory<K, V, I, R, T, C> results = new ResultFactory<>();
    R defaultName;
    ActionDefFactory<K, V, I, R, T, C> actionDefFactory = new ActionDefFactory<>();
    StepFactory<K, V, I, R, T, C> stepFactory = new StepFactory<>(workflow);
    ResultTargetStepsMap<K, V, I, R, T, C> resultMapping = new ResultTargetStepsMap<>(actionDefFactory, results, stepFactory);
    private Map<R, ActionDefBuilder<K, V, I, R, T, C>> pendingActionBuilders = new HashMap<>();

    public WorkflowBuilder(R defaultName) {
        this.defaultName = defaultName;
    }

    public WorkflowBuilder() {
        this.defaultName = null;
    }

    @SafeVarargs
    public final Workflow<K, V, I, R, T, C> start(R... startSteps) {
        checkActionsAreDefined();
        buildActions();
        createSteps();
        prepareResultSupplier();
        workflow.setSteps(Arrays.stream(startSteps).map(stepFactory::step).collect(Collectors.toSet()));
        cleanUpBuilderData();
        return workflow;
    }

    private void prepareResultSupplier() {
        for (TargetStepTriuple<K, V, I, R, T, C> key : resultMapping.keySet()) {
            ActionDef<K, V, I, R, T, C> action = actionDefFactory.get(key.action.name);
            Step<K, V, I, R, T, C> step = stepFactory.get(key.step.name);
            ResultImpl<K, V, I, R, T, C> result = new ResultImpl<>();
            result.getSteps().addAll(
                    resultMapping.get(key.step.name, key.action.name, key.result.name)
                            .stream()
                            .map(stepFactory::step)
                            .collect(Collectors.toSet())
            );
            resultSupplier.put(step, action, key.result.name, result);
        }
    }

    private void buildActions() {
        for (final ActionDefBuilder<K, V, I, R, T, C> builder : pendingActionBuilders.values()) {
            builder.build(actionDefFactory);
        }
    }

    private void createSteps() {
        for (final R stepId : stepFactory.keySet()) {
            stepFactory.get(stepId);
        }
    }

    private void checkActionsAreDefined() {
        for (final ActionDefBuilder<K, V, I, R, T, C> builder : pendingActionBuilders.values()) {
            if (!actionDefFactory.isAlreadyCreated(builder.name)) {
                throw new IllegalArgumentException(String.format("Action '%s' defined but not used.", builder.name));
            }
        }
        for (R actionName : actionDefFactory.keySet()) {
            if (!actionName.equals(defaultName) &&
                    !pendingActionBuilders.containsKey(actionName)) {
                throw new IllegalArgumentException(String.format("Action '%s' used but not defined.", actionName));
            }
        }
    }

    private void cleanUpBuilderData() {
        resultMapping = null;
        stepFactory = null;
        actionDefFactory = null;
        defaultName = null;
        pendingActionBuilders = null;
    }

    public TransitionBuilder<K, V, I, R, T, C> from(R name) {
        stepFactory.get(name);
        return new TransitionBuilder<>(this, name);
    }

    public ActionDefBuilder<K, V, I, R, T, C> action(R name) {
        final ActionDefBuilder<K, V, I, R, T, C> ab = new ActionDefBuilder<>(workflow, defaultName, name);
        pendingActionBuilders.put(name, ab);
        return ab;
    }

}
