package javax0.workflow.functions;


import javax0.workflow.*;

public class OkPostFunction<K,V,T> implements Functions.PostFunction<K,V,String,T> {

    @Override
    public Result apply(Action<K,V,String,T> action, T transientObject,
                               Parameters<K,V> userInput) {
        return action.result("OK").get();
    }
}
