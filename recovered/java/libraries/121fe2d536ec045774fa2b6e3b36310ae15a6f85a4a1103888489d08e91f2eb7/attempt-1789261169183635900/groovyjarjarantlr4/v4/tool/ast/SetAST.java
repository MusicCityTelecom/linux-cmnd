/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool.ast;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTVisitor;
import groovyjarjarantlr4.v4.tool.ast.RuleElementAST;

public class SetAST
extends GrammarAST
implements RuleElementAST {
    public SetAST(SetAST node) {
        super(node);
    }

    public SetAST(int type, Token t, String text) {
        super(type, t, text);
    }

    @Override
    public SetAST dupNode() {
        return new SetAST(this);
    }

    @Override
    public Object visit(GrammarASTVisitor v) {
        return v.visit(this);
    }
}

