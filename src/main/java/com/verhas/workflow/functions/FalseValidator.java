package com.verhas.workflow.functions;

import com.verhas.workflow.Action;
import com.verhas.workflow.Validator;

import java.util.Map;

public class FalseValidator implements Validator {

    public boolean valid(Action action, Object transientObject,
                         Map<String, String> userInput) {
        return false;
    }
}
