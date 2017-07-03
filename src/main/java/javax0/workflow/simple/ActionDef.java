package javax0.workflow.simple;

import javax0.workflow.Functions;
import javax0.workflow.Parameters;

/**
 * @author Peter Verhas
 */
class ActionDef<K, V, R, T, C> extends Named<R> {

    Parameters<K,V> parameters;
    Functions.Condition<K, V, R, T, C> condition;
    Functions.Pre<K, V, R, T, C> pre;
    Functions.Validator<K, V, R, T, C> validator;
    Functions.Post<K, V, R, T, C> post;

    @Override
    public String toString() {
        return "ActionDef[" + getName() + "]";
    }
}
