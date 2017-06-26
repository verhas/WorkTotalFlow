package javax0.workflow.functions;

import javax0.workflow.Action;
import javax0.workflow.Functions;

public class NullPreFunction<K,V,R,T> implements Functions.PreFunction<K,V,R,T> {

    public Object apply(Action<K,V,R,T> action) {
        return null;
    }

}
