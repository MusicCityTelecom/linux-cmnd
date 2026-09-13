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
 *  org.springframework.util.Assert
 */
package org.springframework.integration.expression;

import java.util.function.Supplier;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.TypedValue;
import org.springframework.expression.common.ExpressionUtils;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.Assert;

public class SupplierExpression<T>
implements Expression {
    private final Supplier<T> supplier;
    private final EvaluationContext defaultContext = new StandardEvaluationContext();
    private final EvaluationException readOnlyException;

    public SupplierExpression(Supplier<T> supplier) {
        Assert.notNull(supplier, (String)"'function' must not be null.");
        this.supplier = supplier;
        this.readOnlyException = new EvaluationException(this.getExpressionString(), "SupplierExpression is a 'read only' Expression implementation");
    }

    public Object getValue() throws EvaluationException {
        return this.supplier.get();
    }

    public Object getValue(Object rootObject) throws EvaluationException {
        return this.getValue();
    }

    public <C> C getValue(Class<C> desiredResultType) throws EvaluationException {
        return this.getValue(this.defaultContext, desiredResultType);
    }

    public <C> C getValue(Object rootObject, Class<C> desiredResultType) throws EvaluationException {
        return this.getValue(this.defaultContext, rootObject, desiredResultType);
    }

    public Object getValue(EvaluationContext context) throws EvaluationException {
        Object root = context.getRootObject().getValue();
        return root == null ? this.getValue() : this.getValue(root);
    }

    public Object getValue(EvaluationContext context, Object rootObject) throws EvaluationException {
        return this.getValue(rootObject);
    }

    public <C> C getValue(EvaluationContext context, Class<C> desiredResultType) throws EvaluationException {
        return (C)ExpressionUtils.convertTypedValue((EvaluationContext)context, (TypedValue)new TypedValue(this.getValue(context)), desiredResultType);
    }

    public <C> C getValue(EvaluationContext context, Object rootObject, Class<C> desiredResultType) throws EvaluationException {
        return (C)ExpressionUtils.convertTypedValue((EvaluationContext)context, (TypedValue)new TypedValue(this.getValue(rootObject)), desiredResultType);
    }

    public Class<?> getValueType() throws EvaluationException {
        throw this.readOnlyException;
    }

    public Class<?> getValueType(Object rootObject) throws EvaluationException {
        throw this.readOnlyException;
    }

    public Class<?> getValueType(EvaluationContext context) throws EvaluationException {
        throw this.readOnlyException;
    }

    public Class<?> getValueType(EvaluationContext context, Object rootObject) throws EvaluationException {
        throw this.readOnlyException;
    }

    public TypeDescriptor getValueTypeDescriptor() throws EvaluationException {
        throw this.readOnlyException;
    }

    public TypeDescriptor getValueTypeDescriptor(Object rootObject) throws EvaluationException {
        throw this.readOnlyException;
    }

    public TypeDescriptor getValueTypeDescriptor(EvaluationContext context) throws EvaluationException {
        throw this.readOnlyException;
    }

    public TypeDescriptor getValueTypeDescriptor(EvaluationContext context, Object rootObject) throws EvaluationException {
        throw this.readOnlyException;
    }

    public void setValue(EvaluationContext context, Object value) throws EvaluationException {
        throw this.readOnlyException;
    }

    public void setValue(Object rootObject, Object value) throws EvaluationException {
        throw this.readOnlyException;
    }

    public void setValue(EvaluationContext context, Object rootObject, Object value) throws EvaluationException {
        throw this.readOnlyException;
    }

    public boolean isWritable(EvaluationContext context) throws EvaluationException {
        return false;
    }

    public boolean isWritable(EvaluationContext context, Object rootObject) throws EvaluationException {
        return false;
    }

    public boolean isWritable(Object rootObject) throws EvaluationException {
        return false;
    }

    public final String getExpressionString() {
        return this.supplier.toString();
    }
}

