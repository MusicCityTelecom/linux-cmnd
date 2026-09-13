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

public class ArithmeticNode
extends SimpleNode {
    public ArithmeticNode(int i) {
        super(i);
    }

    public Class getType(EvaluationContext ctx) throws ELException {
        return Number.class;
    }
}

