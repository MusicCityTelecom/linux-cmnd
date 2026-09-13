/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.el.ELException
 */
package com.sun.el.parser;

import com.sun.el.lang.EvaluationContext;
import com.sun.el.parser.SimpleNode;
import java.math.BigInteger;
import javax.el.ELException;

public final class AstInteger
extends SimpleNode {
    private Number number;

    public AstInteger(int id) {
        super(id);
    }

    protected Number getInteger() {
        if (this.number == null) {
            try {
                this.number = Long.valueOf(this.image);
            }
            catch (ArithmeticException e1) {
                this.number = new BigInteger(this.image);
            }
        }
        return this.number;
    }

    public Class getType(EvaluationContext ctx) throws ELException {
        return this.getInteger().getClass();
    }

    public Object getValue(EvaluationContext ctx) throws ELException {
        return this.getInteger();
    }
}

