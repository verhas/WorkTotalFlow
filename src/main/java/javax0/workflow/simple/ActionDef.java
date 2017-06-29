package javax0.workflow.simple;

import javax0.workflow.Functions;
import javax0.workflow.Parameters;

/**
 * @author Peter Verhas
 */
class ActionDef<K, V, R, T> extends Named<R> {

    Parameters<K,V> parameters;
    Functions.Condition<K, V, R, T> condition;
    Functions.Pre<K, V, R, T> pre;
    Functions.Validator<K, V, R, T> validator;
    Functions.Post<K, V, R, T> post;

    @Override
    public String toString() {
        return "ActionDef[" + getName() + "']";
    }
}
