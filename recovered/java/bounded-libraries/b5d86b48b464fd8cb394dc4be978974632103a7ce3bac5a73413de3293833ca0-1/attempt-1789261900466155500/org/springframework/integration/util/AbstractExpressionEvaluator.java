/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.context.expression.BeanFactoryResolver
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.core.log.LogAccessor
 *  org.springframework.expression.BeanResolver
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.EvaluationException
 *  org.springframework.expression.Expression
 *  org.springframework.expression.ExpressionParser
 *  org.springframework.expression.TypeConverter
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 *  org.springframework.expression.spel.support.StandardEvaluationContext
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.util;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.log.LogAccessor;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.TypeConverter;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.integration.expression.ExpressionUtils;
import org.springframework.integration.support.DefaultMessageBuilderFactory;
import org.springframework.integration.support.MessageBuilderFactory;
import org.springframework.integration.support.utils.IntegrationUtils;
import org.springframework.integration.util.BeanFactoryTypeConverter;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;

public abstract class AbstractExpressionEvaluator
implements BeanFactoryAware,
InitializingBean {
    protected final LogAccessor logger = new LogAccessor(this.getClass());
    protected static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();
    private final BeanFactoryTypeConverter typeConverter = new BeanFactoryTypeConverter();
    private volatile StandardEvaluationContext evaluationContext;
    private volatile BeanFactory beanFactory;
    private volatile MessageBuilderFactory messageBuilderFactory = new DefaultMessageBuilderFactory();

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        this.typeConverter.setBeanFactory(beanFactory);
        if (this.evaluationContext != null && this.evaluationContext.getBeanResolver() == null) {
            this.evaluationContext.setBeanResolver((BeanResolver)new BeanFactoryResolver(beanFactory));
        }
    }

    protected BeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    public void setConversionService(ConversionService conversionService) {
        if (conversionService != null) {
            this.typeConverter.setConversionService(conversionService);
        }
    }

    protected MessageBuilderFactory getMessageBuilderFactory() {
        return this.messageBuilderFactory;
    }

    public final void afterPropertiesSet() {
        this.getEvaluationContext();
        if (this.beanFactory != null) {
            this.messageBuilderFactory = IntegrationUtils.getMessageBuilderFactory(this.beanFactory);
        }
        this.onInit();
    }

    protected StandardEvaluationContext getEvaluationContext() {
        return this.getEvaluationContext(true);
    }

    protected final StandardEvaluationContext getEvaluationContext(boolean beanFactoryRequired) {
        if (this.evaluationContext == null) {
            ConversionService conversionService;
            this.evaluationContext = this.beanFactory == null && !beanFactoryRequired ? ExpressionUtils.createStandardEvaluationContext() : ExpressionUtils.createStandardEvaluationContext(this.beanFactory);
            this.evaluationContext.setTypeConverter((TypeConverter)this.typeConverter);
            if (this.beanFactory != null && (conversionService = IntegrationUtils.getConversionService(this.beanFactory)) != null) {
                this.typeConverter.setConversionService(conversionService);
            }
        }
        return this.evaluationContext;
    }

    @Nullable
    protected <T> T evaluateExpression(Expression expression, Message<?> message, @Nullable Class<T> expectedType) {
        try {
            return this.evaluateExpression(expression, (Object)message, expectedType);
        }
        catch (Exception ex) {
            this.logger.debug((Throwable)ex, (CharSequence)"SpEL Expression evaluation failed with Exception.");
            Throwable cause = null;
            if (ex instanceof EvaluationException) {
                cause = ex.getCause();
            }
            throw IntegrationUtils.wrapInHandlingExceptionIfNecessary(message, () -> "Expression evaluation failed: " + expression.getExpressionString(), cause == null ? ex : cause);
        }
    }

    @Nullable
    protected Object evaluateExpression(String expression, Object input) {
        return this.evaluateExpression(expression, input, null);
    }

    @Nullable
    protected <T> T evaluateExpression(String expression, Object input, @Nullable Class<T> expectedType) {
        return (T)EXPRESSION_PARSER.parseExpression(expression).getValue((EvaluationContext)this.getEvaluationContext(), input, expectedType);
    }

    @Nullable
    protected Object evaluateExpression(Expression expression, Object input) {
        return this.evaluateExpression(expression, input, null);
    }

    @Nullable
    protected <T> T evaluateExpression(Expression expression, @Nullable Class<T> expectedType) {
        return (T)expression.getValue((EvaluationContext)this.getEvaluationContext(), expectedType);
    }

    @Nullable
    protected Object evaluateExpression(Expression expression) {
        return expression.getValue((EvaluationContext)this.getEvaluationContext());
    }

    @Nullable
    protected <T> T evaluateExpression(Expression expression, Object input, @Nullable Class<T> expectedType) {
        return (T)expression.getValue((EvaluationContext)this.getEvaluationContext(), input, expectedType);
    }

    protected void onInit() {
    }
}

