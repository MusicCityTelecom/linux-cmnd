/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.couchbase.client.java.env.ClusterEnvironment$Builder
 */
package org.springframework.boot.autoconfigure.couchbase;

import com.couchbase.client.java.env.ClusterEnvironment;

@FunctionalInterface
public interface ClusterEnvironmentBuilderCustomizer {
    public void customize(ClusterEnvironment.Builder var1);
}

