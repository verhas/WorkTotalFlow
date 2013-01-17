package com.verhas.workflow;

/**
 * All class that has a definition counter part should implement this interface
 * @author Istvan Verhas
 */
public interface HasDefinition<T extends Definition> {

    /**
     * Get the definition
     * @return the specific definition
     */
    public T getDefinition();
}
