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
 *  org.springframework.util.Assert
 */
package org.springframework.integration.expression;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.TypedValue;
import org.springframework.expression.common.ExpressionUtils;
import org.springframework.util.Assert;

public class ValueExpression<V>
implements Expression {
    private final V value;
    private final Class<V> aClass;
    private final TypedValue typedResultValue;
    private final TypeDescriptor typeDescriptor;

    public ValueExpression(V value) {
        Assert.notNull(value, (String)"'value' must not be null");
        this.value = value;
        this.aClass = this.value.getClass();
        this.typedResultValue = new TypedValue(this.value);
        this.typeDescriptor = this.typedResultValue.getTypeDescriptor();
    }

    public V getValue() throws EvaluationException {
        return this.value;
    }

    public V getValue(Object rootObject) throws EvaluationException {
        return this.value;
    }

    public V getValue(EvaluationContext context) throws EvaluationException {
        return this.value;
    }

    public V getValue(EvaluationContext context, Object rootObject) throws EvaluationException {
        return this.value;
    }

    public <T> T getValue(Object rootObject, Class<T> desiredResultType) throws EvaluationException {
        return this.getValue(desiredResultType);
    }

    public <T> T getValue(Class<T> desiredResultType) throws EvaluationException {
        return (T)ExpressionUtils.convertTypedValue(null, (TypedValue)this.typedResultValue, desiredResultType);
    }

    public <T> T getValue(EvaluationContext context, Object rootObject, Class<T> desiredResultType) throws EvaluationException {
        return this.getValue(context, desiredResultType);
    }

    public <T> T getValue(EvaluationContext context, Class<T> desiredResultType) throws EvaluationException {
        return (T)ExpressionUtils.convertTypedValue((EvaluationContext)context, (TypedValue)this.typedResultValue, desiredResultType);
    }

    public Class<V> getValueType() throws EvaluationException {
        return this.aClass;
    }

    public Class<V> getValueType(Object rootObject) throws EvaluationException {
        return this.aClass;
    }

    public Class<V> getValueType(EvaluationContext context) throws EvaluationException {
        return this.aClass;
    }

    public Class<V> getValueType(EvaluationContext context, Object rootObject) throws EvaluationException {
        return this.aClass;
    }

    public TypeDescriptor getValueTypeDescriptor() throws EvaluationException {
        return this.typeDescriptor;
    }

    public TypeDescriptor getValueTypeDescriptor(Object rootObject) throws EvaluationException {
        return this.typeDescriptor;
    }

    public TypeDescriptor getValueTypeDescriptor(EvaluationContext context) throws EvaluationException {
        return this.typeDescriptor;
    }

    public TypeDescriptor getValueTypeDescriptor(EvaluationContext context, Object rootObject) throws EvaluationException {
        return this.typeDescriptor;
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

    public void setValue(EvaluationContext context, Object value) throws EvaluationException {
        this.setValue(context, null, value);
    }

    public void setValue(Object rootObject, Object value) throws EvaluationException {
        this.setValue(null, rootObject, value);
    }

    public void setValue(EvaluationContext context, Object rootObject, Object value) throws EvaluationException {
        throw new EvaluationException(this.value.toString(), "Cannot call setValue() on a ValueExpression");
    }

    public String getExpressionString() {
        return this.value.toString();
    }

    public String toString() {
        return "ValueExpression [value=" + this.value + "]";
    }
}

