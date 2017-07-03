package javax0.workflow.simple;

import javax0.workflow.Result;
import javax0.workflow.Step;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Peter Verhas
 */
public class ResultImpl<K, V, R, T, C> extends Named<R> implements Result<K, V, R, T, C> {

    private final Collection<Step<K, V, R, T, C>> steps = new HashSet<>();

    @Override
    public Collection<Step<K, V, R, T, C>> getSteps() {
        return steps;
    }

}
