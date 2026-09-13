/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.metadata;

import org.springframework.integration.metadata.MetadataStoreListener;

public abstract class MetadataStoreListenerAdapter
implements MetadataStoreListener {
    @Override
    public void onAdd(String key, String value) {
    }

    @Override
    public void onRemove(String key, String oldValue) {
    }

    @Override
    public void onUpdate(String key, String newValue) {
    }
}

