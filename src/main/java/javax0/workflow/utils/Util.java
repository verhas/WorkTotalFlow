package javax0.workflow.utils;

import javax0.workflow.Action;
import javax0.workflow.Step;
import javax0.workflow.Workflow;
import javax0.workflow.exceptions.ValidatorFailed;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Util<K, V, R, T> {
    private static Consumer<String> logger = (s) -> {
    };

    public static void setLogger(Consumer<String> logger) {
        Util.logger = logger;
    }

    public static <K, V, R, T> Collection<R> stepsOf(Workflow<K, V, R, T> workflow) {
        return workflow.getSteps().stream().map(Step::getName).collect(Collectors.toSet());
    }

    public static <K, V, R, T> Collection<R> actionsOf(Step<K, V, R, T> step) {
        return step.getActions().stream().map(Action::getName).collect(Collectors.toSet());
    }

    public static <K, V, R, T> Util<K, V, R, T>.Executor from(Step<K, V, R, T> step) {
        return new Util<K, V, R, T>().new Executor(step);
    }

    public static <K, V, R, T> Util<K, V, R, T>.WorkflowUtil workflow(Workflow<K, V, R, T> workflow) {
        return new Util<K, V, R, T>().new WorkflowUtil(workflow);
    }

    public class Executor {
        private final Step<K, V, R, T> step;

        public Executor(Step<K, V, R, T> step) {
            this.step = step;
        }

        public void execute(R id) throws ValidatorFailed {
            Action<K, V, R, T> action = step.getActions().stream()
                    .filter(a -> a.getName().equals(id)).findAny().orElse(null);
            logger.accept(String.format("From step '%s' action '%s' is '%s'",step.getName(),id,action));
            if (action == null) {
                throw new IllegalArgumentException(
                        String.format("Action '%s' can not be executed from step '%s'.", id, step.getName()));
            }
            action.perform();
        }
    }

    public class WorkflowUtil {
        private final Workflow<K, V, R, T> workflow;

        public WorkflowUtil(Workflow<K, V, R, T> workflow) {
            this.workflow = workflow;
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

        public Step<K, V, R, T> getStep(R id) {
            return workflow.getSteps().stream().filter(s -> s.getName().equals(id)).findAny().orElse(null);
        }
    }


}
