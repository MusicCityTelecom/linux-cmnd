/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool.ast;

import groovyjarjarantlr4.v4.tool.ast.AltAST;
import groovyjarjarantlr4.v4.tool.ast.BlockAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarRootAST;
import groovyjarjarantlr4.v4.tool.ast.NotAST;
import groovyjarjarantlr4.v4.tool.ast.OptionalBlockAST;
import groovyjarjarantlr4.v4.tool.ast.PlusBlockAST;
import groovyjarjarantlr4.v4.tool.ast.PredAST;
import groovyjarjarantlr4.v4.tool.ast.RangeAST;
import groovyjarjarantlr4.v4.tool.ast.RuleAST;
import groovyjarjarantlr4.v4.tool.ast.RuleRefAST;
import groovyjarjarantlr4.v4.tool.ast.SetAST;
import groovyjarjarantlr4.v4.tool.ast.StarBlockAST;
import groovyjarjarantlr4.v4.tool.ast.TerminalAST;

public interface GrammarASTVisitor {
    public Object visit(GrammarAST var1);

    public Object visit(GrammarRootAST var1);

    public Object visit(RuleAST var1);

    public Object visit(BlockAST var1);

    public Object visit(OptionalBlockAST var1);

    public Object visit(PlusBlockAST var1);

    public Object visit(StarBlockAST var1);

    public Object visit(AltAST var1);

    public Object visit(NotAST var1);

    public Object visit(PredAST var1);

    public Object visit(RangeAST var1);

    public Object visit(SetAST var1);

    public Object visit(RuleRefAST var1);

    public Object visit(TerminalAST var1);
}

