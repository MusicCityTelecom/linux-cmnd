/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.parse;

import groovyjarjarantlr4.runtime.CharStream;
import groovyjarjarantlr4.runtime.CommonToken;
import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.TokenStream;
import groovyjarjarantlr4.runtime.tree.CommonTreeAdaptor;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTErrorNode;
import groovyjarjarantlr4.v4.tool.ast.RuleAST;
import groovyjarjarantlr4.v4.tool.ast.TerminalAST;

public class GrammarASTAdaptor
extends CommonTreeAdaptor {
    CharStream input;

    public GrammarASTAdaptor() {
    }

    public GrammarASTAdaptor(CharStream input) {
        this.input = input;
    }

    @Override
    public GrammarAST nil() {
        return (GrammarAST)super.nil();
    }

    @Override
    public GrammarAST create(Token token) {
        return new GrammarAST(token);
    }

    @Override
    public GrammarAST create(int tokenType, String text) {
        GrammarAST t = tokenType == 94 ? new RuleAST(new CommonToken(tokenType, text)) : (tokenType == 62 ? new TerminalAST(new CommonToken(tokenType, text)) : (GrammarAST)super.create(tokenType, text));
        t.token.setInputStream(this.input);
        return t;
    }

    @Override
    public GrammarAST create(int tokenType, Token fromToken, String text) {
        return (GrammarAST)super.create(tokenType, fromToken, text);
    }

    @Override
    public GrammarAST create(int tokenType, Token fromToken) {
        return (GrammarAST)super.create(tokenType, fromToken);
    }

    @Override
    public GrammarAST dupNode(Object t) {
        if (t == null) {
            return null;
        }
        return ((GrammarAST)t).dupNode();
    }

    @Override
    public GrammarASTErrorNode errorNode(TokenStream input, Token start, Token stop, RecognitionException e) {
        return new GrammarASTErrorNode(input, start, stop, e);
    }
}

