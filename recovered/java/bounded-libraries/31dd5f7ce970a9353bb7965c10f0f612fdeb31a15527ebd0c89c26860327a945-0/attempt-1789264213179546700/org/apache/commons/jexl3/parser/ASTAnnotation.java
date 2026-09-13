/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import org.apache.commons.jexl3.parser.JexlNode;
import org.apache.commons.jexl3.parser.Parser;
import org.apache.commons.jexl3.parser.ParserVisitor;

public class ASTAnnotation
extends JexlNode {
    private String name = null;

    ASTAnnotation(int id) {
        super(id);
    }

    ASTAnnotation(Parser p, int id) {
        super(p, id);
    }

    @Override
    public String toString() {
        return this.name;
    }

    void setName(String identifier) {
        this.name = identifier.charAt(0) == '@' ? identifier.substring(1) : identifier;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public Object jjtAccept(ParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}

