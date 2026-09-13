/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import org.apache.commons.jexl3.parser.JexlNode;
import org.apache.commons.jexl3.parser.Parser;
import org.apache.commons.jexl3.parser.ParserVisitor;

public class ASTIdentifierAccess
extends JexlNode {
    private String name = null;
    private Integer identifier = null;

    ASTIdentifierAccess(int id) {
        super(id);
    }

    ASTIdentifierAccess(Parser p, int id) {
        super(p, id);
    }

    void setIdentifier(String id) {
        this.name = id;
        this.identifier = ASTIdentifierAccess.parseIdentifier(id);
    }

    @Override
    public boolean isGlobalVar() {
        return !this.isSafe() && !this.isExpression();
    }

    public boolean isSafe() {
        return false;
    }

    public boolean isExpression() {
        return false;
    }

    public static Integer parseIdentifier(String id) {
        if (id != null) {
            int length = id.length();
            int val = 0;
            for (int i = 0; i < length; ++i) {
                char c = id.charAt(i);
                if (c == '0') {
                    if (length == 1) {
                        return 0;
                    }
                    if (val == 0) {
                        return null;
                    }
                } else if (c < '0' || c > '9') {
                    return null;
                }
                val *= 10;
                val += c - 48;
            }
            return val;
        }
        return null;
    }

    public Object getIdentifier() {
        return this.identifier != null ? this.identifier : this.name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public Object jjtAccept(ParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    @Override
    public String toString() {
        return this.name;
    }
}

