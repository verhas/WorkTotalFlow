package javax0.workflow.simple;

import java.util.*;
import java.util.stream.Collectors;

class ResultMap<K, V, R, T> {
  private final Map<MapTuple<K, V, R, T>, Collection<R>> resultMap = new HashMap<>();
  private final ActionDefs<K, V, R, T> actions;
  private final Results<K,V,R,T> results;
  private final Steps<K, V, R, T> steps;

  ResultMap(ActionDefs<K, V, R, T> actions, Results<K,V,R,T> results, Steps<K, V, R, T> steps) {
    this.actions = actions;
    this.results = results;
    this.steps = steps;
  }

  private MapTuple<K, V, R, T> key(R step, R action, R result) {
    StepImpl<K, V, R, T> stepBld = steps.get(step);
    ActionDef<K, V, R, T> actionDef = actions.get(action);
    ResultImpl<K,V,R,T> resultBld = results.get(result);
    return MapTuple.tuple(stepBld, actionDef, resultBld);
  }


  Set<MapTuple<K, V, R, T>> keySet() {
    return resultMap.keySet();
  }

  boolean contains(R step, R action, R result) {
    return resultMap.containsKey(key(step, action, result));
  }

  Collection<R> get(R step, R action, R result) {
    return resultMap.get(key(step, action, result));
  }

  @SafeVarargs
  final void put(R step, R action, R result, R... steps) {
    MapTuple<K, V, R, T> complexKey = key(step, action, result);
    if (resultMap.containsKey(complexKey)) {
      throw new IllegalArgumentException(
          String.format("Mapping (a:'%s',r:'%s') -> s:['%s'] already exists.", action, result,
              String.join(",", Arrays.stream(steps).map(Objects::toString).collect(Collectors.toList()))));
    }
    resultMap.put(complexKey, Arrays.asList(steps));
  }
}
