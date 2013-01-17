/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.verhas.workflow.simple;

import com.verhas.workflow.ConditionDefinition;
import com.verhas.workflow.PostFunctionDefinition;
import com.verhas.workflow.PreFunctionDefinition;
import com.verhas.workflow.ValidatorDefinition;

import java.util.Map;

/**
 *
 * @author verhas
 */
public class SimpleActionDeclaration {

    private String name;
    private Map<String, String> parameters;
    private ConditionDefinition conditionDefinition;
    private PreFunctionDefinition preFunctionDefinition;
    private ValidatorDefinition validatorDefinition;
    private PostFunctionDefinition postFunctionDefinition;

    public String getName() {
        return name;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public ConditionDefinition getConditionDefinition() {
        return conditionDefinition;
    }

    public PostFunctionDefinition getPostFunctionDefinition() {
        return postFunctionDefinition;
    }

    public PreFunctionDefinition getPreFunctionDefinition() {
        return preFunctionDefinition;
    }

    public ValidatorDefinition getValidatorDefinition() {
        return validatorDefinition;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setConditionDefinition(ConditionDefinition conditionDefinition) {
        this.conditionDefinition = conditionDefinition;
    }

    public void setPostFunctionDefinition(PostFunctionDefinition postFunctionDefinition) {
        this.postFunctionDefinition = postFunctionDefinition;
    }

    public void setPreFunctionDefinition(PreFunctionDefinition preFunctionDefinition) {
        this.preFunctionDefinition = preFunctionDefinition;
    }

    public void setValidatorDefinition(ValidatorDefinition validatorDefinition) {
        this.validatorDefinition = validatorDefinition;
    }

    @Override
    public String toString() {
        return name;
    }

}
