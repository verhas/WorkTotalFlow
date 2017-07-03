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
    private final Workflow<K, V, R, T, C> workflow;
    private final Function<Map<Step<K, V, R, T, C>, Collection<Action<K, V, R, T, C>>>, Map.Entry<Step<K, V, R, T, C>, Action<K, V, R, T, C>>> selectAction;
    private final Function<T, Parameters<K, V>> queryUserInput;
    private final Consumer<Workflow<K, V, R, T, C>> validationFailureHandler;

    public Executor(Workflow<K, V, R, T, C> workflow,
                    Function<Map<Step<K, V, R, T, C>, Collection<Action<K, V, R, T, C>>>, Map.Entry<Step<K, V, R, T, C>, Action<K, V, R, T, C>>> selectAction,
                    Function<T, Parameters<K, V>> queryUserInput,
                    Consumer<Workflow<K, V, R, T, C>> validationFailureHandler) {
        this.workflow = workflow;
        this.selectAction = selectAction;
        this.queryUserInput = queryUserInput;
        this.validationFailureHandler = validationFailureHandler;
    }

    public void step() {
        Collection<Step<K, V, R, T, C>> steps = workflow.getSteps();
        Map<Step<K, V, R, T, C>, Collection<Action<K, V, R, T, C>>> actionMap = new HashMap<>();
        for (final Step<K, V, R, T, C> step : steps) {
            Collection<Action<K, V, R, T, C>> actions = step.getActions().collect(Collectors.toSet());
            if (!actions.isEmpty()) {
                actionMap.put(step, actions);
            }
        }
        Map.Entry<Step<K, V, R, T, C>, Action<K, V, R, T, C>> entry = selectAction.apply(actionMap);
        T transientObject = entry.getValue().performPre();
        Parameters<K, V> userInput = queryUserInput.apply(transientObject);
        try {
            entry.getValue().performPost(transientObject, userInput);
        } catch (ValidatorFailed validatorFailed) {
            validationFailureHandler.accept(workflow);
        }
    }
}
