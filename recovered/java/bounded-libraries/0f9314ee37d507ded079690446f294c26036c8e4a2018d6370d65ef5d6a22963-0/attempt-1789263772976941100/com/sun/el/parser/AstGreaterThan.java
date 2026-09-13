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

public final class AstGreaterThan
extends BooleanNode {
    public AstGreaterThan(int id) {
        super(id);
    }

    public Object getValue(EvaluationContext ctx) throws ELException {
        Object obj0 = this.children[0].getValue(ctx);
        if (obj0 == null) {
            return Boolean.FALSE;
        }
        Object obj1 = this.children[1].getValue(ctx);
        if (obj1 == null) {
            return Boolean.FALSE;
        }
        return AstGreaterThan.compare(obj0, obj1) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}

