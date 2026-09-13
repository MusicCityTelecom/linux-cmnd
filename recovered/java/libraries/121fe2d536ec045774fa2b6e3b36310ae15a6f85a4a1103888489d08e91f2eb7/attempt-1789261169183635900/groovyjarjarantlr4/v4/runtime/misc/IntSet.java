/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.misc;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import java.util.List;

public interface IntSet {
    public void add(int var1);

    @NotNull
    public IntSet addAll(@Nullable IntSet var1);

    @Nullable
    public IntSet and(@Nullable IntSet var1);

    @Nullable
    public IntSet complement(@Nullable IntSet var1);

    @Nullable
    public IntSet or(@Nullable IntSet var1);

    @Nullable
    public IntSet subtract(@Nullable IntSet var1);

    public int size();

    public boolean isNil();

    public boolean equals(Object var1);

    public int getSingleElement();

    public boolean contains(int var1);

    public void remove(int var1);

    @NotNull
    public List<Integer> toList();

    public String toString();
}

