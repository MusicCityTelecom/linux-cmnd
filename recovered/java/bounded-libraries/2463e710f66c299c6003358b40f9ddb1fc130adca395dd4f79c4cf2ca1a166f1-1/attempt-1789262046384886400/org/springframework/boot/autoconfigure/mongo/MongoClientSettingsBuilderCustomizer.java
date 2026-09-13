/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mongodb.MongoClientSettings$Builder
 */
package org.springframework.boot.autoconfigure.mongo;

import com.mongodb.MongoClientSettings;

@FunctionalInterface
public interface MongoClientSettingsBuilderCustomizer {
    public void customize(MongoClientSettings.Builder var1);
}

