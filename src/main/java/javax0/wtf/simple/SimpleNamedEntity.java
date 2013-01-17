/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax0.wtf.simple;

import javax0.wtf.NamedEntity;

/**
 *
 * @author verhas
 */
public abstract class SimpleNamedEntity implements NamedEntity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
