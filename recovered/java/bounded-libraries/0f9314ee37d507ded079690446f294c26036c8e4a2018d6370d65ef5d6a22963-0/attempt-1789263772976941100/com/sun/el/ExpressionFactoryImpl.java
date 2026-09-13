/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.el.ELContext
 *  javax.el.ELException
 *  javax.el.ExpressionFactory
 *  javax.el.MethodExpression
 *  javax.el.ValueExpression
 */
package com.sun.el;

import com.sun.el.ValueExpressionLiteral;
import com.sun.el.lang.ELSupport;
import com.sun.el.lang.ExpressionBuilder;
import com.sun.el.util.MessageFactory;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

public class ExpressionFactoryImpl
extends ExpressionFactory {
    public Object coerceToType(Object obj, Class type) {
        Object ret;
        try {
            ret = ELSupport.coerceToType(obj, type);
        }
        catch (IllegalArgumentException ex) {
            throw new ELException((Throwable)ex);
        }
        return ret;
    }

    public MethodExpression createMethodExpression(ELContext context, String expression, Class expectedReturnType, Class[] expectedParamTypes) {
        if (expectedParamTypes == null) {
            throw new NullPointerException(MessageFactory.get("error.method.nullParms"));
        }
        ExpressionBuilder builder = new ExpressionBuilder(expression, context);
        return builder.createMethodExpression(expectedReturnType, expectedParamTypes);
    }

    public ValueExpression createValueExpression(ELContext context, String expression, Class expectedType) {
        if (expectedType == null) {
            throw new NullPointerException(MessageFactory.get("error.value.expectedType"));
        }
        ExpressionBuilder builder = new ExpressionBuilder(expression, context);
        return builder.createValueExpression(expectedType);
    }

    public ValueExpression createValueExpression(Object instance, Class expectedType) {
        if (expectedType == null) {
            throw new NullPointerException(MessageFactory.get("error.value.expectedType"));
        }
        return new ValueExpressionLiteral(instance, expectedType);
    }
}

