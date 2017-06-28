package javax0.workflow.simple.builder;

import java.util.*;
import java.util.stream.Collectors;

class ResultMap<K, V, R, T> {
    private final Map<Tuple<K, V, R, T>, Collection<R>> resultMap = new HashMap<>();
    private final ActionBlds<K, V, R, T> actions;
    private final ResultBlds<R> results;
    private final StepBlds<K, V, R, T> steps;

    ResultMap(ActionBlds<K, V, R, T> actions, ResultBlds<R> results, StepBlds<K, V, R, T> steps) {
        this.actions = actions;
        this.results = results;
        this.steps = steps;
    }

    private Tuple<K, V, R, T> key(R action, R result) {
        ActionBld<K, V, R, T> actionBld = actions.get(action);
        ResultBld<R> resultBld = results.get(result);
        return Tuple.tuple(actionBld, resultBld);
    }


    Set<Tuple<K,V,R,T>> keySet(){
        return resultMap.keySet();
    }

    boolean contains(R action, R result) {
        return resultMap.containsKey(key(action, result));
    }

    Collection<R> get(R action, R result) {
        return resultMap.get(key(action, result));
    }

    void put(R action, R result, R... steps) {
        Tuple<K, V, R, T> complexKey = key(action, result);
        if (resultMap.containsKey(complexKey)) {
            throw new IllegalArgumentException(
                    String.format("Mapping (a:'%s',r:'%s') -> s:['%s'] already exists.", action, result,
                            String.join(",", Arrays.stream(steps).map(Objects::toString).collect(Collectors.toList()))));
        }
        resultMap.put(complexKey, Arrays.asList(steps));
    }
}
