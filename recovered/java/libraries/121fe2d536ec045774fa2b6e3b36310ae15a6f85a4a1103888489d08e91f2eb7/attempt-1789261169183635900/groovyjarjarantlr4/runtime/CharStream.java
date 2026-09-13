/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime;

import groovyjarjarantlr4.runtime.IntStream;

public interface CharStream
extends IntStream {
    public static final int EOF = -1;

    public String substring(int var1, int var2);

    public int LT(int var1);

    public int getLine();

    public void setLine(int var1);

    public void setCharPositionInLine(int var1);

    public int getCharPositionInLine();
}

