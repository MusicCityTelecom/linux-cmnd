/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.tree;

import groovyjarjarantlr4.v4.runtime.RuleContext;
import groovyjarjarantlr4.v4.runtime.tree.ParseTree;

public interface RuleNode
extends ParseTree {
    public RuleContext getRuleContext();

    @Override
    public RuleNode getParent();
}

