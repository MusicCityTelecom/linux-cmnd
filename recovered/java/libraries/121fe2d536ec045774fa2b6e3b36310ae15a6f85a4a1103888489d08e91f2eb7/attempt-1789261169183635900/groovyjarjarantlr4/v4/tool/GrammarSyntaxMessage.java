/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool;

import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.tool.ANTLRMessage;
import groovyjarjarantlr4.v4.tool.ErrorType;

public class GrammarSyntaxMessage
extends ANTLRMessage {
    public GrammarSyntaxMessage(ErrorType etype, String fileName, Token offendingToken, RecognitionException antlrException, Object ... args) {
        super(etype, antlrException, offendingToken, args);
        this.fileName = fileName;
        this.offendingToken = offendingToken;
        if (offendingToken != null) {
            this.line = offendingToken.getLine();
            this.charPosition = offendingToken.getCharPositionInLine();
        }
    }

    @Override
    public RecognitionException getCause() {
        return (RecognitionException)super.getCause();
    }
}

