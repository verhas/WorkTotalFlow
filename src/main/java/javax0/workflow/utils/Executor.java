package javax0.workflow.utils;

import javax0.workflow.Action;
import javax0.workflow.Parameters;
import javax0.workflow.Step;
import javax0.workflow.Workflow;
import javax0.workflow.exceptions.ValidatorFailed;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Executor<K, V, R, T, C> {
    private final WorkflowWrapper<K, V, R, T, C> wrapper;
    private final Workflow<K, V, R, T, C> workflow;
    private final ActionSelector<R> selectAction;
    private final UserInputQuery<K, V, T> queryUserInput;
    private final ValidationFailureHandler<K, V, R, T, C> validationFailureHandler;

    public Executor(WorkflowWrapper<K, V, R, T, C> wrapper,
                    ActionSelector<R> selectAction,
                    UserInputQuery<K, V, T> queryUserInput,
                    ValidationFailureHandler<K, V, R, T, C> validationFailureHandler) {
        this.wrapper = wrapper;
        this.workflow = wrapper.workflow;
        this.selectAction = selectAction;
        this.queryUserInput = queryUserInput;
        this.validationFailureHandler = validationFailureHandler;
    }

    public void step() {
        Collection<Step<K, V, R, T, C>> steps = workflow.getSteps();
        Map<R, Collection<R>> actionMap = new HashMap<>();
        for (final Step<K, V, R, T, C> step : steps) {
            Collection<R> actions = step.getActions().map(Action::getName).collect(Collectors.toSet());
            if (!actions.isEmpty()) {
                actionMap.put(step.getName(), actions);
            }
        }
        Map.Entry<R, R> entry = selectAction.apply(actionMap);
        wrapper.step(entry.getKey());
        if (wrapper.canExecute(entry.getValue())) {
            wrapper.logger.accept(String.format("From step '%s' action '%s' is executed", entry.getKey(), entry.getValue()));
            Action<K, V, R, T, C> action = wrapper.action();
            T transientObject = action.performPre();
            Parameters<K, V> userInput = queryUserInput.apply(transientObject);
            try {
                action.performPost(transientObject, userInput);
            } catch (ValidatorFailed validatorFailed) {
                validationFailureHandler.accept(workflow);
            }
        }
    }

    public interface ActionSelector<R> extends Function<Map<R, Collection<R>>, Map.Entry<R, R>> {
    }

    public interface UserInputQuery<K, V, T> extends Function<T, Parameters<K, V>> {
    }

    public interface ValidationFailureHandler<K, V, R, T, C> extends Consumer<Workflow<K, V, R, T, C>> {
    }
}
