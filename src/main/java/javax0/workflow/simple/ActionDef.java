package javax0.workflow.simple;

import javax0.workflow.Functions;
import javax0.workflow.Parameters;
import javax0.workflow.Workflow;

/**
 * An ActionDef is the definition of an action. It defines the conditions, the parameters ties to the action,
 * the pre and post functions and the validator. It does not tied to any step. An {@link javax0.workflow.Action}
 * implemented by {@link ActionUse} is tied to certain source steps and may of them may share ActionDefs.
 * <p>
 * An {@link javax0.workflow.Action} is implemented by {@link ActionUse} and to do that each {@link ActionUse}
 * has an ActionDef. Multiple {@link ActionUse} instances may share the same ActionDef.
 * <p>
 * The formal definition of the workflow and the API interfaces do not specify how the actual workflow
 * implementation should
 * handle the situation when the same action can be executed from different steps. It would not be practical
 * to require the library user to define differently named actions for the different steps to perform the
 * same actions. Thus the implementation separates the definition of the action functionality defined in the
 * interface to an ActionDef and an ActionUse. Action use is the concrete action that is tied to a step and
 * ActionDef is the definition of the action: how to perform it.
 *
 * @param <K> see {@link Workflow} for documentation
 * @param <V> see {@link Workflow} for documentation
 * @param <R> see {@link Workflow} for documentation
 * @param <T> see {@link Workflow} for documentation
 * @param <C> see {@link Workflow} for documentation
 */
class ActionDef<K, V, R, T, C> extends Named<R> {

    Parameters<K, V> parameters;
    Functions.Condition<K, V, R, T, C> condition;
    Functions.Pre<K, V, R, T, C> pre;
    Functions.Validator<K, V, R, T, C> validator;
    Functions.Post<K, V, R, T, C> post;

    @Override
    public String toString() {
        return "ActionDef[" + getName() + "]";
    }
}
