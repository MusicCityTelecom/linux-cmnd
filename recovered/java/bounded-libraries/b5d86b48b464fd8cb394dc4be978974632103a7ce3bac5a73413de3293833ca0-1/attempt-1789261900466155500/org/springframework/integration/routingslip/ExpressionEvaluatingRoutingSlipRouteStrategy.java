/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.expression.ExpressionParser
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.routingslip;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.expression.ExpressionUtils;
import org.springframework.integration.routingslip.RoutingSlipRouteStrategy;
import org.springframework.messaging.Message;

public class ExpressionEvaluatingRoutingSlipRouteStrategy
implements RoutingSlipRouteStrategy,
BeanFactoryAware,
InitializingBean {
    private static final ExpressionParser PARSER = new SpelExpressionParser();
    private final Expression expression;
    private EvaluationContext evaluationContext;
    private BeanFactory beanFactory;

    public ExpressionEvaluatingRoutingSlipRouteStrategy(String expression) {
        this(PARSER.parseExpression(expression));
    }

    public ExpressionEvaluatingRoutingSlipRouteStrategy(Expression expression) {
        this.expression = expression;
    }

    public void setIntegrationEvaluationContext(EvaluationContext evaluationContext) {
        this.evaluationContext = evaluationContext;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void afterPropertiesSet() {
        if (this.evaluationContext == null) {
            this.evaluationContext = ExpressionUtils.createStandardEvaluationContext(this.beanFactory);
        }
    }

    @Override
    public Object getNextPath(Message<?> requestMessage, Object reply) {
        return this.expression.getValue(this.evaluationContext, (Object)new RequestAndReply(requestMessage, reply));
    }

    public String toString() {
        return "ExpressionEvaluatingRoutingSlipRouteStrategy for: [" + this.expression.getExpressionString() + "]";
    }

    public static class RequestAndReply {
        private final Message<?> request;
        private final Object reply;

        RequestAndReply(Message<?> request, Object reply) {
            this.request = request;
            this.reply = reply;
        }

        public Message<?> getRequest() {
            return this.request;
        }

        public Object getReply() {
            return this.reply;
        }
    }
}

