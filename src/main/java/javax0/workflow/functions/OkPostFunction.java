package javax0.workflow.functions;


import javax0.workflow.*;

public class OkPostFunction<K,V,T> implements Functions.Post<K,V,String,T> {

    @Override
    public Result<K,V,String,T> apply(Action<K,V,String,T> action, T transientObject,
                               Parameters<K,V> userInput) {
        return action.result("OK").get();
    }
}
