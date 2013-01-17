package com.verhas.workflow;

import java.util.Map;

/**
 *
 * @author Peter Verhas
 */
public class SamplePostFunction implements PostFunction {

  public Result postFunction(Action action,
          Object transientObject,
          Map<String, String> userInput) {
    return action.getResult("OK");
  }
}
