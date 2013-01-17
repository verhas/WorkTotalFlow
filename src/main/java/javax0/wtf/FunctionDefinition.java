package javax0.wtf;

/**
 *
 * @author Peter Verhas
 */
public interface FunctionDefinition<FunctionType extends Function> extends Definition {

    public FunctionType getFunction() throws ClassNotFoundException,
                                             InstantiationException,
                                             IllegalAccessException;
}
