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

public final class AstLiteralExpression
extends SimpleNode {
    public AstLiteralExpression(int id) {
        super(id);
    }

    public Class getType(EvaluationContext ctx) throws ELException {
        return String.class;
    }

    public Object getValue(EvaluationContext ctx) throws ELException {
        return this.image;
    }

    public void setImage(String image) {
        if (image.indexOf(92) == -1) {
            this.image = image;
            return;
        }
        int size = image.length();
        StringBuffer buf = new StringBuffer(size);
        for (int i = 0; i < size; ++i) {
            char c1;
            char c = image.charAt(i);
            if (c == '\\' && i + 1 < size && ((c1 = image.charAt(i + 1)) == '\\' || c1 == '\"' || c1 == '\'' || c1 == '#' || c1 == '$')) {
                c = c1;
                ++i;
            }
            buf.append(c);
        }
        this.image = buf.toString();
    }
}

