package javax0.workflow.simple;

import javax0.workflow.Result;
import javax0.workflow.Step;

import java.util.Collection;

/**
 * @author Peter Verhas
 */
public class ResultImpl<K, V, R, T> implements Result<K, V, R, T> {

    private final Collection<Step<K, V, R, T>> steps;

    public ResultImpl(Collection<Step<K, V, R, T>> steps) {
        this.steps = steps;
    }

    @Override
    public Collection<Step<K, V, R, T>> getSteps() {
        return steps;
    }

}
