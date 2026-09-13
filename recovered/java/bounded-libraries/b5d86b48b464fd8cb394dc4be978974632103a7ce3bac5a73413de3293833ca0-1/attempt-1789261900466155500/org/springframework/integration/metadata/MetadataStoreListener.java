/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.metadata;

public interface MetadataStoreListener {
    public void onAdd(String var1, String var2);

    public void onRemove(String var1, String var2);

    public void onUpdate(String var1, String var2);
}

