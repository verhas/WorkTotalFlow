/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.verhas.workflow.simple;

import com.verhas.workflow.NamedEntity;

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
