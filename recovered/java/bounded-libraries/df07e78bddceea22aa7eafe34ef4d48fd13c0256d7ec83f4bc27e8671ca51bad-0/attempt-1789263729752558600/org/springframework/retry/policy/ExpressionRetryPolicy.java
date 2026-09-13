/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.context.expression.BeanFactoryResolver
 *  org.springframework.expression.BeanResolver
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.expression.ParserContext
 *  org.springframework.expression.common.TemplateParserContext
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 *  org.springframework.expression.spel.support.StandardEvaluationContext
 *  org.springframework.util.Assert
 */
package org.springframework.retry.policy;

import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.util.Assert;

public class ExpressionRetryPolicy
extends SimpleRetryPolicy
implements BeanFactoryAware {
    private static final Log logger = LogFactory.getLog(ExpressionRetryPolicy.class);
    private static final TemplateParserContext PARSER_CONTEXT = new TemplateParserContext();
    private final Expression expression;
    private final StandardEvaluationContext evaluationContext = new StandardEvaluationContext();

    public ExpressionRetryPolicy(Expression expression) {
        Assert.notNull((Object)expression, (String)"'expression' cannot be null");
        this.expression = expression;
    }

    public ExpressionRetryPolicy(String expressionString) {
        Assert.notNull((Object)expressionString, (String)"'expressionString' cannot be null");
        this.expression = ExpressionRetryPolicy.getExpression(expressionString);
    }

    public ExpressionRetryPolicy(int maxAttempts, Map<Class<? extends Throwable>, Boolean> retryableExceptions, boolean traverseCauses, Expression expression) {
        super(maxAttempts, retryableExceptions, traverseCauses);
        Assert.notNull((Object)expression, (String)"'expression' cannot be null");
        this.expression = expression;
    }

    public ExpressionRetryPolicy(int maxAttempts, Map<Class<? extends Throwable>, Boolean> retryableExceptions, boolean traverseCauses, String expressionString, boolean defaultValue) {
        super(maxAttempts, retryableExceptions, traverseCauses, defaultValue);
        Assert.notNull((Object)expressionString, (String)"'expressionString' cannot be null");
        this.expression = ExpressionRetryPolicy.getExpression(expressionString);
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.evaluationContext.setBeanResolver((BeanResolver)new BeanFactoryResolver(beanFactory));
    }

    public ExpressionRetryPolicy withBeanFactory(BeanFactory beanFactory) {
        this.setBeanFactory(beanFactory);
        return this;
    }

    @Override
    public boolean canRetry(RetryContext context) {
        Throwable lastThrowable = context.getLastThrowable();
        if (lastThrowable == null) {
            return super.canRetry(context);
        }
        return super.canRetry(context) && (Boolean)this.expression.getValue((EvaluationContext)this.evaluationContext, (Object)lastThrowable, Boolean.class) != false;
    }

    private static Expression getExpression(String expression) {
        if (ExpressionRetryPolicy.isTemplate(expression)) {
            logger.warn((Object)"#{...} syntax is not required for this run-time expression and is deprecated in favor of a simple expression string");
            return new SpelExpressionParser().parseExpression(expression, (ParserContext)PARSER_CONTEXT);
        }
        return new SpelExpressionParser().parseExpression(expression);
    }

    public static boolean isTemplate(String expression) {
        return expression.contains(PARSER_CONTEXT.getExpressionPrefix()) && expression.contains(PARSER_CONTEXT.getExpressionSuffix());
    }
}

