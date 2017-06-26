package javax0.workflow.simple.string;

import javax0.workflow.Step;

import java.util.Collection;

/**
 * @author Peter Verhas
 */
public class Result<T> extends javax0.workflow.simple.Result<String,String,String,T> {

    public Result(Collection<Step<String,String,String,T>> steps) {
        super(steps);
    }

}
