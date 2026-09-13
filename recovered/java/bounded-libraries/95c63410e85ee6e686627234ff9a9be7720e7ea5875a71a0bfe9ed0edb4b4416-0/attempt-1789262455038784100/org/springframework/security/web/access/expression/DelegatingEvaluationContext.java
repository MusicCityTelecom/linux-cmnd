/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.BeanResolver
 *  org.springframework.expression.ConstructorResolver
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.MethodResolver
 *  org.springframework.expression.OperatorOverloader
 *  org.springframework.expression.PropertyAccessor
 *  org.springframework.expression.TypeComparator
 *  org.springframework.expression.TypeConverter
 *  org.springframework.expression.TypeLocator
 *  org.springframework.expression.TypedValue
 */
package org.springframework.security.web.access.expression;

import java.util.List;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.ConstructorResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.MethodResolver;
import org.springframework.expression.OperatorOverloader;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.TypeComparator;
import org.springframework.expression.TypeConverter;
import org.springframework.expression.TypeLocator;
import org.springframework.expression.TypedValue;

class DelegatingEvaluationContext
implements EvaluationContext {
    private final EvaluationContext delegate;

    DelegatingEvaluationContext(EvaluationContext delegate) {
        this.delegate = delegate;
    }

    public TypedValue getRootObject() {
        return this.delegate.getRootObject();
    }

    public List<ConstructorResolver> getConstructorResolvers() {
        return this.delegate.getConstructorResolvers();
    }

    public List<MethodResolver> getMethodResolvers() {
        return this.delegate.getMethodResolvers();
    }

    public List<PropertyAccessor> getPropertyAccessors() {
        return this.delegate.getPropertyAccessors();
    }

    public TypeLocator getTypeLocator() {
        return this.delegate.getTypeLocator();
    }

    public TypeConverter getTypeConverter() {
        return this.delegate.getTypeConverter();
    }

    public TypeComparator getTypeComparator() {
        return this.delegate.getTypeComparator();
    }

    public OperatorOverloader getOperatorOverloader() {
        return this.delegate.getOperatorOverloader();
    }

    public BeanResolver getBeanResolver() {
        return this.delegate.getBeanResolver();
    }

    public void setVariable(String name, Object value) {
        this.delegate.setVariable(name, value);
    }

    public Object lookupVariable(String name) {
        return this.delegate.lookupVariable(name);
    }
}

