package javax0.workflow.simple;

import javax0.workflow.Parameters;
import javax0.workflow.Result;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TransitionBuilder<K, V, R, T, C> {
    private final WorkflowBuilder<K, V, R, T, C> builder;
    private final R step;

    TransitionBuilder(WorkflowBuilder<K, V, R, T, C> builder, R name) {
        this.builder = builder;
        this.step = name;
    }

    @SafeVarargs
    public final TransitionBuilder<K, V, R, T, C> to(R... steps) {
        if (builder.defaultName == null) {
            throw new IllegalArgumentException("Can not use 'to(steps)' when there is no default name");
        }
        final ActionDef<K, V, R, T, C> actionDef = builder.actions.get(builder.defaultName);
        final Parameters<K, V> defaultActionDefParameters = null;
        final Result<K, V, R, T, C> autoResult = new ResultImpl<>();

        autoResult.getSteps().addAll(Arrays.stream(steps).map(builder.steps::get).collect(Collectors.toSet()));

        final ActionUse<K, V, R, T, C> actionUse = new ActionUse<>(builder.steps.get(step), actionDef, defaultActionDefParameters, autoResult);
        builder.steps.get(step).actions.add(actionUse);
        builder.resultMapping.put(step, builder.defaultName, builder.defaultName, steps);
        return TransitionBuilder.this;
    }

    public FunctionBuilder action(R name) {
        ActionDef<K, V, R, T, C> actionDef = builder.actions.get(name);
        ActionUse<K, V, R, T, C> actionUse = new ActionUse<>(builder.steps.get(step), actionDef, null);
        builder.steps.get(step).actions.add(actionUse);
        return new FunctionBuilder(name, actionUse);
    }

    public class FunctionBuilder extends ParametersBuilder<FunctionBuilder, K, V> {
        private final R action;
        private final ActionUse<K, V, R, T, C> actionUse;

        FunctionBuilder(R action, ActionUse<K, V, R, T, C> actionUse) {
            this.actionUse = actionUse;
            builder.actions.get(action);
            this.action = action;
        }

        @SafeVarargs
        public final TransitionBuilder<K, V, R, T, C> to(R... steps) {
            if (builder.defaultName == null) {
                throw new IllegalArgumentException("Can not use 'to(workflow)' when there is no default result");
            }
            builder.resultMapping.put(step, action, builder.defaultName, steps);
            actionUse.setParameters(parameters::get);
            return TransitionBuilder.this;
        }

        public ResultBuilder when(R name) {
            actionUse.setParameters(parameters::get);
            return new ResultBuilder(name);
        }

        public class ResultBuilder {
            private final R result;

            public ResultBuilder(R result) {
                this.result = result;
            }

            @SafeVarargs
            public final FunctionBuilder then(R... steps) {
                builder.resultMapping.put(step, action, result, steps);
                return FunctionBuilder.this;
            }
        }
    }

}
