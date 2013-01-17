package com.verhas.workflow.simple;

import com.verhas.workflow.Function;
import com.verhas.workflow.WorkflowDefinition;
import com.verhas.workflow.WorkflowEngine;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Peter Verhas
 */
public class SimpleWorkflowEngine<T extends Function> implements WorkflowEngine {

    public void register(WorkflowDefinition workflowDefinition) {
        SimpleWorkflowDefinition simpleWorkflowDefinition =
            (SimpleWorkflowDefinition)workflowDefinition;
        simpleWorkflowDefinition.setEngine(this);
    }
    private Map<String, T> functionMap = new HashMap<String, T>();

    public T getFunction(String name) {
        return functionMap.get(name);
    }

    public boolean containsFunction(String name) {
        return functionMap.containsKey(name);
    }

    public void setFunction(String name, T function) {
        functionMap.put(name, function);
    }
}
