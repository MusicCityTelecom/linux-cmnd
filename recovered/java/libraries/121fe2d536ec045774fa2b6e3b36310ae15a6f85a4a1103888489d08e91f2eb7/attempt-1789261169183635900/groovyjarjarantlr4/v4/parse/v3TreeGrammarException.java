/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.parse;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.runtime.misc.ParseCancellationException;

public class v3TreeGrammarException
extends ParseCancellationException {
    private static final long serialVersionUID = -8383611621498312969L;
    public Token location;

    public v3TreeGrammarException(Token location) {
        this.location = location;
    }
}

