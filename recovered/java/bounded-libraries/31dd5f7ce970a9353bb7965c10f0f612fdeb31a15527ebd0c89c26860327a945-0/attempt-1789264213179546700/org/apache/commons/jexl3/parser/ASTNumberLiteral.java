/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import org.apache.commons.jexl3.parser.JexlNode;
import org.apache.commons.jexl3.parser.NumberParser;
import org.apache.commons.jexl3.parser.Parser;
import org.apache.commons.jexl3.parser.ParserVisitor;

public final class ASTNumberLiteral
extends JexlNode
implements JexlNode.Constant<Number> {
    private final NumberParser nlp = new NumberParser();

    ASTNumberLiteral(int id) {
        super(id);
    }

    ASTNumberLiteral(Parser p, int id) {
        super(p, id);
    }

    @Override
    public String toString() {
        return this.nlp.toString();
    }

    @Override
    public Number getLiteral() {
        return this.nlp.getLiteralValue();
    }

    @Override
    protected boolean isConstant(boolean literal) {
        return true;
    }

    public Class<? extends Number> getLiteralClass() {
        return this.nlp.getLiteralClass();
    }

    public boolean isInteger() {
        return this.nlp.isInteger();
    }

    void setNatural(String s) {
        this.nlp.setNatural(s);
    }

    void setReal(String s) {
        this.nlp.setReal(s);
    }

    @Override
    public Object jjtAccept(ParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}

