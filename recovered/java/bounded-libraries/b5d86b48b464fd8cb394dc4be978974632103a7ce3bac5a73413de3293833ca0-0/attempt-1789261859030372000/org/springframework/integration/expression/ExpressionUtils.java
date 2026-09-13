/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.context.expression.BeanFactoryResolver
 *  org.springframework.context.expression.MapAccessor
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.core.io.Resource
 *  org.springframework.expression.BeanResolver
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.expression.ExpressionParser
 *  org.springframework.expression.PropertyAccessor
 *  org.springframework.expression.TypeConverter
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 *  org.springframework.expression.spel.support.DataBindingPropertyAccessor
 *  org.springframework.expression.spel.support.SimpleEvaluationContext
 *  org.springframework.expression.spel.support.SimpleEvaluationContext$Builder
 *  org.springframework.expression.spel.support.StandardEvaluationContext
 *  org.springframework.expression.spel.support.StandardTypeConverter
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 *  org.springframework.util.ResourceUtils
 */
package org.springframework.integration.expression;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.MapAccessor;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.Resource;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.TypeConverter;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.DataBindingPropertyAccessor;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.expression.spel.support.StandardTypeConverter;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.integration.expression.ValueExpression;
import org.springframework.integration.support.utils.IntegrationUtils;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

public final class ExpressionUtils {
    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();
    private static final Log LOGGER = LogFactory.getLog(ExpressionUtils.class);

    private ExpressionUtils() {
    }

    public static StandardEvaluationContext createStandardEvaluationContext() {
        return (StandardEvaluationContext)ExpressionUtils.doCreateContext(null, false);
    }

    public static SimpleEvaluationContext createSimpleEvaluationContext() {
        return (SimpleEvaluationContext)ExpressionUtils.doCreateContext(null, true);
    }

    public static StandardEvaluationContext createStandardEvaluationContext(@Nullable BeanFactory beanFactory) {
        if (beanFactory == null) {
            LOGGER.warn((Object)"Creating EvaluationContext with no beanFactory", (Throwable)new RuntimeException("No beanFactory"));
        }
        return (StandardEvaluationContext)ExpressionUtils.doCreateContext(beanFactory, false);
    }

    public static SimpleEvaluationContext createSimpleEvaluationContext(@Nullable BeanFactory beanFactory) {
        if (beanFactory == null) {
            LOGGER.warn((Object)"Creating EvaluationContext with no beanFactory", (Throwable)new RuntimeException("No beanFactory"));
        }
        return (SimpleEvaluationContext)ExpressionUtils.doCreateContext(beanFactory, true);
    }

    private static EvaluationContext doCreateContext(@Nullable BeanFactory beanFactory, boolean simple) {
        ConversionService conversionService = null;
        SimpleEvaluationContext evaluationContext = null;
        if (beanFactory != null) {
            Object object = evaluationContext = simple ? IntegrationContextUtils.getSimpleEvaluationContext(beanFactory) : IntegrationContextUtils.getEvaluationContext(beanFactory);
        }
        if (evaluationContext == null) {
            if (beanFactory != null) {
                conversionService = IntegrationUtils.getConversionService(beanFactory);
            }
            evaluationContext = ExpressionUtils.createEvaluationContext(conversionService, beanFactory, simple);
        }
        return evaluationContext;
    }

    private static EvaluationContext createEvaluationContext(@Nullable ConversionService conversionService, @Nullable BeanFactory beanFactory, boolean simple) {
        if (simple) {
            SimpleEvaluationContext.Builder ecBuilder = SimpleEvaluationContext.forPropertyAccessors((PropertyAccessor[])new PropertyAccessor[]{new MapAccessor(), DataBindingPropertyAccessor.forReadOnlyAccess()}).withInstanceMethods();
            if (conversionService != null) {
                ecBuilder.withConversionService(conversionService);
            }
            return ecBuilder.build();
        }
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        evaluationContext.addPropertyAccessor((PropertyAccessor)new MapAccessor());
        if (conversionService != null) {
            evaluationContext.setTypeConverter((TypeConverter)new StandardTypeConverter(conversionService));
        }
        if (beanFactory != null) {
            evaluationContext.setBeanResolver((BeanResolver)new BeanFactoryResolver(beanFactory));
        }
        return evaluationContext;
    }

    public static File expressionToFile(Expression expression, EvaluationContext evaluationContext, @Nullable Message<?> message, String propertyName) {
        Object value = message == null ? expression.getValue(evaluationContext) : expression.getValue(evaluationContext, message);
        Assert.state((value != null ? 1 : 0) != 0, () -> String.format("The provided %s expression (%s) must not evaluate to null.", propertyName, expression.getExpressionString()));
        if (value instanceof File) {
            return (File)value;
        }
        if (value instanceof String) {
            String path = (String)value;
            Assert.hasText((String)path, (String)String.format("Unable to resolve %s for the provided Expression '%s'.", propertyName, expression.getExpressionString()));
            try {
                return ResourceUtils.getFile((String)path);
            }
            catch (FileNotFoundException ex) {
                throw new IllegalStateException(String.format("Unable to resolve %s for the provided Expression '%s'.", propertyName, expression.getExpressionString()), ex);
            }
        }
        if (value instanceof Resource) {
            try {
                return ((Resource)value).getFile();
            }
            catch (IOException ex) {
                throw new IllegalStateException(String.format("Unable to resolve %s for the provided Expression '%s'.", propertyName, expression.getExpressionString()), ex);
            }
        }
        throw new IllegalStateException(String.format("The provided %s expression (%s) must evaluate to type java.io.File, String or org.springframework.core.io.Resource, not %s.", propertyName, expression.getExpressionString(), value.getClass().getName()));
    }

    public static Expression intExpression(String expression) {
        try {
            return new ValueExpression<Integer>(Integer.parseInt(expression));
        }
        catch (NumberFormatException numberFormatException) {
            return EXPRESSION_PARSER.parseExpression(expression);
        }
    }

    public static Expression longExpression(String expression) {
        try {
            return new ValueExpression<Long>(Long.parseLong(expression));
        }
        catch (NumberFormatException numberFormatException) {
            return EXPRESSION_PARSER.parseExpression(expression);
        }
    }
}

