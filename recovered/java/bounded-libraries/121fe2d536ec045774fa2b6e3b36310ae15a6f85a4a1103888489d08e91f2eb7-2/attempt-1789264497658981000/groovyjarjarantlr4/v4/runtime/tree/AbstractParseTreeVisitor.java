/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.tree;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.tree.ErrorNode;
import groovyjarjarantlr4.v4.runtime.tree.ParseTree;
import groovyjarjarantlr4.v4.runtime.tree.ParseTreeVisitor;
import groovyjarjarantlr4.v4.runtime.tree.RuleNode;
import groovyjarjarantlr4.v4.runtime.tree.TerminalNode;

public abstract class AbstractParseTreeVisitor<Result>
implements ParseTreeVisitor<Result> {
    @Override
    public Result visit(@NotNull ParseTree tree) {
        return (Result)tree.accept(this);
    }

    @Override
    public Result visitChildren(@NotNull RuleNode node) {
        Result result = this.defaultResult();
        int n = node.getChildCount();
        for (int i = 0; i < n && this.shouldVisitNextChild(node, result); ++i) {
            ParseTree c = node.getChild(i);
            Object childResult = c.accept(this);
            result = this.aggregateResult(result, childResult);
        }
        return result;
    }

    @Override
    public Result visitTerminal(@NotNull TerminalNode node) {
        return this.defaultResult();
    }

    @Override
    public Result visitErrorNode(@NotNull ErrorNode node) {
        return this.defaultResult();
    }

    protected Result defaultResult() {
        return null;
    }

    protected Result aggregateResult(Result aggregate, Result nextResult) {
        return nextResult;
    }

    protected boolean shouldVisitNextChild(@NotNull RuleNode node, Result currentResult) {
        return true;
    }
}

