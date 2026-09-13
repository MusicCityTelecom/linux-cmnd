/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import java.util.regex.Pattern;
import org.apache.commons.jexl3.parser.JexlNode;
import org.apache.commons.jexl3.parser.Parser;
import org.apache.commons.jexl3.parser.ParserVisitor;

public final class ASTRegexLiteral
extends JexlNode
implements JexlNode.Constant<Pattern> {
    private Pattern literal = null;

    ASTRegexLiteral(int id) {
        super(id);
    }

    ASTRegexLiteral(Parser p, int id) {
        super(p, id);
    }

    @Override
    public String toString() {
        return this.literal != null ? this.literal.toString() : "";
    }

    @Override
    public Pattern getLiteral() {
        return this.literal;
    }

    @Override
    protected boolean isConstant(boolean literal) {
        return true;
    }

    void setLiteral(String literal) {
        this.literal = Pattern.compile(literal);
    }

    @Override
    public Object jjtAccept(ParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}

