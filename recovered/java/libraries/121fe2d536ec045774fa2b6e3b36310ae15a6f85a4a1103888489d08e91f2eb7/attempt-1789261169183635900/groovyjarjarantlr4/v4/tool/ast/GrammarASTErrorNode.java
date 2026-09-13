/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool.ast;

import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.TokenStream;
import groovyjarjarantlr4.runtime.tree.CommonErrorNode;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;

public class GrammarASTErrorNode
extends GrammarAST {
    CommonErrorNode delegate;

    public GrammarASTErrorNode(TokenStream input, Token start, Token stop, RecognitionException e) {
        this.delegate = new CommonErrorNode(input, start, stop, e);
    }

    @Override
    public boolean isNil() {
        return this.delegate.isNil();
    }

    @Override
    public int getType() {
        return this.delegate.getType();
    }

    @Override
    public String getText() {
        return this.delegate.getText();
    }

    @Override
    public String toString() {
        return this.delegate.toString();
    }
}

