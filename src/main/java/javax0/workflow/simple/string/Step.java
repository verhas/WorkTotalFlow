package javax0.workflow.simple.string;


import java.util.Collection;

/**
 * @author Peter Verhas
 */
public class Step<T> extends javax0.workflow.simple.Step<String,String,String,T> {


    public Step(javax0.workflow.Workflow<String,String,String,T> workflow, Collection<javax0.workflow.Action<String,String,String,T>> actions) {
        super(workflow,actions);
    }
}
