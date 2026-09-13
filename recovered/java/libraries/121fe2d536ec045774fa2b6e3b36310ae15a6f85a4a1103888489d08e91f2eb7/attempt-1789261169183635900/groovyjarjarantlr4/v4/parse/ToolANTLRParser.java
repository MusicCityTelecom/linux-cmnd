/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.parse;

import groovyjarjarantlr4.runtime.NoViableAltException;
import groovyjarjarantlr4.runtime.Parser;
import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.TokenStream;
import groovyjarjarantlr4.v4.Tool;
import groovyjarjarantlr4.v4.parse.ANTLRParser;
import groovyjarjarantlr4.v4.parse.v4ParserException;
import groovyjarjarantlr4.v4.tool.ErrorType;

public class ToolANTLRParser
extends ANTLRParser {
    public Tool tool;

    public ToolANTLRParser(TokenStream input, Tool tool) {
        super(input);
        this.tool = tool;
    }

    @Override
    public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
        String msg = this.getParserErrorMessage(this, e);
        if (!this.paraphrases.isEmpty()) {
            String paraphrase = (String)this.paraphrases.peek();
            msg = msg + " while " + paraphrase;
        }
        this.tool.errMgr.syntaxError(ErrorType.SYNTAX_ERROR, this.getSourceName(), e.token, e, msg);
    }

    public String getParserErrorMessage(Parser parser, RecognitionException e) {
        String msg;
        if (e instanceof NoViableAltException) {
            String name = parser.getTokenErrorDisplay(e.token);
            msg = name + " came as a complete surprise to me";
        } else {
            msg = e instanceof v4ParserException ? ((v4ParserException)e).msg : parser.getErrorMessage(e, parser.getTokenNames());
        }
        return msg;
    }

    @Override
    public void grammarError(ErrorType etype, Token token, Object ... args) {
        this.tool.errMgr.grammarError(etype, this.getSourceName(), token, args);
    }
}

