/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import org.apache.commons.jexl3.internal.Debugger;
import org.apache.commons.jexl3.parser.ASTMapEntry;
import org.apache.commons.jexl3.parser.JexlNode;
import org.apache.commons.jexl3.parser.Parser;
import org.apache.commons.jexl3.parser.ParserVisitor;

public final class ASTMapLiteral
extends JexlNode {
    private boolean constant = false;

    ASTMapLiteral(int id) {
        super(id);
    }

    ASTMapLiteral(Parser p, int id) {
        super(p, id);
    }

    @Override
    public String toString() {
        Debugger dbg = new Debugger();
        return dbg.data(this);
    }

    @Override
    protected boolean isConstant(boolean literal) {
        return this.constant;
    }

    @Override
    public void jjtClose() {
        this.constant = true;
        for (int c = 0; c < this.jjtGetNumChildren() && this.constant; ++c) {
            JexlNode child = this.jjtGetChild(c);
            if (child instanceof ASTMapEntry) {
                this.constant = child.isConstant(true);
                continue;
            }
            if (child.isConstant()) continue;
            this.constant = false;
        }
    }

    @Override
    public Object jjtAccept(ParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}

