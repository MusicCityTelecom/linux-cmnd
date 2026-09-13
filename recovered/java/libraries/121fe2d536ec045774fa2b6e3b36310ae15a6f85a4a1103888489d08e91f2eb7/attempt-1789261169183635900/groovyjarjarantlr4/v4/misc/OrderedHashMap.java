/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.misc;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrderedHashMap<K, V>
extends LinkedHashMap<K, V> {
    private static final long serialVersionUID = -4127551298268351889L;
    protected List<K> elements = new ArrayList<K>();

    public K getKey(int i) {
        return this.elements.get(i);
    }

    public V getElement(int i) {
        return this.get(this.elements.get(i));
    }

    @Override
    public V put(K key, V value) {
        this.elements.add(key);
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<K, V> entry : m.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V remove(Object key) {
        this.elements.remove(key);
        return super.remove(key);
    }

    @Override
    public void clear() {
        this.elements.clear();
        super.clear();
    }
}

