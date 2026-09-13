/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.parse;

import groovyjarjarantlr4.runtime.CharStream;
import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.Tool;
import groovyjarjarantlr4.v4.parse.ANTLRLexer;
import groovyjarjarantlr4.v4.tool.ErrorType;

public class ToolANTLRLexer
extends ANTLRLexer {
    public Tool tool;

    public ToolANTLRLexer(CharStream input, Tool tool) {
        super(input);
        this.tool = tool;
    }

    @Override
    public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
        String msg = this.getErrorMessage(e, tokenNames);
        this.tool.errMgr.syntaxError(ErrorType.SYNTAX_ERROR, this.getSourceName(), e.token, e, msg);
    }

    @Override
    public void grammarError(ErrorType etype, Token token, Object ... args) {
        this.tool.errMgr.grammarError(etype, this.getSourceName(), token, args);
    }
}

