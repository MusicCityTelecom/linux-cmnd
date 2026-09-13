/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import java.util.Map;
import java.util.Set;

public interface Table<R, C, V> {
    public boolean contains(Object var1, Object var2);

    public boolean containsRow(Object var1);

    public boolean containsColumn(Object var1);

    public boolean containsValue(Object var1);

    public V get(Object var1, Object var2);

    public int size();

    public boolean equals(Object var1);

    public int hashCode();

    public void clear();

    public V put(R var1, C var2, V var3);

    public void putAll(Table<? extends R, ? extends C, ? extends V> var1);

    public V remove(Object var1, Object var2);

    public Map<C, V> row(R var1);

    public Map<R, V> column(C var1);

    public Set<Cell<R, C, V>> cellSet();

    public Set<R> rowKeySet();

    public Set<C> columnKeySet();

    public Map<R, Map<C, V>> rowMap();

    public Map<C, Map<R, V>> columnMap();

    public static interface Cell<R, C, V> {
        public R getRowKey();

        public C getColumnKey();

        public V getValue();

        public boolean equals(Object var1);

        public int hashCode();
    }
}

