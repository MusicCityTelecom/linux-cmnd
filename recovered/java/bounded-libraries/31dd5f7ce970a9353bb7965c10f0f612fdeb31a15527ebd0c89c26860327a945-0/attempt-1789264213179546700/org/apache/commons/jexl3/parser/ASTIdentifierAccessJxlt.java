/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import org.apache.commons.jexl3.JxltEngine;
import org.apache.commons.jexl3.parser.ASTIdentifierAccess;
import org.apache.commons.jexl3.parser.Parser;

public class ASTIdentifierAccessJxlt
extends ASTIdentifierAccess {
    protected JxltEngine.Expression jxltExpr;

    ASTIdentifierAccessJxlt(int id) {
        super(id);
    }

    ASTIdentifierAccessJxlt(Parser p, int id) {
        super(p, id);
    }

    @Override
    public boolean isExpression() {
        return true;
    }

    public void setExpression(JxltEngine.Expression tp) {
        this.jxltExpr = tp;
    }

    public JxltEngine.Expression getExpression() {
        return this.jxltExpr;
    }
}

