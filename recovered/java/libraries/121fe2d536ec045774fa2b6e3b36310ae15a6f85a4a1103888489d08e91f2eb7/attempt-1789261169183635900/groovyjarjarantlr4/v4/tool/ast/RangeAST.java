/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool.ast;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTVisitor;
import groovyjarjarantlr4.v4.tool.ast.RuleElementAST;

public class RangeAST
extends GrammarAST
implements RuleElementAST {
    public RangeAST(RangeAST node) {
        super(node);
    }

    public RangeAST(Token t) {
        super(t);
    }

    @Override
    public RangeAST dupNode() {
        return new RangeAST(this);
    }

    @Override
    public Object visit(GrammarASTVisitor v) {
        return v.visit(this);
    }
}

