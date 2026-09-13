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

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class AstMethodSuffix
extends SimpleNode {
    public AstMethodSuffix(int id) {
        super(id);
    }

    String getMethodName() {
        return this.image;
    }

    Class<?>[] getParamTypes() {
        return null;
    }

    Object[] getParameters(EvaluationContext ctx) throws ELException {
        if (this.children == null) {
            return new Object[0];
        }
        Object[] obj = new Object[this.children.length];
        for (int i = 0; i < obj.length; ++i) {
            obj[i] = this.children[i].getValue(ctx);
        }
        return obj;
    }

    @Override
    public boolean isParametersProvided() {
        return true;
    }
}

