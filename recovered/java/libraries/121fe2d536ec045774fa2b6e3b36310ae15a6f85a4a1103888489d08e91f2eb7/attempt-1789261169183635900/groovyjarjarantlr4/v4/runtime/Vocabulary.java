/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;

public interface Vocabulary {
    public int getMaxTokenType();

    @Nullable
    public String getLiteralName(int var1);

    @Nullable
    public String getSymbolicName(int var1);

    @NotNull
    public String getDisplayName(int var1);
}

