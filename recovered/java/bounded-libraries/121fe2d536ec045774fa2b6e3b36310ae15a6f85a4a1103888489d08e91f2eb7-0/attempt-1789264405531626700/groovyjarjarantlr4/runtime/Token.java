/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime;

import groovyjarjarantlr4.runtime.CharStream;
import groovyjarjarantlr4.runtime.CommonToken;

public interface Token {
    public static final int EOR_TOKEN_TYPE = 1;
    public static final int DOWN = 2;
    public static final int UP = 3;
    public static final int MIN_TOKEN_TYPE = 4;
    public static final int EOF = -1;
    public static final int INVALID_TOKEN_TYPE = 0;
    public static final Token INVALID_TOKEN = new CommonToken(0);
    public static final Token SKIP_TOKEN = new CommonToken(0);
    public static final int DEFAULT_CHANNEL = 0;
    public static final int HIDDEN_CHANNEL = 99;

    public String getText();

    public void setText(String var1);

    public int getType();

    public void setType(int var1);

    public int getLine();

    public void setLine(int var1);

    public int getCharPositionInLine();

    public void setCharPositionInLine(int var1);

    public int getChannel();

    public void setChannel(int var1);

    public int getTokenIndex();

    public void setTokenIndex(int var1);

    public CharStream getInputStream();

    public void setInputStream(CharStream var1);
}

