package javax0.workflow.functions;

import javax0.workflow.Action;
import javax0.workflow.Functions;

public class TrueCondition<K, V, R, T, C> implements Functions.Condition<K, V, R, T, C> {

    public boolean test(Action<K, V, R, T, C> action) {
        return true;
    }
}
