/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.el.ELException
 */
package com.sun.el.parser;

import com.sun.el.lang.EvaluationContext;
import com.sun.el.parser.BooleanNode;
import javax.el.ELException;

public final class AstAnd
extends BooleanNode {
    public AstAnd(int id) {
        super(id);
    }

    public Object getValue(EvaluationContext ctx) throws ELException {
        Object obj = this.children[0].getValue(ctx);
        Boolean b = AstAnd.coerceToBoolean(obj);
        if (!b.booleanValue()) {
            return b;
        }
        obj = this.children[1].getValue(ctx);
        b = AstAnd.coerceToBoolean(obj);
        return b;
    }
}

