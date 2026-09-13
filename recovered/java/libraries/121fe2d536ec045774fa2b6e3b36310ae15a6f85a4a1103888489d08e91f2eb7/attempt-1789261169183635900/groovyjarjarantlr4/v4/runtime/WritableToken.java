/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.Token;

public interface WritableToken
extends Token {
    public void setText(String var1);

    public void setType(int var1);

    public void setLine(int var1);

    public void setCharPositionInLine(int var1);

    public void setChannel(int var1);

    public void setTokenIndex(int var1);
}

