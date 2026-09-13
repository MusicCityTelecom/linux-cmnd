/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.tool.ANTLRMessage;
import groovyjarjarantlr4.v4.tool.ErrorType;

public class ToolMessage
extends ANTLRMessage {
    public ToolMessage(ErrorType errorType) {
        super(errorType);
    }

    public ToolMessage(ErrorType errorType, Object ... args) {
        super(errorType, null, Token.INVALID_TOKEN, args);
    }

    public ToolMessage(ErrorType errorType, Throwable e, Object ... args) {
        super(errorType, e, Token.INVALID_TOKEN, args);
    }
}

