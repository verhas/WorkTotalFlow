package javax0.workflow.functions;

import javax0.workflow.Action;
import javax0.workflow.Functions;

public class TrueCondition<K,V,R,T> implements Functions.Condition<K,V,R,T> {

    public boolean test(Action<K,V,R,T> action) {
        return true;
    }
}
