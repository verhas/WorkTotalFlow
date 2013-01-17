package com.verhas.workflow;

import java.util.Map;

import javax0.wtf.Action;
import javax0.wtf.Validator;

/**
 * A sample validator that allways return true.
 * @author Peter Verhas
 */
public class SampleValidator implements Validator {

  public boolean valid(Action action, Object transientObject, Map<String, String> userInput) {
    return true;
  }
}
