package javax0.workflow.simple;

import javax0.workflow.Result;
import javax0.workflow.Step;
import javax0.workflow.Workflow;

import java.util.Collection;
import java.util.HashSet;

/**
 * Simple implementation of the interface {@link Result}
 *
 * @param <K> see {@link Workflow} for documentation
 * @param <V> see {@link Workflow} for documentation
 * @param <R> see {@link Workflow} for documentation
 * @param <T> see {@link Workflow} for documentation
 * @param <C> see {@link Workflow} for documentation
 */
public class ResultImpl<K, V, R, T, C> extends Named<R> implements Result<K, V, R, T, C> {

    private final Collection<Step<K, V, R, T, C>> steps = new HashSet<>();

    @Override
    public Collection<Step<K, V, R, T, C>> getSteps() {
        return steps;
    }

}
