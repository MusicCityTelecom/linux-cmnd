/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.health;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.boot.actuate.health.NamedContributor;
import org.springframework.boot.actuate.health.NamedContributors;
import org.springframework.util.Assert;

abstract class NamedContributorsMapAdapter<V, C>
implements NamedContributors<C> {
    private final Map<String, C> map;

    NamedContributorsMapAdapter(Map<String, V> map, Function<V, ? extends C> valueAdapter) {
        Assert.notNull(map, (String)"Map must not be null");
        Assert.notNull(valueAdapter, (String)"ValueAdapter must not be null");
        map.keySet().forEach(this::validateKey);
        this.map = Collections.unmodifiableMap(map.entrySet().stream().collect(LinkedHashMap::new, (result, entry) -> result.put(entry.getKey(), this.adapt(entry.getValue(), valueAdapter)), Map::putAll));
    }

    private void validateKey(String value) {
        Assert.notNull((Object)value, (String)"Map must not contain null keys");
        Assert.isTrue((!value.contains("/") ? 1 : 0) != 0, (String)"Map keys must not contain a '/'");
    }

    private C adapt(V value, Function<V, ? extends C> valueAdapter) {
        C contributor = value != null ? (C)valueAdapter.apply(value) : null;
        Assert.notNull(contributor, (String)"Map must not contain null values");
        return contributor;
    }

    @Override
    public Iterator<NamedContributor<C>> iterator() {
        final Iterator<Map.Entry<String, C>> iterator = this.map.entrySet().iterator();
        return new Iterator<NamedContributor<C>>(){

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public NamedContributor<C> next() {
                Map.Entry entry = (Map.Entry)iterator.next();
                return NamedContributor.of((String)entry.getKey(), entry.getValue());
            }
        };
    }

    @Override
    public C getContributor(String name) {
        return this.map.get(name);
    }
}

