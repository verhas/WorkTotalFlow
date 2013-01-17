package com.verhas.workflow.functions;

import com.verhas.workflow.Action;
import com.verhas.workflow.Condition;

public class TrueCondition implements Condition {

    public boolean canBePerformed(Action action) {
        return true;
    }
}
