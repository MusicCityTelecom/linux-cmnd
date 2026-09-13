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

public final class AstLessThanEqual
extends BooleanNode {
    public AstLessThanEqual(int id) {
        super(id);
    }

    public Object getValue(EvaluationContext ctx) throws ELException {
        Object obj1;
        Object obj0 = this.children[0].getValue(ctx);
        if (obj0 == (obj1 = this.children[1].getValue(ctx))) {
            return Boolean.TRUE;
        }
        if (obj0 == null || obj1 == null) {
            return Boolean.FALSE;
        }
        return AstLessThanEqual.compare(obj0, obj1) <= 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}

