/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime;

import groovyjarjarantlr4.runtime.IntStream;
import groovyjarjarantlr4.runtime.MismatchedTokenException;
import groovyjarjarantlr4.runtime.Token;

public class UnwantedTokenException
extends MismatchedTokenException {
    public UnwantedTokenException() {
    }

    public UnwantedTokenException(int expecting, IntStream input) {
        super(expecting, input);
    }

    public Token getUnexpectedToken() {
        return this.token;
    }

    public String toString() {
        String exp = ", expected " + this.expecting;
        if (this.expecting == 0) {
            exp = "";
        }
        if (this.token == null) {
            return "UnwantedTokenException(found=" + null + exp + ")";
        }
        return "UnwantedTokenException(found=" + this.token.getText() + exp + ")";
    }
}

