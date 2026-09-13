/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool.ast;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.analysis.LeftRecursiveRuleAltInfo;
import groovyjarjarantlr4.v4.tool.Alternative;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTVisitor;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTWithOptions;

public class AltAST
extends GrammarASTWithOptions {
    public Alternative alt;
    public LeftRecursiveRuleAltInfo leftRecursiveAltInfo;
    public GrammarAST altLabel;

    public AltAST(AltAST node) {
        super(node);
        this.alt = node.alt;
        this.altLabel = node.altLabel;
        this.leftRecursiveAltInfo = node.leftRecursiveAltInfo;
    }

    public AltAST(Token t) {
        super(t);
    }

    public AltAST(int type) {
        super(type);
    }

    public AltAST(int type, Token t) {
        super(type, t);
    }

    public AltAST(int type, Token t, String text) {
        super(type, t, text);
    }

    @Override
    public AltAST dupNode() {
        return new AltAST(this);
    }

    @Override
    public Object visit(GrammarASTVisitor v) {
        return v.visit(this);
    }
}

