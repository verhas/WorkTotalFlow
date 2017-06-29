package javax0.workflow.simple;

public class TransitionBuilder<K, V, R, T> {
    private final WorkflowBuilder<K, V, R, T> builder;
    private final R step;

    public TransitionBuilder(WorkflowBuilder<K, V, R, T> builder, R name) {
        this.builder = builder;
        this.step = name;
    }

    public FunctionBuilder action(R name) {
        ActionDef<K, V, R, T> actionDef = builder.actions.get(name);
        ActionUse<K, V, R, T> actionUse = new ActionUse<>(builder.steps.get(step),actionDef);
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
        public final TransitionBuilder<K, V, R, T> to(R... steps) {
            if (builder.defaultResult == null) {
                throw new IllegalArgumentException("Can not use 'to(workflow)' when there is no default result");
            }
            builder.resultMapping.put(step, action, builder.defaultResult, steps);
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
