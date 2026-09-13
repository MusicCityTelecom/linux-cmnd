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

public final class AstNotEqual
extends BooleanNode {
    public AstNotEqual(int id) {
        super(id);
    }

    public Object getValue(EvaluationContext ctx) throws ELException {
        Object obj1;
        Object obj0 = this.children[0].getValue(ctx);
        return !AstNotEqual.equals(obj0, obj1 = this.children[1].getValue(ctx));
    }
}

