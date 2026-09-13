/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime;

import groovyjarjarantlr4.runtime.Token;

public interface TokenSource {
    public Token nextToken();

    public String getSourceName();
}

