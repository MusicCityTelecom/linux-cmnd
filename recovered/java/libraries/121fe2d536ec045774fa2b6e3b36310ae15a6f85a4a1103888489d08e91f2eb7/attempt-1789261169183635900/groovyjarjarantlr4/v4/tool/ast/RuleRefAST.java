/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool.ast;

import groovyjarjarantlr4.runtime.CommonToken;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTVisitor;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTWithOptions;
import groovyjarjarantlr4.v4.tool.ast.RuleElementAST;

public class RuleRefAST
extends GrammarASTWithOptions
implements RuleElementAST {
    public RuleRefAST(RuleRefAST node) {
        super(node);
    }

    public RuleRefAST(Token t) {
        super(t);
    }

    public RuleRefAST(int type) {
        super(type);
    }

    public RuleRefAST(int type, Token t) {
        super(type, t);
    }

    @Override
    public RuleRefAST dupNode() {
        RuleRefAST r = new RuleRefAST(this);
        r.token = this.token;
        this.token = new CommonToken(r.token);
        return r;
    }

    @Override
    public Object visit(GrammarASTVisitor v) {
        return v.visit(this);
    }
}

