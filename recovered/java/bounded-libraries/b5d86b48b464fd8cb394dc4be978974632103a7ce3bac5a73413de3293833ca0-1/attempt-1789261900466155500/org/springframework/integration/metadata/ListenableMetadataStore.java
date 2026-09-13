/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.metadata;

import org.springframework.integration.metadata.ConcurrentMetadataStore;
import org.springframework.integration.metadata.MetadataStoreListener;

public interface ListenableMetadataStore
extends ConcurrentMetadataStore {
    public void addListener(MetadataStoreListener var1);

    public void removeListener(MetadataStoreListener var1);
}

