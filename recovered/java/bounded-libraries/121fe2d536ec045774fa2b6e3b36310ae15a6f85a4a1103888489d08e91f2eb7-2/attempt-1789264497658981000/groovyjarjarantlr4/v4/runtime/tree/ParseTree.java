/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.tree;

import groovyjarjarantlr4.v4.runtime.Parser;
import groovyjarjarantlr4.v4.runtime.tree.ParseTreeVisitor;
import groovyjarjarantlr4.v4.runtime.tree.SyntaxTree;

public interface ParseTree
extends SyntaxTree {
    @Override
    public ParseTree getParent();

    @Override
    public ParseTree getChild(int var1);

    public <T> T accept(ParseTreeVisitor<? extends T> var1);

    public String getText();

    public String toStringTree(Parser var1);
}

