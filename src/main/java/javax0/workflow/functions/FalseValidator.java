package javax0.workflow.functions;


import javax0.workflow.Action;
import javax0.workflow.Functions;
import javax0.workflow.Parameters;

public class FalseValidator<K, V, R, T, C> implements Functions.Validator<K, V, R, T, C> {

    @Override
    public boolean test(Action<K, V, R, T, C> action, T transientObject, Parameters<K, V> userInput) {
        return false;
    }
}
