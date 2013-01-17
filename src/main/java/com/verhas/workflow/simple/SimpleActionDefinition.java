package com.verhas.workflow.simple;

import com.verhas.workflow.ActionDefinition;
import com.verhas.workflow.ConditionDefinition;
import com.verhas.workflow.PostFunctionDefinition;
import com.verhas.workflow.PreFunctionDefinition;
import com.verhas.workflow.ResultDefinition;
import com.verhas.workflow.StepDefinition;
import com.verhas.workflow.ValidatorDefinition;

import java.util.Map;

/**
 *
 * @author Peter Verhas
 */
public class SimpleActionDefinition implements ActionDefinition {

    private Map<String, String> parameters;
    private ResultDefinition[] resultDefinitions;
    private StepDefinition stepDefinition;
    private SimpleActionDeclaration actionDeclaration;

    public SimpleActionDeclaration getActionDeclaration() {
        return actionDeclaration;
    }

    public void setActionDeclaration(SimpleActionDeclaration actionDeclaration) {
        this.actionDeclaration = actionDeclaration;
    }

    public StepDefinition getStepDefinition() {
        return stepDefinition;
    }

    public void setStepDefinition(StepDefinition stepDefinition) {
        this.stepDefinition = stepDefinition;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getName() {
        return getActionDeclaration().getName();
    }

    public ConditionDefinition getConditionDefinition() {
        return getActionDeclaration().getConditionDefinition();
    }

    public PostFunctionDefinition getPostFunctionDefinition() {
        return getActionDeclaration().getPostFunctionDefinition();
    }

    public PreFunctionDefinition getPreFunctionDefinition() {
        return getActionDeclaration().getPreFunctionDefinition();
    }

    public ResultDefinition[] getResultDefinitions() {
        return resultDefinitions;
    }

    public ValidatorDefinition getValidatorDefinition() {
        return getActionDeclaration().getValidatorDefinition();
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public void setName(String name) {
        getActionDeclaration().setName(name);
    }

    public void setConditionDefinition(ConditionDefinition conditionDefinition) {
        getActionDeclaration().setConditionDefinition(conditionDefinition);
    }

    public void setPostFunctionDefinition(PostFunctionDefinition postFunctionDefinition) {
        getActionDeclaration().setPostFunctionDefinition(postFunctionDefinition);
    }

    public void setPreFunctionDefinition(PreFunctionDefinition preFunctionDefinition) {
        getActionDeclaration().setPreFunctionDefinition(preFunctionDefinition);
    }

    public void setResultDefinitions(ResultDefinition[] resultDefinitions) {
        this.resultDefinitions = resultDefinitions;
    }

    public void setValidatorDefinition(ValidatorDefinition validatorDefinition) {
        getActionDeclaration().setValidatorDefinition(validatorDefinition);
    }

    @Override
    public String toString() {
        return getActionDeclaration().getName();
    }
}
