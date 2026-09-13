/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.el.ELContext
 *  javax.el.ELException
 *  javax.el.ELResolver
 *  javax.el.MethodExpression
 *  javax.el.MethodInfo
 *  javax.el.MethodNotFoundException
 *  javax.el.ValueExpression
 *  javax.el.ValueReference
 *  javax.el.VariableMapper
 */
package com.sun.el.parser;

import com.sun.el.lang.ELSupport;
import com.sun.el.lang.EvaluationContext;
import com.sun.el.parser.SimpleNode;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.MethodExpression;
import javax.el.MethodInfo;
import javax.el.MethodNotFoundException;
import javax.el.ValueExpression;
import javax.el.ValueReference;
import javax.el.VariableMapper;

public final class AstIdentifier
extends SimpleNode {
    public AstIdentifier(int id) {
        super(id);
    }

    public Class getType(EvaluationContext ctx) throws ELException {
        ValueExpression expr;
        VariableMapper varMapper = ctx.getVariableMapper();
        if (varMapper != null && (expr = varMapper.resolveVariable(this.image)) != null) {
            return expr.getType(ctx.getELContext());
        }
        ctx.setPropertyResolved(false);
        Class ret = ctx.getELResolver().getType((ELContext)ctx, null, (Object)this.image);
        if (!ctx.isPropertyResolved()) {
            ELSupport.throwUnhandled(null, this.image);
        }
        return ret;
    }

    public ValueReference getValuereference(EvaluationContext ctx) throws ELException {
        ValueExpression expr;
        VariableMapper varMapper = ctx.getVariableMapper();
        if (varMapper != null && (expr = varMapper.resolveVariable(this.image)) != null) {
            return expr.getValueReference(ctx.getELContext());
        }
        return new ValueReference(null, (Object)this.image);
    }

    public Object getValue(EvaluationContext ctx) throws ELException {
        ValueExpression expr;
        VariableMapper varMapper = ctx.getVariableMapper();
        if (varMapper != null && (expr = varMapper.resolveVariable(this.image)) != null) {
            return expr.getValue(ctx.getELContext());
        }
        ctx.setPropertyResolved(false);
        Object ret = ctx.getELResolver().getValue((ELContext)ctx, null, (Object)this.image);
        if (!ctx.isPropertyResolved()) {
            ELSupport.throwUnhandled(null, this.image);
        }
        return ret;
    }

    public boolean isReadOnly(EvaluationContext ctx) throws ELException {
        ValueExpression expr;
        VariableMapper varMapper = ctx.getVariableMapper();
        if (varMapper != null && (expr = varMapper.resolveVariable(this.image)) != null) {
            return expr.isReadOnly(ctx.getELContext());
        }
        ctx.setPropertyResolved(false);
        boolean ret = ctx.getELResolver().isReadOnly((ELContext)ctx, null, (Object)this.image);
        if (!ctx.isPropertyResolved()) {
            ELSupport.throwUnhandled(null, this.image);
        }
        return ret;
    }

    public void setValue(EvaluationContext ctx, Object value) throws ELException {
        ValueExpression expr;
        VariableMapper varMapper = ctx.getVariableMapper();
        if (varMapper != null && (expr = varMapper.resolveVariable(this.image)) != null) {
            expr.setValue(ctx.getELContext(), value);
            return;
        }
        ctx.setPropertyResolved(false);
        ELResolver elResolver = ctx.getELResolver();
        if (value != null) {
            value = ELSupport.coerceToType(value, elResolver.getType((ELContext)ctx, null, (Object)this.image));
        }
        elResolver.setValue((ELContext)ctx, null, (Object)this.image, value);
        if (!ctx.isPropertyResolved()) {
            ELSupport.throwUnhandled(null, this.image);
        }
    }

    private final Object invokeTarget(EvaluationContext ctx, Object target, Object[] paramValues) throws ELException {
        if (target instanceof MethodExpression) {
            MethodExpression me = (MethodExpression)target;
            return me.invoke(ctx.getELContext(), paramValues);
        }
        if (target == null) {
            throw new MethodNotFoundException("Identity '" + this.image + "' was null and was unable to invoke");
        }
        throw new ELException("Identity '" + this.image + "' does not reference a MethodExpression instance, returned type: " + target.getClass().getName());
    }

    public Object invoke(EvaluationContext ctx, Class[] paramTypes, Object[] paramValues) throws ELException {
        return this.getMethodExpression(ctx).invoke(ctx.getELContext(), paramValues);
    }

    public MethodInfo getMethodInfo(EvaluationContext ctx, Class[] paramTypes) throws ELException {
        return this.getMethodExpression(ctx).getMethodInfo(ctx.getELContext());
    }

    private final MethodExpression getMethodExpression(EvaluationContext ctx) throws ELException {
        Object obj = null;
        VariableMapper varMapper = ctx.getVariableMapper();
        ValueExpression ve = null;
        if (varMapper != null && (ve = varMapper.resolveVariable(this.image)) != null) {
            obj = ve.getValue((ELContext)ctx);
        }
        if (ve == null) {
            ctx.setPropertyResolved(false);
            obj = ctx.getELResolver().getValue((ELContext)ctx, null, (Object)this.image);
        }
        if (obj instanceof MethodExpression) {
            return (MethodExpression)obj;
        }
        if (obj == null) {
            throw new MethodNotFoundException("Identity '" + this.image + "' was null and was unable to invoke");
        }
        throw new ELException("Identity '" + this.image + "' does not reference a MethodExpression instance, returned type: " + obj.getClass().getName());
    }
}

