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

public final class AstDeferredExpression
extends SimpleNode {
    public AstDeferredExpression(int id) {
        super(id);
    }

    public Class getType(EvaluationContext ctx) throws ELException {
        return this.children[0].getType(ctx);
    }

    public Object getValue(EvaluationContext ctx) throws ELException {
        return this.children[0].getValue(ctx);
    }

    public boolean isReadOnly(EvaluationContext ctx) throws ELException {
        return this.children[0].isReadOnly(ctx);
    }

    public void setValue(EvaluationContext ctx, Object value) throws ELException {
        this.children[0].setValue(ctx, value);
    }
}

