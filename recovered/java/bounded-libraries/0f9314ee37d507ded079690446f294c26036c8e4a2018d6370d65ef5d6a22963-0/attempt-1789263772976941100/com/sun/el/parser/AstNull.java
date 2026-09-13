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

public final class AstNull
extends SimpleNode {
    public AstNull(int id) {
        super(id);
    }

    public Class getType(EvaluationContext ctx) throws ELException {
        return null;
    }

    public Object getValue(EvaluationContext ctx) throws ELException {
        return null;
    }
}

