/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime.memoize;

public interface MemoizeCache<K, V> {
    public V put(K var1, V var2);

    public V get(K var1);

    default public V getAndPut(K key, ValueProvider<? super K, ? extends V> valueProvider) {
        V value = this.get(key);
        if (null == value) {
            value = valueProvider.provide(key);
            this.put(key, value);
        }
        return value;
    }

    public void cleanUpNullReferences();

    @FunctionalInterface
    public static interface ValueProvider<K, V> {
        public V provide(K var1);
    }
}

