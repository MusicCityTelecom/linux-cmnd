/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.tool.ANTLRMessage;
import groovyjarjarantlr4.v4.tool.ErrorType;

public class GrammarSemanticsMessage
extends ANTLRMessage {
    public GrammarSemanticsMessage(ErrorType etype, String fileName, Token offendingToken, Object ... args) {
        super(etype, offendingToken, args);
        this.fileName = fileName;
        if (offendingToken != null) {
            this.line = offendingToken.getLine();
            this.charPosition = offendingToken.getCharPositionInLine();
        }
    }
}

