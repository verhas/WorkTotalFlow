package com.verhas.workflow.functions;

import com.verhas.workflow.Action;
import com.verhas.workflow.PostFunction;
import com.verhas.workflow.Result;

import java.util.Map;

public class OkPostFunction implements PostFunction {

    public Result postFunction(Action action, Object transientObject,
                               Map<String, String> userInput) {
        return action.getResult("OK");
    }
}
