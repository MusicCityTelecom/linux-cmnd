/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import org.apache.commons.jexl3.parser.JexlNode;
import org.apache.commons.jexl3.parser.Parser;
import org.apache.commons.jexl3.parser.ParserVisitor;

public final class ASTJxltLiteral
extends JexlNode {
    private String literal = null;

    ASTJxltLiteral(int id) {
        super(id);
    }

    ASTJxltLiteral(Parser p, int id) {
        super(p, id);
    }

    void setLiteral(String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return this.literal;
    }

    @Override
    public String toString() {
        return this.literal;
    }

    @Override
    public Object jjtAccept(ParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}

