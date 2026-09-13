/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.core;

import java.util.List;
import java.util.Map;

public interface MultivaluedMap<K, V>
extends Map<K, List<V>> {
    public void putSingle(K var1, V var2);

    public void add(K var1, V var2);

    public V getFirst(K var1);

    public void addAll(K var1, V ... var2);

    public void addAll(K var1, List<V> var2);

    public void addFirst(K var1, V var2);

    public boolean equalsIgnoreValueOrder(MultivaluedMap<K, V> var1);
}

