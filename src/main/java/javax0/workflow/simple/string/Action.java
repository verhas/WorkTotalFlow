package javax0.workflow.simple.string;

import javax0.workflow.Functions;
import javax0.workflow.Parameters;
import javax0.workflow.Step;

/**
 * @author Peter Verhas
 */
public class Action<T> extends javax0.workflow.simple.Action<String,String,String,T> {
    public Action(Step<String,String,String,T> step,
                  Parameters parameters,
                  Functions.Pre pre,
                  Functions.Condition condition,
                  Functions.Pre preFunction,
                  Functions.Validator validator,
                  Functions.Post post) {
        super(step, parameters, pre, condition, preFunction, validator, post);
    }
}
