package javax0.workflow.functions;

import javax0.workflow.Action;
import javax0.workflow.Functions;

public class NullPreFunction<K,V,R,T> implements Functions.Pre<K,V,R,T> {

    public T apply(Action<K,V,R,T> action) {
        return null;
    }

}
