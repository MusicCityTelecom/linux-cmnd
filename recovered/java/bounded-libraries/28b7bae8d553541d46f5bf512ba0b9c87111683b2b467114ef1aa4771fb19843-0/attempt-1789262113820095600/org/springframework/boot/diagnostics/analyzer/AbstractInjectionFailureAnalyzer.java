/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanInstantiationException
 *  org.springframework.beans.factory.InjectionPoint
 *  org.springframework.beans.factory.UnsatisfiedDependencyException
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.diagnostics.analyzer;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.util.ClassUtils;

public abstract class AbstractInjectionFailureAnalyzer<T extends Throwable>
extends AbstractFailureAnalyzer<T> {
    @Override
    protected final FailureAnalysis analyze(Throwable rootFailure, T cause) {
        return this.analyze(rootFailure, cause, this.getDescription(rootFailure));
    }

    private String getDescription(Throwable rootFailure) {
        UnsatisfiedDependencyException unsatisfiedDependency = this.findMostNestedCause(rootFailure, UnsatisfiedDependencyException.class);
        if (unsatisfiedDependency != null) {
            return this.getDescription(unsatisfiedDependency);
        }
        BeanInstantiationException beanInstantiationException = this.findMostNestedCause(rootFailure, BeanInstantiationException.class);
        if (beanInstantiationException != null) {
            return this.getDescription(beanInstantiationException);
        }
        return null;
    }

    private <C extends Exception> C findMostNestedCause(Throwable root, Class<C> type) {
        Exception result = null;
        for (Throwable candidate = root; candidate != null; candidate = candidate.getCause()) {
            if (!type.isAssignableFrom(candidate.getClass())) continue;
            result = (Exception)candidate;
        }
        return (C)result;
    }

    private String getDescription(UnsatisfiedDependencyException ex) {
        InjectionPoint injectionPoint = ex.getInjectionPoint();
        if (injectionPoint != null) {
            if (injectionPoint.getField() != null) {
                return String.format("Field %s in %s", injectionPoint.getField().getName(), injectionPoint.getField().getDeclaringClass().getName());
            }
            if (injectionPoint.getMethodParameter() != null) {
                if (injectionPoint.getMethodParameter().getConstructor() != null) {
                    return String.format("Parameter %d of constructor in %s", injectionPoint.getMethodParameter().getParameterIndex(), injectionPoint.getMethodParameter().getDeclaringClass().getName());
                }
                return String.format("Parameter %d of method %s in %s", injectionPoint.getMethodParameter().getParameterIndex(), injectionPoint.getMethodParameter().getMethod().getName(), injectionPoint.getMethodParameter().getDeclaringClass().getName());
            }
        }
        return ex.getResourceDescription();
    }

    private String getDescription(BeanInstantiationException ex) {
        if (ex.getConstructingMethod() != null) {
            return String.format("Method %s in %s", ex.getConstructingMethod().getName(), ex.getConstructingMethod().getDeclaringClass().getName());
        }
        if (ex.getConstructor() != null) {
            return String.format("Constructor in %s", ClassUtils.getUserClass(ex.getConstructor().getDeclaringClass()).getName());
        }
        return ex.getBeanClass().getName();
    }

    protected abstract FailureAnalysis analyze(Throwable var1, T var2, String var3);
}

