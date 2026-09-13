/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.tree;

import groovyjarjarantlr4.v4.runtime.Token;
import groovyjarjarantlr4.v4.runtime.tree.ErrorNode;
import groovyjarjarantlr4.v4.runtime.tree.ParseTreeVisitor;
import groovyjarjarantlr4.v4.runtime.tree.TerminalNodeImpl;

public class ErrorNodeImpl
extends TerminalNodeImpl
implements ErrorNode {
    public ErrorNodeImpl(Token token) {
        super(token);
    }

    @Override
    public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
        return visitor.visitErrorNode(this);
    }
}

