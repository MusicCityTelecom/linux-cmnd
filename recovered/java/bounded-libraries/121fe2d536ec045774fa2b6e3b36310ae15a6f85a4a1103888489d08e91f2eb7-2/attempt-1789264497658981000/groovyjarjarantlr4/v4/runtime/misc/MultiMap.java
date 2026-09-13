/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.misc;

import groovyjarjarantlr4.v4.runtime.misc.Tuple;
import groovyjarjarantlr4.v4.runtime.misc.Tuple2;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MultiMap<K, V>
extends LinkedHashMap<K, List<V>> {
    private static final long serialVersionUID = -4956746660057462312L;

    public void map(K key, V value) {
        ArrayList<V> elementsForKey = (ArrayList<V>)this.get(key);
        if (elementsForKey == null) {
            elementsForKey = new ArrayList<V>();
            super.put(key, elementsForKey);
        }
        elementsForKey.add(value);
    }

    public List<Tuple2<K, V>> getPairs() {
        ArrayList<Tuple2<K, V>> pairs = new ArrayList<Tuple2<K, V>>();
        for (Object key : this.keySet()) {
            for (Object value : (List)this.get(key)) {
                pairs.add(Tuple.create(key, value));
            }
        }
        return pairs;
    }
}

