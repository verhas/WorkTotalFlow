package javax0.workflow.functions;

import javax0.workflow.Action;
import javax0.workflow.Functions;

public class FalseCondition<K,V,R,T> implements Functions.Condition<K,V,R,T> {


    @Override
    public boolean test(Action<K, V, R, T> kvrtAction) {
        return false;
    }
}
