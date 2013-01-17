package com.verhas.workflow;

/**
 * Action, Result and Step interfaces extend this interface because they
 * define entites that have names. To have a name the class has to implement a
 * method to get access to the name.
 *
 * @author Peter Verhas
 */
public interface NamedEntity {

    /**
     * Get access to the name of the entity.
     *
     * @return the name of the entity.
     */
    public String getName();

}
