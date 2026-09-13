/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.convert.TypeDescriptor
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.EvaluationException
 *  org.springframework.expression.Expression
 *  org.springframework.expression.TypedValue
 *  org.springframework.expression.common.ExpressionUtils
 *  org.springframework.expression.spel.support.StandardEvaluationContext
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.integration.expression;

import java.util.function.Function;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.TypedValue;
import org.springframework.expression.common.ExpressionUtils;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class FunctionExpression<S>
implements Expression {
    private final Function<S, ?> function;
    private final EvaluationContext defaultContext = new StandardEvaluationContext();
    private final EvaluationException readOnlyException;

    public FunctionExpression(Function<S, ?> function) {
        Assert.notNull(function, (String)"'function' must not be null.");
        this.function = function;
        this.readOnlyException = new EvaluationException(this.getExpressionString(), "FunctionExpression is a 'read only' Expression implementation");
    }

    @Nullable
    public Object getValue() throws EvaluationException {
        return this.function.apply(null);
    }

    @Nullable
    public Object getValue(@Nullable Object rootObject) throws EvaluationException {
        return this.function.apply(rootObject);
    }

    @Nullable
    public <T> T getValue(@Nullable Class<T> desiredResultType) throws EvaluationException {
        return this.getValue(this.defaultContext, desiredResultType);
    }

    @Nullable
    public <T> T getValue(@Nullable Object rootObject, @Nullable Class<T> desiredResultType) throws EvaluationException {
        return this.getValue(this.defaultContext, rootObject, desiredResultType);
    }

    @Nullable
    public Object getValue(EvaluationContext context) throws EvaluationException {
        return this.getValue(context.getRootObject().getValue());
    }

    @Nullable
    public Object getValue(EvaluationContext context, @Nullable Object rootObject) throws EvaluationException {
        return this.getValue(rootObject);
    }

    @Nullable
    public <T> T getValue(EvaluationContext context, @Nullable Class<T> desiredResultType) throws EvaluationException {
        return (T)ExpressionUtils.convertTypedValue((EvaluationContext)context, (TypedValue)new TypedValue(this.getValue(context)), desiredResultType);
    }

    @Nullable
    public <T> T getValue(EvaluationContext context, @Nullable Object rootObject, @Nullable Class<T> desiredResultType) throws EvaluationException {
        return (T)ExpressionUtils.convertTypedValue((EvaluationContext)context, (TypedValue)new TypedValue(this.getValue(context, rootObject)), desiredResultType);
    }

    public Class<?> getValueType() throws EvaluationException {
        throw this.readOnlyException;
    }

    public Class<?> getValueType(@Nullable Object rootObject) throws EvaluationException {
        throw this.readOnlyException;
    }

    public Class<?> getValueType(EvaluationContext context) throws EvaluationException {
        throw this.readOnlyException;
    }

    public Class<?> getValueType(EvaluationContext context, @Nullable Object rootObject) throws EvaluationException {
        throw this.readOnlyException;
    }

    public TypeDescriptor getValueTypeDescriptor() throws EvaluationException {
        throw this.readOnlyException;
    }

    public TypeDescriptor getValueTypeDescriptor(@Nullable Object rootObject) throws EvaluationException {
        throw this.readOnlyException;
    }

    public TypeDescriptor getValueTypeDescriptor(EvaluationContext context) throws EvaluationException {
        throw this.readOnlyException;
    }

    public TypeDescriptor getValueTypeDescriptor(EvaluationContext context, @Nullable Object rootObject) throws EvaluationException {
        throw this.readOnlyException;
    }

    public void setValue(EvaluationContext context, @Nullable Object value) throws EvaluationException {
        throw this.readOnlyException;
    }

    public void setValue(@Nullable Object rootObject, @Nullable Object value) throws EvaluationException {
        throw this.readOnlyException;
    }

    public void setValue(EvaluationContext context, @Nullable Object rootObject, @Nullable Object value) throws EvaluationException {
        throw this.readOnlyException;
    }

    public boolean isWritable(EvaluationContext context) throws EvaluationException {
        return false;
    }

    public boolean isWritable(EvaluationContext context, @Nullable Object rootObject) throws EvaluationException {
        return false;
    }

    public boolean isWritable(@Nullable Object rootObject) throws EvaluationException {
        return false;
    }

    public final String getExpressionString() {
        return this.function.toString();
    }
}

