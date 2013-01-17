package javax0.wtf.functions;


import java.util.Map;

import javax0.wtf.Action;
import javax0.wtf.PostFunction;
import javax0.wtf.Result;

public class OkPostFunction implements PostFunction {

    public Result postFunction(Action action, Object transientObject,
                               Map<String, String> userInput) {
        return action.getResult("OK");
    }
}
