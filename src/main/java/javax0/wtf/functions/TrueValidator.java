package javax0.wtf.functions;


import java.util.Map;

import javax0.wtf.Action;
import javax0.wtf.Validator;

public class TrueValidator implements Validator {

    public boolean valid(Action action, Object transientObject,
                         Map<String, String> userInput) {
        return true;
    }
}
