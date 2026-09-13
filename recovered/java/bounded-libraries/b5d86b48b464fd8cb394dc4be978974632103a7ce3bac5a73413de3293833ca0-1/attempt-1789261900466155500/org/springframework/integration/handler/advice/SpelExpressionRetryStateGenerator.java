/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.classify.Classifier
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 *  org.springframework.expression.spel.support.StandardEvaluationContext
 *  org.springframework.messaging.Message
 *  org.springframework.retry.RetryState
 *  org.springframework.retry.support.DefaultRetryState
 *  org.springframework.util.Assert
 */
package org.springframework.integration.handler.advice;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.classify.Classifier;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.integration.expression.ExpressionUtils;
import org.springframework.integration.handler.advice.RetryStateGenerator;
import org.springframework.messaging.Message;
import org.springframework.retry.RetryState;
import org.springframework.retry.support.DefaultRetryState;
import org.springframework.util.Assert;

public class SpelExpressionRetryStateGenerator
implements RetryStateGenerator,
BeanFactoryAware {
    private volatile StandardEvaluationContext evaluationContext;
    private final Expression keyExpression;
    private final Expression forceRefreshExpression;
    private volatile Classifier<? super Throwable, Boolean> classifier;

    public SpelExpressionRetryStateGenerator(String keyExpression) {
        this(keyExpression, null);
    }

    public SpelExpressionRetryStateGenerator(String keyExpression, String forceRefreshExpression) {
        Assert.notNull((Object)keyExpression, (String)"keyExpression must not be null");
        this.keyExpression = new SpelExpressionParser().parseExpression(keyExpression);
        this.evaluationContext = ExpressionUtils.createStandardEvaluationContext();
        this.forceRefreshExpression = forceRefreshExpression == null ? null : new SpelExpressionParser().parseExpression(forceRefreshExpression);
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.evaluationContext = ExpressionUtils.createStandardEvaluationContext(beanFactory);
    }

    public void setClassifier(Classifier<? super Throwable, Boolean> classifier) {
        this.classifier = classifier;
    }

    @Override
    public RetryState determineRetryState(Message<?> message) {
        Boolean forceRefresh = this.forceRefreshExpression == null ? Boolean.FALSE : (Boolean)this.forceRefreshExpression.getValue((EvaluationContext)this.evaluationContext, message, Boolean.class);
        return new DefaultRetryState(this.keyExpression.getValue((EvaluationContext)this.evaluationContext, message), forceRefresh == null ? false : forceRefresh, this.classifier);
    }
}

