/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool.ast;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTVisitor;
import groovyjarjarantlr4.v4.tool.ast.RuleElementAST;

public class NotAST
extends GrammarAST
implements RuleElementAST {
    public NotAST(NotAST node) {
        super(node);
    }

    public NotAST(int type, Token t) {
        super(type, t);
    }

    @Override
    public NotAST dupNode() {
        return new NotAST(this);
    }

    @Override
    public Object visit(GrammarASTVisitor v) {
        return v.visit(this);
    }
}

