package javax0.workflow.functions;


import javax0.workflow.Action;
import javax0.workflow.Functions;
import javax0.workflow.Parameters;
import javax0.workflow.Result;

public class OkPostFunction<K, V, T, C> implements Functions.Post<K, V, String, T, C> {

    @Override
    public Result<K, V, String, T, C> apply(Action<K, V, String, T, C> action, T transientObject,
                                         Parameters<K, V> userInput) {
        return action.result("OK").get();
    }
}
