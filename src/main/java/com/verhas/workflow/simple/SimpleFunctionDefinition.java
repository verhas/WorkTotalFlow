package com.verhas.workflow.simple;

import com.verhas.workflow.Function;
import com.verhas.workflow.FunctionDefinition;

/**
 *
 * @author Peter Verhas
 */
public abstract class SimpleFunctionDefinition<T extends Function>
        extends SimpleNamedEntity
        implements FunctionDefinition<T> {

    private String className = null;

    public void setClassName(String className) {
        this.className = className;
    }
    private SimpleWorkflowEngine engine;

    public SimpleWorkflowEngine getEngine() {
        return engine;
    }

    public void setEngine(SimpleWorkflowEngine engine) {
        this.engine = engine;
    }

    public T getFunction() throws ClassNotFoundException,
                                  InstantiationException,
                                  IllegalAccessException {
        T function = null;
        if (className != null && !engine.containsFunction(className)) {
            function = (T)Class.forName(className).newInstance();
            engine.setFunction(className, function);
        }
        function = (T)engine.getFunction(className);
        return function;
    }
}
