/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.el.ELException
 */
package com.sun.el.parser;

import com.sun.el.lang.EvaluationContext;
import com.sun.el.parser.SimpleNode;
import javax.el.ELException;

public final class AstChoice
extends SimpleNode {
    public AstChoice(int id) {
        super(id);
    }

    public Class getType(EvaluationContext ctx) throws ELException {
        Object val = this.getValue(ctx);
        return val != null ? val.getClass() : null;
    }

    public Object getValue(EvaluationContext ctx) throws ELException {
        Object obj0 = this.children[0].getValue(ctx);
        Boolean b0 = AstChoice.coerceToBoolean(obj0);
        return this.children[b0 != false ? 1 : 2].getValue(ctx);
    }

    public boolean isReadOnly(EvaluationContext ctx) throws ELException {
        Object obj0 = this.children[0].getValue(ctx);
        Boolean b0 = AstChoice.coerceToBoolean(obj0);
        return this.children[b0 != false ? 1 : 2].isReadOnly(ctx);
    }

    public void setValue(EvaluationContext ctx, Object value) throws ELException {
        Object obj0 = this.children[0].getValue(ctx);
        Boolean b0 = AstChoice.coerceToBoolean(obj0);
        this.children[b0 != false ? 1 : 2].setValue(ctx, value);
    }

    public Object invoke(EvaluationContext ctx, Class[] paramTypes, Object[] paramValues) throws ELException {
        Object obj0 = this.children[0].getValue(ctx);
        Boolean b0 = AstChoice.coerceToBoolean(obj0);
        return this.children[b0 != false ? 1 : 2].invoke(ctx, paramTypes, paramValues);
    }
}

