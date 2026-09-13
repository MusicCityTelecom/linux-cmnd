/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import org.apache.commons.jexl3.parser.JexlNode;
import org.apache.commons.jexl3.parser.Parser;
import org.apache.commons.jexl3.parser.ParserVisitor;

public class ASTIdentifier
extends JexlNode {
    protected String name = null;
    protected int symbol = -1;
    protected int flags = 0;
    private static final int REDEFINED = 0;
    private static final int SHADED = 1;
    private static final int CAPTURED = 2;

    ASTIdentifier(int id) {
        super(id);
    }

    ASTIdentifier(Parser p, int id) {
        super(p, id);
    }

    @Override
    public String toString() {
        return this.name;
    }

    void setSymbol(String identifier) {
        if (identifier.charAt(0) == '#') {
            this.symbol = Integer.parseInt(identifier.substring(1));
        }
        this.name = identifier;
    }

    void setSymbol(int r, String identifier) {
        this.symbol = r;
        this.name = identifier;
    }

    public int getSymbol() {
        return this.symbol;
    }

    private static int set(int ordinal, int mask, boolean value) {
        return value ? mask | 1 << ordinal : mask & ~(1 << ordinal);
    }

    private static boolean isSet(int ordinal, int mask) {
        return (mask & 1 << ordinal) != 0;
    }

    public void setRedefined(boolean f) {
        this.flags = ASTIdentifier.set(0, this.flags, f);
    }

    public boolean isRedefined() {
        return ASTIdentifier.isSet(0, this.flags);
    }

    public void setShaded(boolean f) {
        this.flags = ASTIdentifier.set(1, this.flags, f);
    }

    public boolean isShaded() {
        return ASTIdentifier.isSet(1, this.flags);
    }

    public void setCaptured(boolean f) {
        this.flags = ASTIdentifier.set(2, this.flags, f);
    }

    public boolean isCaptured() {
        return ASTIdentifier.isSet(2, this.flags);
    }

    public String getName() {
        return this.name;
    }

    public String getNamespace() {
        return null;
    }

    @Override
    public Object jjtAccept(ParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}

