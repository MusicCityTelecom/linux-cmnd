/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;

@FunctionalInterface
public interface AutoConfigurationImportFilter {
    public boolean[] match(String[] var1, AutoConfigurationMetadata var2);
}

