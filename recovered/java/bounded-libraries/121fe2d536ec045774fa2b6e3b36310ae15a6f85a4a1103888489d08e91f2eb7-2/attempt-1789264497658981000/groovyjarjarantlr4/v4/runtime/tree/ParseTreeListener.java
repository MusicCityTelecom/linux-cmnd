/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.tree;

import groovyjarjarantlr4.v4.runtime.ParserRuleContext;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.tree.ErrorNode;
import groovyjarjarantlr4.v4.runtime.tree.TerminalNode;

public interface ParseTreeListener {
    public void visitTerminal(@NotNull TerminalNode var1);

    public void visitErrorNode(@NotNull ErrorNode var1);

    public void enterEveryRule(@NotNull ParserRuleContext var1);

    public void exitEveryRule(@NotNull ParserRuleContext var1);
}

