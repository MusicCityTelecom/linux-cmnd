/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.i18n.LocaleContextHolder
 *  org.springframework.core.convert.TypeDescriptor
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.EvaluationException
 *  org.springframework.expression.Expression
 *  org.springframework.util.Assert
 */
package org.springframework.integration.expression;

import java.util.Locale;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.integration.expression.ExpressionSource;
import org.springframework.util.Assert;

public class DynamicExpression
implements Expression {
    private final String key;
    private final ExpressionSource expressionSource;

    public DynamicExpression(String key, ExpressionSource expressionSource) {
        Assert.notNull((Object)key, (String)"key must not be null");
        Assert.notNull((Object)expressionSource, (String)"expressionSource must not be null");
        this.key = key;
        this.expressionSource = expressionSource;
    }

    public Object getValue() throws EvaluationException {
        return this.resolveExpression().getValue();
    }

    public Object getValue(Object rootObject) throws EvaluationException {
        return this.resolveExpression().getValue(rootObject);
    }

    public <T> T getValue(Class<T> desiredResultType) throws EvaluationException {
        return (T)this.resolveExpression().getValue(desiredResultType);
    }

    public <T> T getValue(Object rootObject, Class<T> desiredResultType) throws EvaluationException {
        return (T)this.resolveExpression().getValue(rootObject, desiredResultType);
    }

    public Object getValue(EvaluationContext context) throws EvaluationException {
        return this.resolveExpression().getValue(context);
    }

    public Object getValue(EvaluationContext context, Object rootObject) throws EvaluationException {
        return this.resolveExpression().getValue(context, rootObject);
    }

    public <T> T getValue(EvaluationContext context, Class<T> desiredResultType) throws EvaluationException {
        return (T)this.resolveExpression().getValue(context, desiredResultType);
    }

    public <T> T getValue(EvaluationContext context, Object rootObject, Class<T> desiredResultType) throws EvaluationException {
        return (T)this.resolveExpression().getValue(context, rootObject, desiredResultType);
    }

    public Class<?> getValueType() throws EvaluationException {
        return this.resolveExpression().getValueType();
    }

    public Class<?> getValueType(Object rootObject) throws EvaluationException {
        return this.resolveExpression().getValueType(rootObject);
    }

    public Class<?> getValueType(EvaluationContext context) throws EvaluationException {
        return this.resolveExpression().getValueType(context);
    }

    public Class<?> getValueType(EvaluationContext context, Object rootObject) throws EvaluationException {
        return this.resolveExpression().getValueType(context, rootObject);
    }

    public TypeDescriptor getValueTypeDescriptor() throws EvaluationException {
        return this.resolveExpression().getValueTypeDescriptor();
    }

    public TypeDescriptor getValueTypeDescriptor(Object rootObject) throws EvaluationException {
        return this.resolveExpression().getValueTypeDescriptor(rootObject);
    }

    public TypeDescriptor getValueTypeDescriptor(EvaluationContext context) throws EvaluationException {
        return this.resolveExpression().getValueTypeDescriptor(context);
    }

    public TypeDescriptor getValueTypeDescriptor(EvaluationContext context, Object rootObject) throws EvaluationException {
        return this.resolveExpression().getValueTypeDescriptor(context, rootObject);
    }

    public boolean isWritable(EvaluationContext context) throws EvaluationException {
        return this.resolveExpression().isWritable(context);
    }

    public boolean isWritable(EvaluationContext context, Object rootObject) throws EvaluationException {
        return this.resolveExpression().isWritable(context, rootObject);
    }

    public boolean isWritable(Object rootObject) throws EvaluationException {
        return this.resolveExpression().isWritable(rootObject);
    }

    public void setValue(EvaluationContext context, Object value) throws EvaluationException {
        this.resolveExpression().setValue(context, value);
    }

    public void setValue(Object rootObject, Object value) throws EvaluationException {
        this.resolveExpression().setValue(rootObject, value);
    }

    public void setValue(EvaluationContext context, Object rootObject, Object value) throws EvaluationException {
        this.resolveExpression().setValue(context, rootObject, value);
    }

    public String getExpressionString() {
        return this.resolveExpression().getExpressionString();
    }

    private Expression resolveExpression() {
        Locale locale = LocaleContextHolder.getLocale();
        Expression expression = this.expressionSource.getExpression(this.key, locale);
        Assert.state((expression != null ? 1 : 0) != 0, (String)("Unable to resolve Expression with key '" + this.key + "'"));
        return expression;
    }
}

