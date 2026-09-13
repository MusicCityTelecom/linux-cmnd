/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.AbstractMultivaluedMap;
import javax.ws.rs.core.MultivaluedMap;

public class MultivaluedHashMap<K, V>
extends AbstractMultivaluedMap<K, V>
implements Serializable {
    private static final long serialVersionUID = -6052320403766368902L;

    public MultivaluedHashMap() {
        super(new HashMap());
    }

    public MultivaluedHashMap(int initialCapacity) {
        super(new HashMap(initialCapacity));
    }

    public MultivaluedHashMap(int initialCapacity, float loadFactor) {
        super(new HashMap(initialCapacity, loadFactor));
    }

    public MultivaluedHashMap(MultivaluedMap<? extends K, ? extends V> map) {
        this();
        super.putAll(map);
    }

    @Override
    private <T extends K, U extends V> void putAll(MultivaluedMap<T, U> map) {
        for (Map.Entry e : map.entrySet()) {
            this.store.put(e.getKey(), new ArrayList((Collection)e.getValue()));
        }
    }

    public MultivaluedHashMap(Map<? extends K, ? extends V> map) {
        this();
        for (Map.Entry<K, V> e : map.entrySet()) {
            this.putSingle(e.getKey(), e.getValue());
        }
    }
}

