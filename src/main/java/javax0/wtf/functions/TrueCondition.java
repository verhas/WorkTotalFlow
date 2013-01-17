package javax0.wtf.functions;

import javax0.wtf.Action;
import javax0.wtf.Condition;

public class TrueCondition implements Condition {

    public boolean canBePerformed(Action action) {
        return true;
    }
}
