/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import org.apache.commons.jexl3.parser.JexlNode;
import org.apache.commons.jexl3.parser.Parser;
import org.apache.commons.jexl3.parser.ParserVisitor;

public final class ASTStringLiteral
extends JexlNode
implements JexlNode.Constant<String> {
    private String literal = null;

    ASTStringLiteral(int id) {
        super(id);
    }

    ASTStringLiteral(Parser p, int id) {
        super(p, id);
    }

    @Override
    public String toString() {
        return this.literal;
    }

    @Override
    public String getLiteral() {
        return this.literal;
    }

    @Override
    protected boolean isConstant(boolean literal) {
        return true;
    }

    void setLiteral(String literal) {
        this.literal = literal;
    }

    @Override
    public Object jjtAccept(ParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}

