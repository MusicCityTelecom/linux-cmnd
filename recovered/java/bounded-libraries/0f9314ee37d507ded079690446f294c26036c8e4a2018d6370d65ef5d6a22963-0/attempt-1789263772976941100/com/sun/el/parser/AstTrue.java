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

public final class AstTrue
extends BooleanNode {
    public AstTrue(int id) {
        super(id);
    }

    public Object getValue(EvaluationContext ctx) throws ELException {
        return Boolean.TRUE;
    }
}

