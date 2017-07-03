package javax0.workflow.simple;

import javax0.workflow.Action;
import javax0.workflow.Result;
import javax0.workflow.Step;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

class ResultSupplier<K, V, R, T, C> {
  private final Map<SupplierTuple<K, V, R, T, C>, Result<K, V, R, T, C>> stepMap = new HashMap<>();

  private SupplierTuple<K, V, R, T, C> key(Step<K, V, R, T, C> step, ActionDef<K, V, R, T, C> actionDef, R name) {
    return SupplierTuple.tuple(step, actionDef, name);
  }

  Supplier<Result<K, V, R, T, C>> supplier(Action<K, V, R, T, C> action, R name) {
    return () -> stepMap.get(key(action.getStep(), ((ActionUse<K, V, R, T, C>)action).actionDef, name));
  }

  void put(Step<K, V, R, T, C> step, ActionDef<K, V, R, T, C> actionDef, R name, Result<K, V, R, T, C> result) {
    SupplierTuple<K, V, R, T, C> complexKey = key(step, actionDef, name);
    if (stepMap.containsKey(complexKey)) {
      throw new IllegalArgumentException(
          String.format("Mapping (s: '%s', a:'%s',r:'%s') -> supplier already exists.", step, actionDef, name));
    }
    stepMap.put(complexKey, result);
  }
}
