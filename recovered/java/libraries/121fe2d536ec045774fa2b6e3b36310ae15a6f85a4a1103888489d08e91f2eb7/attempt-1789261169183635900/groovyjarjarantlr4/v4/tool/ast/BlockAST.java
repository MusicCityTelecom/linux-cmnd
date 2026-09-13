/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool.ast;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTVisitor;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTWithOptions;
import groovyjarjarantlr4.v4.tool.ast.RuleElementAST;
import java.util.HashMap;
import java.util.Map;

public class BlockAST
extends GrammarASTWithOptions
implements RuleElementAST {
    public static final Map<String, String> defaultBlockOptions = new HashMap<String, String>();
    public static final Map<String, String> defaultLexerBlockOptions = new HashMap<String, String>();

    public BlockAST(BlockAST node) {
        super(node);
    }

    public BlockAST(Token t) {
        super(t);
    }

    public BlockAST(int type) {
        super(type);
    }

    public BlockAST(int type, Token t) {
        super(type, t);
    }

    public BlockAST(int type, Token t, String text) {
        super(type, t, text);
    }

    @Override
    public BlockAST dupNode() {
        return new BlockAST(this);
    }

    @Override
    public Object visit(GrammarASTVisitor v) {
        return v.visit(this);
    }
}

