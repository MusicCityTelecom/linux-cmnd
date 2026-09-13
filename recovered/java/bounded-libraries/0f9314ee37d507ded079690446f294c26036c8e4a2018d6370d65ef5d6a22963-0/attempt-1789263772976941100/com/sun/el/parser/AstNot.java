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

public final class AstNot
extends SimpleNode {
    public AstNot(int id) {
        super(id);
    }

    public Class getType(EvaluationContext ctx) throws ELException {
        return Boolean.class;
    }

    public Object getValue(EvaluationContext ctx) throws ELException {
        Object obj = this.children[0].getValue(ctx);
        Boolean b = AstNot.coerceToBoolean(obj);
        return b == false;
    }
}

