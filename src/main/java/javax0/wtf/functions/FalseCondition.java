package javax0.wtf.functions;

import javax0.wtf.Action;
import javax0.wtf.Condition;

public class FalseCondition implements Condition {

    public boolean canBePerformed(Action action) {
        return false;
    }
}
