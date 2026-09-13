/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.IntStream;
import groovyjarjarantlr4.v4.runtime.misc.Interval;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;

public interface CharStream
extends IntStream {
    @NotNull
    public String getText(@NotNull Interval var1);
}

