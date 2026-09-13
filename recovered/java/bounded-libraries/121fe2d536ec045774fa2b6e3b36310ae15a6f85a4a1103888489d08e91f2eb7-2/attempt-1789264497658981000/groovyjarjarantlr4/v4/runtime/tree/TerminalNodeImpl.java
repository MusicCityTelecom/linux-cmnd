/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.tree;

import groovyjarjarantlr4.v4.runtime.Parser;
import groovyjarjarantlr4.v4.runtime.RuleContext;
import groovyjarjarantlr4.v4.runtime.Token;
import groovyjarjarantlr4.v4.runtime.misc.Interval;
import groovyjarjarantlr4.v4.runtime.tree.ParseTree;
import groovyjarjarantlr4.v4.runtime.tree.ParseTreeVisitor;
import groovyjarjarantlr4.v4.runtime.tree.RuleNode;
import groovyjarjarantlr4.v4.runtime.tree.TerminalNode;

public class TerminalNodeImpl
implements TerminalNode {
    public Token symbol;
    public RuleNode parent;

    public TerminalNodeImpl(Token symbol) {
        this.symbol = symbol;
    }

    @Override
    public ParseTree getChild(int i) {
        return null;
    }

    @Override
    public Token getSymbol() {
        return this.symbol;
    }

    @Override
    public RuleNode getParent() {
        return this.parent;
    }

    public void setParent(RuleContext parent) {
        this.parent = parent;
    }

    @Override
    public Token getPayload() {
        return this.symbol;
    }

    @Override
    public Interval getSourceInterval() {
        if (this.symbol != null) {
            int tokenIndex = this.symbol.getTokenIndex();
            return new Interval(tokenIndex, tokenIndex);
        }
        return Interval.INVALID;
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
        return visitor.visitTerminal(this);
    }

    @Override
    public String getText() {
        if (this.symbol != null) {
            return this.symbol.getText();
        }
        return null;
    }

    @Override
    public String toStringTree(Parser parser) {
        return this.toString();
    }

    public String toString() {
        if (this.symbol != null) {
            if (this.symbol.getType() == -1) {
                return "<EOF>";
            }
            return this.symbol.getText();
        }
        return "<null>";
    }

    @Override
    public String toStringTree() {
        return this.toString();
    }
}

