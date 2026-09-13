/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.el.ELException
 */
package com.sun.el.parser;

import com.sun.el.lang.ELArithmetic;
import com.sun.el.lang.EvaluationContext;
import com.sun.el.parser.ArithmeticNode;
import javax.el.ELException;

public final class AstPlus
extends ArithmeticNode {
    public AstPlus(int id) {
        super(id);
    }

    public Object getValue(EvaluationContext ctx) throws ELException {
        Object obj0 = this.children[0].getValue(ctx);
        Object obj1 = this.children[1].getValue(ctx);
        return ELArithmetic.add(obj0, obj1);
    }
}

