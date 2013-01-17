package com.verhas.workflow.functions;

import com.verhas.workflow.Action;
import com.verhas.workflow.PreFunction;

public class NullPreFunction implements PreFunction {

    public Object preFunction(Action action) {
        return null;
    }

}
