/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.util.Assert
 */
package org.springframework.integration.endpoint;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.expression.ExpressionUtils;
import org.springframework.util.Assert;

public abstract class ExpressionMessageProducerSupport
extends MessageProducerSupport {
    private volatile Expression payloadExpression;
    private volatile EvaluationContext evaluationContext;

    public void setPayloadExpression(Expression payloadExpression) {
        this.payloadExpression = payloadExpression;
    }

    public void setPayloadExpressionString(String payloadExpression) {
        Assert.hasText((String)payloadExpression, (String)"'payloadExpression' must not be empty");
        this.payloadExpression = EXPRESSION_PARSER.parseExpression(payloadExpression);
    }

    public void setIntegrationEvaluationContext(EvaluationContext evaluationContext) {
        this.evaluationContext = evaluationContext;
    }

    @Override
    protected void onInit() {
        super.onInit();
        if (this.evaluationContext == null) {
            this.evaluationContext = ExpressionUtils.createStandardEvaluationContext(this.getBeanFactory());
        }
    }

    protected Object evaluatePayloadExpression(Object payload) {
        Object evaluationResult = payload;
        if (this.payloadExpression != null) {
            evaluationResult = this.payloadExpression.getValue(this.evaluationContext, payload);
        }
        return evaluationResult;
    }
}

