package javax0.workflow.simple.string;


import javax0.workflow.Result;

import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * @author Peter Verhas
 */
public class Workflow<T> extends javax0.workflow.simple.Workflow<String,String,String,T> {

    public Workflow(
            BiFunction<javax0.workflow.Action<String,String,String,T>,
                       String,
            Supplier<Result<String,String,String,T>>> resultFactory) {
        super(resultFactory);
    }


}
