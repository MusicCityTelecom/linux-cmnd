/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.security.access.ConfigAttribute
 */
package org.springframework.security.web.access.expression;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.EvaluationContextPostProcessor;

class WebExpressionConfigAttribute
implements ConfigAttribute,
EvaluationContextPostProcessor<FilterInvocation> {
    private final Expression authorizeExpression;
    private final EvaluationContextPostProcessor<FilterInvocation> postProcessor;

    WebExpressionConfigAttribute(Expression authorizeExpression, EvaluationContextPostProcessor<FilterInvocation> postProcessor) {
        this.authorizeExpression = authorizeExpression;
        this.postProcessor = postProcessor;
    }

    Expression getAuthorizeExpression() {
        return this.authorizeExpression;
    }

    @Override
    public EvaluationContext postProcess(EvaluationContext context, FilterInvocation fi) {
        return this.postProcessor != null ? this.postProcessor.postProcess(context, fi) : context;
    }

    public String getAttribute() {
        return null;
    }

    public String toString() {
        return this.authorizeExpression.getExpressionString();
    }
}

