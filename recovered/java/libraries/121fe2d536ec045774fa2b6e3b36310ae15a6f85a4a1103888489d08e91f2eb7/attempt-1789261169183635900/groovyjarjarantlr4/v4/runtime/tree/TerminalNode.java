/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.tree;

import groovyjarjarantlr4.v4.runtime.Token;
import groovyjarjarantlr4.v4.runtime.tree.ParseTree;
import groovyjarjarantlr4.v4.runtime.tree.RuleNode;

public interface TerminalNode
extends ParseTree {
    public Token getSymbol();

    @Override
    public RuleNode getParent();
}

