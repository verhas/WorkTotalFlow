package com.verhas.workflow;

import java.util.Map;

import javax0.wtf.Action;
import javax0.wtf.PostFunction;
import javax0.wtf.Result;

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
