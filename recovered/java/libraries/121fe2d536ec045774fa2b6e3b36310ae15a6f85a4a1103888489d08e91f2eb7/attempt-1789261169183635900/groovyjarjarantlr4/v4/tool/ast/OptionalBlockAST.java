/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool.ast;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTVisitor;
import groovyjarjarantlr4.v4.tool.ast.QuantifierAST;
import groovyjarjarantlr4.v4.tool.ast.RuleElementAST;

public class OptionalBlockAST
extends GrammarAST
implements RuleElementAST,
QuantifierAST {
    private final boolean _greedy;

    public OptionalBlockAST(OptionalBlockAST node) {
        super(node);
        this._greedy = node._greedy;
    }

    public OptionalBlockAST(int type, Token t, Token nongreedy) {
        super(type, t);
        this._greedy = nongreedy == null;
    }

    @Override
    public boolean isGreedy() {
        return this._greedy;
    }

    @Override
    public OptionalBlockAST dupNode() {
        return new OptionalBlockAST(this);
    }

    @Override
    public Object visit(GrammarASTVisitor v) {
        return v.visit(this);
    }
}

