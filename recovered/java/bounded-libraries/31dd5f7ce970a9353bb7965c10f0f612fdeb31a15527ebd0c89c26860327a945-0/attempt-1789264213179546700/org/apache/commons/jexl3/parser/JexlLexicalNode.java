/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import org.apache.commons.jexl3.internal.LexicalScope;
import org.apache.commons.jexl3.parser.JexlNode;
import org.apache.commons.jexl3.parser.JexlParser;
import org.apache.commons.jexl3.parser.Parser;

public class JexlLexicalNode
extends JexlNode
implements JexlParser.LexicalUnit {
    private LexicalScope locals = null;

    public JexlLexicalNode(int id) {
        super(id);
    }

    public JexlLexicalNode(Parser p, int id) {
        super(p, id);
    }

    @Override
    public boolean declareSymbol(int symbol) {
        if (this.locals == null) {
            this.locals = new LexicalScope();
        }
        return this.locals.addSymbol(symbol);
    }

    @Override
    public int getSymbolCount() {
        return this.locals == null ? 0 : this.locals.getSymbolCount();
    }

    @Override
    public boolean hasSymbol(int symbol) {
        return this.locals != null && this.locals.hasSymbol(symbol);
    }

    @Override
    public LexicalScope getLexicalScope() {
        return this.locals;
    }
}

