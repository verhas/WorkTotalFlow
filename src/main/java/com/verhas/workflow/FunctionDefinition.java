package com.verhas.workflow;

/**
 *
 * @author Peter Verhas
 */
public interface FunctionDefinition<FunctionType extends Function> extends Definition {

    public FunctionType getFunction() throws ClassNotFoundException,
                                             InstantiationException,
                                             IllegalAccessException;
}
