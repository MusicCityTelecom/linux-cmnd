/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool.ast;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTVisitor;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTWithOptions;
import groovyjarjarantlr4.v4.tool.ast.RuleElementAST;

public class TerminalAST
extends GrammarASTWithOptions
implements RuleElementAST {
    public TerminalAST(TerminalAST node) {
        super(node);
    }

    public TerminalAST(Token t) {
        super(t);
    }

    public TerminalAST(int type) {
        super(type);
    }

    public TerminalAST(int type, Token t) {
        super(type, t);
    }

    @Override
    public TerminalAST dupNode() {
        return new TerminalAST(this);
    }

    @Override
    public Object visit(GrammarASTVisitor v) {
        return v.visit(this);
    }
}

