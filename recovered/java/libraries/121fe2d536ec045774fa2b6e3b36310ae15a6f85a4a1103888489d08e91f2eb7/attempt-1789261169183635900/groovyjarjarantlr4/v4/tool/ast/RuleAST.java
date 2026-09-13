/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool.ast;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.tree.Tree;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTVisitor;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTWithOptions;

public class RuleAST
extends GrammarASTWithOptions {
    public RuleAST(RuleAST node) {
        super(node);
    }

    public RuleAST(Token t) {
        super(t);
    }

    public RuleAST(int type) {
        super(type);
    }

    public boolean isLexerRule() {
        String name = this.getRuleName();
        return name != null && Grammar.isTokenName(name);
    }

    public String getRuleName() {
        GrammarAST nameNode = (GrammarAST)this.getChild(0);
        if (nameNode != null) {
            return nameNode.getText();
        }
        return null;
    }

    @Override
    public RuleAST dupNode() {
        return new RuleAST(this);
    }

    public ActionAST getLexerAction() {
        Tree onlyAlt;
        Tree lastChild;
        Tree blk = this.getFirstChildWithType(78);
        if (blk.getChildCount() == 1 && (lastChild = (onlyAlt = blk.getChild(0)).getChild(onlyAlt.getChildCount() - 1)).getType() == 4) {
            return (ActionAST)lastChild;
        }
        return null;
    }

    @Override
    public Object visit(GrammarASTVisitor v) {
        return v.visit(this);
    }
}

