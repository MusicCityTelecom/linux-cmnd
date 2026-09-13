/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.integration.metadata;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.integration.metadata.ConcurrentMetadataStore;
import org.springframework.util.Assert;

public class SimpleMetadataStore
implements ConcurrentMetadataStore {
    private final ConcurrentMap<String, String> metadata;

    public SimpleMetadataStore() {
        this(new ConcurrentHashMap<String, String>());
    }

    public SimpleMetadataStore(ConcurrentMap<String, String> metadata) {
        Assert.notNull(metadata, (String)"'metadata' must not be null.");
        this.metadata = metadata;
    }

    @Override
    public void put(String key, String value) {
        this.metadata.put(key, value);
    }

    @Override
    public String get(String key) {
        return (String)this.metadata.get(key);
    }

    @Override
    public String remove(String key) {
        return (String)this.metadata.remove(key);
    }

    @Override
    public String putIfAbsent(String key, String value) {
        return this.metadata.putIfAbsent(key, value);
    }

    @Override
    public boolean replace(String key, String oldValue, String newValue) {
        return this.metadata.replace(key, oldValue, newValue);
    }
}

