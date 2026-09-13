/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool.ast;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.tool.AttributeResolver;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTVisitor;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTWithOptions;
import groovyjarjarantlr4.v4.tool.ast.RuleElementAST;
import java.util.List;

public class ActionAST
extends GrammarASTWithOptions
implements RuleElementAST {
    public AttributeResolver resolver;
    public List<Token> chunks;

    public ActionAST(ActionAST node) {
        super(node);
        this.resolver = node.resolver;
        this.chunks = node.chunks;
    }

    public ActionAST(Token t) {
        super(t);
    }

    public ActionAST(int type) {
        super(type);
    }

    public ActionAST(int type, Token t) {
        super(type, t);
    }

    @Override
    public ActionAST dupNode() {
        return new ActionAST(this);
    }

    @Override
    public Object visit(GrammarASTVisitor v) {
        return v.visit(this);
    }
}

