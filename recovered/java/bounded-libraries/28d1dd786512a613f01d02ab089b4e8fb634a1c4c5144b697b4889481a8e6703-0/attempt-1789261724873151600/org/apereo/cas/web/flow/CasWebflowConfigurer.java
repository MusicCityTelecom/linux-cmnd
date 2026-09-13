/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.binding.expression.Expression
 *  org.springframework.core.Ordered
 *  org.springframework.webflow.action.EvaluateAction
 *  org.springframework.webflow.action.RenderAction
 *  org.springframework.webflow.action.SetAction
 *  org.springframework.webflow.definition.StateDefinition
 *  org.springframework.webflow.definition.registry.FlowDefinitionRegistry
 *  org.springframework.webflow.engine.ActionState
 *  org.springframework.webflow.engine.DecisionState
 *  org.springframework.webflow.engine.EndState
 *  org.springframework.webflow.engine.Flow
 *  org.springframework.webflow.engine.FlowVariable
 *  org.springframework.webflow.engine.SubflowState
 *  org.springframework.webflow.engine.Transition
 *  org.springframework.webflow.engine.TransitionCriteria
 *  org.springframework.webflow.engine.TransitionableState
 *  org.springframework.webflow.engine.ViewState
 *  org.springframework.webflow.engine.builder.BinderConfiguration
 *  org.springframework.webflow.execution.Action
 *  org.springframework.webflow.execution.ViewFactory
 */
package org.apereo.cas.web.flow;

import java.util.List;
import java.util.Map;
import org.springframework.binding.expression.Expression;
import org.springframework.core.Ordered;
import org.springframework.webflow.action.EvaluateAction;
import org.springframework.webflow.action.RenderAction;
import org.springframework.webflow.action.SetAction;
import org.springframework.webflow.definition.StateDefinition;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.ActionState;
import org.springframework.webflow.engine.DecisionState;
import org.springframework.webflow.engine.EndState;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.FlowVariable;
import org.springframework.webflow.engine.SubflowState;
import org.springframework.webflow.engine.Transition;
import org.springframework.webflow.engine.TransitionCriteria;
import org.springframework.webflow.engine.TransitionableState;
import org.springframework.webflow.engine.ViewState;
import org.springframework.webflow.engine.builder.BinderConfiguration;
import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.ViewFactory;

public interface CasWebflowConfigurer
extends Ordered {
    public static final String FLOW_ID_DELEGATION_REDIRECT = "clientredirect";
    public static final String FLOW_ID_PASSWORD_RESET = "pswdreset";
    public static final String FLOW_ID_ACCOUNT = "account";
    public static final String FLOW_ID_LOGIN = "login";
    public static final String FLOW_ID_LOGOUT = "logout";

    public void initialize();

    public Flow getLoginFlow();

    public Flow getLogoutFlow();

    public TransitionableState getStartState(Flow var1);

    public Transition createTransition(String var1, String var2);

    public Transition createTransition(String var1, String var2, Action ... var3);

    public Transition createTransition(String var1, TransitionableState var2);

    public Transition createTransition(Expression var1, String var2, Action ... var3);

    public Transition createTransition(String var1);

    public RenderAction createRenderAction(String ... var1);

    public SetAction createSetAction(String var1, String var2);

    public EvaluateAction createEvaluateAction(String var1);

    public ActionState createActionState(Flow var1, String var2);

    public ActionState createActionState(Flow var1, String var2, String ... var3);

    public ActionState createActionState(Flow var1, String var2, Action ... var3);

    public ActionState createActionState(Flow var1, String var2, Action var3);

    public DecisionState createDecisionState(Flow var1, String var2, String var3, String var4, String var5);

    public void setStartState(Flow var1, String var2);

    public void setStartState(Flow var1, TransitionableState var2);

    public EndState createEndState(Flow var1, String var2);

    public EndState createEndState(Flow var1, String var2, String var3, boolean var4);

    public EndState createEndState(Flow var1, String var2, String var3);

    public EndState createEndState(Flow var1, String var2, Expression var3);

    public EndState createEndState(Flow var1, String var2, ViewFactory var3);

    public ViewState createViewState(Flow var1, String var2, Expression var3, BinderConfiguration var4);

    public ViewState createViewState(Flow var1, String var2, ViewFactory var3);

    public ViewState createViewState(Flow var1, String var2, String var3);

    public ViewState createViewState(Flow var1, String var2, String var3, BinderConfiguration var4);

    public SubflowState createSubflowState(Flow var1, String var2, String var3, Action var4);

    public SubflowState createSubflowState(Flow var1, String var2, String var3);

    public Flow buildFlow(String var1);

    default public String getName() {
        return this.getClass().getSimpleName();
    }

    public void createStateDefaultTransition(TransitionableState var1, String var2);

    public void createStateDefaultTransition(TransitionableState var1, StateDefinition var2);

    public Transition createTransitionForState(TransitionableState var1, String var2, String var3, Map<String, Object> var4);

    public Transition createTransitionForState(TransitionableState var1, String var2);

    public Transition createTransitionForState(TransitionableState var1, String var2, String var3);

    public Transition createTransitionForState(TransitionableState var1, String var2, String var3, Map<String, Object> var4, Action ... var5);

    public Transition createTransitionForState(TransitionableState var1, String var2, String var3, Action ... var4);

    public Transition createTransitionForState(Flow var1, String var2, String var3, String var4);

    public Transition createTransitionForState(TransitionableState var1, String var2, String var3, boolean var4, Map<String, Object> var5, Action ... var6);

    public Transition createTransitionForState(TransitionableState var1, String var2, String var3, boolean var4, Map<String, Object> var5);

    public Transition createTransitionForState(TransitionableState var1, String var2, String var3, boolean var4);

    public Expression createExpression(String var1, Class var2);

    public Expression createExpression(String var1);

    public boolean containsFlowState(Flow var1, String var2);

    public boolean containsSubflowState(Flow var1, String var2);

    public boolean containsTransition(TransitionableState var1, String var2);

    public FlowVariable createFlowVariable(Flow var1, String var2, Class var3);

    public BinderConfiguration createStateBinderConfiguration(Map<String, Map<String, String>> var1);

    public BinderConfiguration createStateBinderConfiguration(List<String> var1);

    public void createStateModelBinding(TransitionableState var1, String var2, Class var3);

    public BinderConfiguration getViewStateBinderConfiguration(ViewState var1);

    public List<TransitionCriteria> getTransitionExecutionCriteriaChainForTransition(Transition var1);

    public <T> T getState(Flow var1, String var2, Class<T> var3);

    public TransitionableState getState(Flow var1, String var2);

    public Flow getFlow(String var1);

    public Flow getFlow(FlowDefinitionRegistry var1, String var2);
}

