/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.parse;

import groovyjarjarantlr4.runtime.IntStream;
import groovyjarjarantlr4.runtime.RecognitionException;

public class v4ParserException
extends RecognitionException {
    private static final long serialVersionUID = -7954962343881278338L;
    public String msg;

    public v4ParserException() {
    }

    public v4ParserException(String msg, IntStream input) {
        super(input);
        this.msg = msg;
    }
}

