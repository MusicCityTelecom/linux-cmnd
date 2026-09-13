/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool.ast;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTVisitor;

public class PredAST
extends ActionAST {
    public PredAST(PredAST node) {
        super(node);
    }

    public PredAST(Token t) {
        super(t);
    }

    public PredAST(int type) {
        super(type);
    }

    public PredAST(int type, Token t) {
        super(type, t);
    }

    @Override
    public PredAST dupNode() {
        return new PredAST(this);
    }

    @Override
    public Object visit(GrammarASTVisitor v) {
        return v.visit(this);
    }
}

