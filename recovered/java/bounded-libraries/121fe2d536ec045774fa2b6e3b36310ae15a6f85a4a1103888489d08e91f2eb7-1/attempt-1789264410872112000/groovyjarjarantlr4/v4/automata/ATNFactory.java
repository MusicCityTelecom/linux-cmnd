/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.automata;

import groovyjarjarantlr4.v4.runtime.atn.ATN;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.BlockAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.PredAST;
import groovyjarjarantlr4.v4.tool.ast.TerminalAST;
import java.util.List;

public interface ATNFactory {
    @NotNull
    public ATN createATN();

    public void setCurrentRuleName(@NotNull String var1);

    public void setCurrentOuterAlt(int var1);

    @NotNull
    public Handle rule(@NotNull GrammarAST var1, @NotNull String var2, @NotNull Handle var3);

    @NotNull
    public ATNState newState();

    @NotNull
    public Handle label(@NotNull Handle var1);

    @NotNull
    public Handle listLabel(@NotNull Handle var1);

    @NotNull
    public Handle tokenRef(@NotNull TerminalAST var1);

    @NotNull
    public Handle set(@NotNull GrammarAST var1, @NotNull List<GrammarAST> var2, boolean var3);

    @NotNull
    public Handle charSetLiteral(@NotNull GrammarAST var1);

    @NotNull
    public Handle range(@NotNull GrammarAST var1, @NotNull GrammarAST var2);

    @NotNull
    public Handle stringLiteral(@NotNull TerminalAST var1);

    @NotNull
    public Handle ruleRef(@NotNull GrammarAST var1);

    @NotNull
    public Handle epsilon(@NotNull GrammarAST var1);

    @NotNull
    public Handle sempred(@NotNull PredAST var1);

    @NotNull
    public Handle action(@NotNull ActionAST var1);

    @NotNull
    public Handle action(@NotNull String var1);

    @NotNull
    public Handle alt(@NotNull List<Handle> var1);

    @NotNull
    public Handle block(@NotNull BlockAST var1, @NotNull GrammarAST var2, @NotNull List<Handle> var3);

    @NotNull
    public Handle optional(@NotNull GrammarAST var1, @NotNull Handle var2);

    @NotNull
    public Handle plus(@NotNull GrammarAST var1, @NotNull Handle var2);

    @NotNull
    public Handle star(@NotNull GrammarAST var1, @NotNull Handle var2);

    @NotNull
    public Handle wildcard(@NotNull GrammarAST var1);

    @NotNull
    public Handle lexerAltCommands(@NotNull Handle var1, @NotNull Handle var2);

    @NotNull
    public Handle lexerCallCommand(@NotNull GrammarAST var1, @NotNull GrammarAST var2);

    @NotNull
    public Handle lexerCommand(@NotNull GrammarAST var1);

    public static class Handle {
        public ATNState left;
        public ATNState right;

        public Handle(ATNState left, ATNState right) {
            this.left = left;
            this.right = right;
        }

        public String toString() {
            return "(" + this.left + "," + this.right + ")";
        }
    }
}

