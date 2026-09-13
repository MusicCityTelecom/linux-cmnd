/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.metadata;

import org.springframework.integration.metadata.MetadataStore;

public interface ConcurrentMetadataStore
extends MetadataStore {
    public String putIfAbsent(String var1, String var2);

    public boolean replace(String var1, String var2, String var3);
}

