package javax0.wtf.simple;

import javax0.wtf.Function;
import javax0.wtf.FunctionDefinition;

/**
 *
 * @author Peter Verhas
 */
public abstract class SimpleFunctionDefinition<T extends Function>
        extends SimpleNamedEntity
        implements FunctionDefinition<T> {

    private String className = null;

    public void setClassName(final String className) {
        this.className = className;
    }
    private SimpleWorkflowEngine<T> engine;

    public SimpleWorkflowEngine<T> getEngine() {
        return engine;
    }

    public void setEngine(final SimpleWorkflowEngine<T> engine) {
        this.engine = engine;
    }

    @SuppressWarnings("unchecked")
	public T getFunction() throws ClassNotFoundException,
                                  InstantiationException,
                                  IllegalAccessException {
        T function = null;
        if (className != null && !engine.containsFunction(className)) {
            function = (T)Class.forName(className).newInstance();
            engine.setFunction(className, function);
        }
        function = (T)engine.getFunction(className);
        return function;
    }
}
