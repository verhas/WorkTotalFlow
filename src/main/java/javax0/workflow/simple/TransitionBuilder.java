package javax0.workflow.simple;

import javax0.workflow.Result;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TransitionBuilder<K, V, R, T, C> {
    private final WorkflowBuilder<K, V, R, T, C> builder;
    private final R step;

    public TransitionBuilder(WorkflowBuilder<K, V, R, T, C> builder, R name) {
        this.builder = builder;
        this.step = name;
    }

    @SafeVarargs
    public final TransitionBuilder<K, V, R, T, C> to(R... steps) {
        if (builder.defaultName == null) {
            throw new IllegalArgumentException("Can not use 'to(steps)' when there is no default name");
        }
        ActionDef<K, V, R, T, C> actionDef = builder.actions.get(builder.defaultName);
        Result<K, V, R, T, C> autoResult = new ResultImpl<>();
        autoResult.getSteps().addAll(Arrays.stream(steps).map(builder.steps::get).collect(Collectors.toSet()));
        ActionUse<K, V, R, T, C> actionUse = new ActionUse<>(builder.steps.get(step), actionDef, autoResult);
        builder.steps.get(step).actions.add(actionUse);
        builder.resultMapping.put(step, builder.defaultName, builder.defaultName, steps);
        return TransitionBuilder.this;
    }

    public FunctionBuilder action(R name) {
        ActionDef<K, V, R, T, C> actionDef = builder.actions.get(name);
        ActionUse<K, V, R, T, C> actionUse = new ActionUse<>(builder.steps.get(step), actionDef);
        builder.steps.get(step).actions.add(actionUse);
        return new FunctionBuilder(name);
    }

    public class FunctionBuilder {
        private final R action;

        public FunctionBuilder(R action) {
            builder.actions.get(action);
            this.action = action;
        }

        @SafeVarargs
        public final TransitionBuilder<K, V, R, T, C> to(R... steps) {
            if (builder.defaultName == null) {
                throw new IllegalArgumentException("Can not use 'to(workflow)' when there is no default result");
            }
            builder.resultMapping.put(step, action, builder.defaultName, steps);
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

            @SafeVarargs
            public final FunctionBuilder then(R... steps) {
                builder.resultMapping.put(step, action, result, steps);
                return FunctionBuilder.this;
            }
        }
    }

}
