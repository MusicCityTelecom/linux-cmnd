/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.tree;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.tree.ErrorNode;
import groovyjarjarantlr4.v4.runtime.tree.ParseTree;
import groovyjarjarantlr4.v4.runtime.tree.RuleNode;
import groovyjarjarantlr4.v4.runtime.tree.TerminalNode;

public interface ParseTreeVisitor<Result> {
    public Result visit(@NotNull ParseTree var1);

    public Result visitChildren(@NotNull RuleNode var1);

    public Result visitTerminal(@NotNull TerminalNode var1);

    public Result visitErrorNode(@NotNull ErrorNode var1);
}

