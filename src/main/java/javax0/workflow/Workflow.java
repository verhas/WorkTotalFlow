package javax0.workflow;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * This interface defines the methods that are available to manage the workflow
 * of an item.
 *
 * @param <K> in a workflow parameters are presented as Parameters that have K,V pairs. These parameters are not used
 *           by the workflow itself but are available to the different {@link Functions}. These parameters are
 *           usually defined in some configuration that the actual Workflow implementation reads.
 *           This is the type parameter for the keys used in Parameters.
 * @param <V> This is the type parameter for the values used in Parameters
 * @param <I> The object type that contains the user input during the workflow.
 * @param <R> steps, actions and results in a workflow have identifier. This helps the higher level handling of the workflow
 *            though such ID is not needed for the internal working and execution of workflow. R is the type parameter
 *            for such an ID. In most of the practical applications we expect that this type parameter will be simple
 *            java.lang.String
 * @param <T> type for the temporary object returned by the pre function and available for the post function. The
 *            object of this type is not used by the workflow, this is simply passed from one return value to the other.
 * @param <C> the type parameter for the context. This context object can be set and stored on the workflow level. Every
 *            workflow can have exactly one context. The context is not used by the workflow engine, it is there to be
 *            available for pre, post functions, conditions and validators.
 */
public interface Workflow<K, V, I, R, T, C> {

    /**
     * <p>
     * Get a result supplier based on the name of the result and the action this result originates from.
     * </p>
     * <p>
     * Post functions are NOT supposed to call this method. They are supposed to call the method with the
     * same name defined in the {@link Action} interface. The default implemenation of that method calls this
     * method providing a unified result lookup for the whole workflow. On the other hand calling the method
     * defined in {@link Action} also works when the actual implementation of the {@link Action} overrides the
     * default implementation and provide different result object / name mapping for different actions.
     * </p>
     * <p>
     * The actual action is needed as an argument because there can be several result objects
     * having the same name in a workflow. A very typical name for a result can be "OK" or
     * "TRANSACTION OK" and there can be many steps that have results with this name. It would be
     * burdensome to have results with name "action 1 is OK", "action 2 is OK" or something similar
     * encoding the name of the action into the name of the result.
     * </p>
     *
     * @param action that is executing at the time this method is called.
     * @param key    the name/id of the result
     * @return a supplier that will give the result
     */
    Supplier<Result<K, V, I, R, T, C>> result(Action<K, V, I, R, T, C> action, R key);

    /**
     * Get the steps that the workflow is currently in.
     */
    Collection<Step<K, V, I, R, T, C>> getSteps();

    /**
     * Set the steps that the workflow is currently in.
     */
    void setSteps(Collection<Step<K, V, I, R, T, C>> steps);

    /**
     * Get the context object that this workflow holds.
     *
     * @return the context object
     */
    C getContext();

    /**
     * Set the workflow's context object.
     *
     * @param context the context object
     */
    void setContext(C context);

}
