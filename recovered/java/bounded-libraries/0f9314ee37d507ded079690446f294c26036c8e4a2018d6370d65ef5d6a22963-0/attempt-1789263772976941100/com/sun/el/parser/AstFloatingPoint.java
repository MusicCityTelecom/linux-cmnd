/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.el.ELException
 */
package com.sun.el.parser;

import com.sun.el.lang.EvaluationContext;
import com.sun.el.parser.SimpleNode;
import java.math.BigDecimal;
import javax.el.ELException;

public final class AstFloatingPoint
extends SimpleNode {
    private Number number;

    public AstFloatingPoint(int id) {
        super(id);
    }

    public Number getFloatingPoint() {
        if (this.number == null) {
            try {
                this.number = Double.valueOf(this.image);
            }
            catch (ArithmeticException e0) {
                this.number = new BigDecimal(this.image);
            }
        }
        return this.number;
    }

    public Object getValue(EvaluationContext ctx) throws ELException {
        return this.getFloatingPoint();
    }

    public Class getType(EvaluationContext ctx) throws ELException {
        return this.getFloatingPoint().getClass();
    }
}

