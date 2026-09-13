/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.dfa;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import java.util.Map;
import java.util.Set;

public interface EdgeMap<T> {
    public int size();

    public boolean isEmpty();

    public boolean containsKey(int var1);

    @Nullable
    public T get(int var1);

    @NotNull
    public EdgeMap<T> put(int var1, @Nullable T var2);

    @NotNull
    public EdgeMap<T> remove(int var1);

    @NotNull
    public EdgeMap<T> putAll(@NotNull EdgeMap<? extends T> var1);

    @NotNull
    public EdgeMap<T> clear();

    @NotNull
    public Map<Integer, T> toMap();

    @NotNull
    public Set<Map.Entry<Integer, T>> entrySet();
}

