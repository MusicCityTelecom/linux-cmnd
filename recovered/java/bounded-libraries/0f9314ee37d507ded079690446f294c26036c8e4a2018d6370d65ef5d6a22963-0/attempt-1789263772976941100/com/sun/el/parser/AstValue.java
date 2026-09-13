/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.el.ELContext
 *  javax.el.ELException
 *  javax.el.ELResolver
 *  javax.el.MethodInfo
 *  javax.el.PropertyNotFoundException
 *  javax.el.PropertyNotWritableException
 *  javax.el.ValueReference
 */
package com.sun.el.parser;

import com.sun.el.lang.ELSupport;
import com.sun.el.lang.EvaluationContext;
import com.sun.el.parser.AstMethodSuffix;
import com.sun.el.parser.Node;
import com.sun.el.parser.SimpleNode;
import com.sun.el.util.MessageFactory;
import com.sun.el.util.ReflectionUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.MethodInfo;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;
import javax.el.ValueReference;

public final class AstValue
extends SimpleNode {
    public AstValue(int id) {
        super(id);
    }

    public Class getType(EvaluationContext ctx) throws ELException {
        Target t = this.getTarget(ctx);
        if (t.suffixNode instanceof AstMethodSuffix) {
            return null;
        }
        Object property = t.suffixNode.getValue(ctx);
        ctx.setPropertyResolved(false);
        Class ret = ctx.getELResolver().getType((ELContext)ctx, t.base, property);
        if (!ctx.isPropertyResolved()) {
            ELSupport.throwUnhandled(t.base, property);
        }
        return ret;
    }

    public ValueReference getValueReference(EvaluationContext ctx) throws ELException {
        Target t = this.getTarget(ctx);
        if (t.suffixNode instanceof AstMethodSuffix) {
            return null;
        }
        Object property = t.suffixNode.getValue(ctx);
        return new ValueReference(t.base, property);
    }

    private Object getValue(Object base, Node child, EvaluationContext ctx) throws ELException {
        Object value = null;
        ELResolver resolver = ctx.getELResolver();
        if (child instanceof AstMethodSuffix) {
            AstMethodSuffix methodSuffix = (AstMethodSuffix)child;
            String method = methodSuffix.getMethodName();
            Class[] paramTypes = methodSuffix.getParamTypes();
            Object[] params = methodSuffix.getParameters(ctx);
            ctx.setPropertyResolved(false);
            value = resolver.invoke((ELContext)ctx, base, (Object)method, paramTypes, params);
        } else {
            Object property = child.getValue(ctx);
            if (property != null) {
                ctx.setPropertyResolved(false);
                value = resolver.getValue((ELContext)ctx, base, property);
                if (!ctx.isPropertyResolved()) {
                    ELSupport.throwUnhandled(base, property);
                }
            }
        }
        return value;
    }

    private final Target getTarget(EvaluationContext ctx) throws ELException {
        Object base = this.children[0].getValue(ctx);
        if (base == null) {
            throw new PropertyNotFoundException(MessageFactory.get("error.unreachable.base", this.children[0].getImage()));
        }
        Object property = null;
        int propCount = this.jjtGetNumChildren() - 1;
        ELResolver resolver = ctx.getELResolver();
        if (propCount > 1) {
            for (int i = 1; base != null && i < propCount; ++i) {
                base = this.getValue(base, this.children[i], ctx);
            }
            if (base == null) {
                throw new PropertyNotFoundException(MessageFactory.get("error.unreachable.property", property));
            }
        }
        return new Target(base, this.children[propCount]);
    }

    public Object getValue(EvaluationContext ctx) throws ELException {
        Object base = this.children[0].getValue(ctx);
        int propCount = this.jjtGetNumChildren();
        ELResolver resolver = ctx.getELResolver();
        for (int i = 1; base != null && i < propCount; ++i) {
            base = this.getValue(base, this.children[i], ctx);
        }
        return base;
    }

    public boolean isReadOnly(EvaluationContext ctx) throws ELException {
        Target t = this.getTarget(ctx);
        if (t.suffixNode instanceof AstMethodSuffix) {
            return true;
        }
        Object property = t.suffixNode.getValue(ctx);
        ctx.setPropertyResolved(false);
        boolean ret = ctx.getELResolver().isReadOnly((ELContext)ctx, t.base, property);
        if (!ctx.isPropertyResolved()) {
            ELSupport.throwUnhandled(t.base, property);
        }
        return ret;
    }

    public void setValue(EvaluationContext ctx, Object value) throws ELException {
        Target t = this.getTarget(ctx);
        if (t.suffixNode instanceof AstMethodSuffix) {
            throw new PropertyNotWritableException(MessageFactory.get("error.syntax.set"));
        }
        Object property = t.suffixNode.getValue(ctx);
        ctx.setPropertyResolved(false);
        ELResolver elResolver = ctx.getELResolver();
        if (value != null) {
            value = ELSupport.coerceToType(value, elResolver.getType((ELContext)ctx, t.base, property));
        }
        elResolver.setValue((ELContext)ctx, t.base, property, value);
        if (!ctx.isPropertyResolved()) {
            ELSupport.throwUnhandled(t.base, property);
        }
    }

    public MethodInfo getMethodInfo(EvaluationContext ctx, Class[] paramTypes) throws ELException {
        Target t = this.getTarget(ctx);
        if (t.suffixNode instanceof AstMethodSuffix) {
            return null;
        }
        Object property = t.suffixNode.getValue(ctx);
        Method m = ReflectionUtil.getMethod(t.base, property, paramTypes);
        return new MethodInfo(m.getName(), m.getReturnType(), (Class[])m.getParameterTypes());
    }

    public Object invoke(EvaluationContext ctx, Class[] paramTypes, Object[] paramValues) throws ELException {
        Target t = this.getTarget(ctx);
        if (t.suffixNode instanceof AstMethodSuffix) {
            AstMethodSuffix methodSuffix = (AstMethodSuffix)t.suffixNode;
            paramTypes = methodSuffix.getParamTypes();
            Object[] params = methodSuffix.getParameters(ctx);
            String method = methodSuffix.getMethodName();
            ctx.setPropertyResolved(false);
            ELResolver resolver = ctx.getELResolver();
            return resolver.invoke((ELContext)ctx, t.base, (Object)method, paramTypes, params);
        }
        Object property = t.suffixNode.getValue(ctx);
        Method m = ReflectionUtil.getMethod(t.base, property, paramTypes);
        Object result = null;
        try {
            result = m.invoke(t.base, paramValues);
        }
        catch (IllegalAccessException iae) {
            throw new ELException((Throwable)iae);
        }
        catch (InvocationTargetException ite) {
            throw new ELException(ite.getCause());
        }
        return result;
    }

    protected static class Target {
        protected Object base;
        protected Node suffixNode;

        Target(Object base, Node suffixNode) {
            this.base = base;
            this.suffixNode = suffixNode;
        }
    }
}

