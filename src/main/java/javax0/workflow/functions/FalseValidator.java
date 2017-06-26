package javax0.workflow.functions;


import javax0.workflow.Action;
import javax0.workflow.Functions;
import javax0.workflow.Parameters;

public class FalseValidator<K, V, R,T> implements Functions.Validator<K, V, R,T> {

    @Override
    public boolean test(Action<K, V, R,T> action, T transientObject, Parameters<K, V> userInput) {
        return false;
    }
}
