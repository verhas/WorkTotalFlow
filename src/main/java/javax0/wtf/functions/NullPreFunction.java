package javax0.wtf.functions;

import javax0.wtf.Action;
import javax0.wtf.PreFunction;

public class NullPreFunction implements PreFunction {

    public Object preFunction(Action action) {
        return null;
    }

}
