package javax0.workflow.simple.builder;

import javax0.workflow.Action;
import javax0.workflow.Result;
import javax0.workflow.Step;
import javax0.workflow.Workflow;
import javax0.workflow.simple.ActionImpl;
import javax0.workflow.simple.ResultImpl;
import javax0.workflow.simple.StepImpl;
import javax0.workflow.simple.WorkflowImpl;

import java.util.*;
import java.util.stream.Collectors;

public class WorkflowBuilder<K, V, R, T> {
    private final Action<K, V, R, T> UNDEFINED_ACTION = new ActionImpl<>(
            null, null, null, null, null, null);
    private final Map<Action<K, V, R, T>, Map<R, Result<K, V, R, T>>> resultSupplierMap = new HashMap<>();
    private final Workflow<K, V, R, T> workflow =
            new WorkflowImpl<>((a, r) -> () -> resultSupplierMap.getOrDefault(a, Collections.emptyMap()).get(r));
    private R defaultResult;
    private ActionBlds<K, V, R, T> actions = new ActionBlds<>();
    private StepBlds<K, V, R, T> steps = new StepBlds<>();
    private ResultBlds<R> results = new ResultBlds<>();
    private Map<R, ActionBuilder<K, V, R, T>> pendingActionBuilders = new HashMap<>();
    private ResultMap<K, V, R, T> resultMapping = new ResultMap<>(actions, results, steps);

    public WorkflowBuilder(R defaultResult) {
        this.defaultResult = defaultResult;
    }

    public WorkflowBuilder() {
        this.defaultResult = null;
    }

    public Workflow<K, V, R, T> start(R... startSteps) {
        checkActionsAreDefined();
        buildActions();
        createSteps();
        createActions();
        wireActionsToSteps();
        workflow.setSteps(Arrays.stream(startSteps).map(steps::step).collect(Collectors.toSet()));
        cleanUpBuilderData();
        return workflow;
    }

    private void prepareSupplierMap(){
        for( Tuple<K,V,R,T> key : resultMapping.keySet()){
            Action action = actions.get(key.action.name).action;
            final Map<R, Result<K, V, R, T>> resultMap;
            if( resultSupplierMap.containsKey(action)){
                resultMap = resultSupplierMap.get(action);
            }else{
                resultSupplierMap.put(action, resultMap = new HashMap<>());
            }
            resultMap.put(key.result.name, new ResultImpl<>(
                    resultMapping.get(key.action.name,key.result.name)
                            .stream()
                            .map(steps::step)
                            .collect(Collectors.toSet())
            ));
        }
    }

    private void wireActionsToSteps(){
        for(final R stepId : steps.keySet()){
            Step<K,V,R,T> step = steps.get(stepId).step;
           for( final R actionId : steps.get(stepId).actions){
               step.getActions().add(actions.get(actionId).action);
           }
        }
    }

    private void buildActions() {
        for (final ActionBuilder<K, V, R, T> builder : pendingActionBuilders.values()) {
            builder.build(actions);
        }
    }

    private void createActions() {
        for (final R actionId : actions.keySet()) {
            ActionBld actionBld = actions.get(actionId);
            actionBld.createAction(steps);
        }
    }

    private void createSteps() {
        for (final R stepId : steps.keySet()) {
            StepBld<K, V, R, T> stepBld = steps.get(stepId);
            stepBld.step = new StepImpl<>(workflow, new HashSet<>());
        }
    }

    private void checkActionsAreDefined() {
        for (final ActionBuilder<K, V, R, T> builder : pendingActionBuilders.values()) {
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

    public TransitionBuilder from(R name) {
        steps.get(name);
        return new TransitionBuilder(name);
    }

    public ActionBuilder<K, V, R, T> action(R name) {
        final ActionBuilder<K, V, R, T> ab = new ActionBuilder<K, V, R, T>(workflow, defaultResult, name);
        pendingActionBuilders.put(name, ab);
        return ab;
    }


    public class TransitionBuilder {
        private final R step;

        public TransitionBuilder(R name) {
            this.step = name;
        }

        public FunctionBuilder action(R name) {
            return new FunctionBuilder(name);
        }

        public class FunctionBuilder {
            private final R action;

            public FunctionBuilder(R action) {
                actions.get(action);
                this.action = action;
            }

            public TransitionBuilder to(R... steps) {
                if (defaultResult == null) {
                    throw new IllegalArgumentException("Can not use to(steps) when there is no default result");
                }
                resultMapping.put(action, defaultResult, steps);
                return TransitionBuilder.this;
            }

            public ResultBuilder when(R name) {
                return new ResultBuilder(name);
            }

            public class ResultBuilder {
                private final R result;

                public ResultBuilder(R result) {
                    this.result = result;
                }

                public FunctionBuilder then(R... steps) {
                    resultMapping.put(action, result, steps);
                    return FunctionBuilder.this;
                }
            }
        }

    }
}
