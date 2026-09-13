/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hazelcast.config.Config
 */
package org.springframework.boot.autoconfigure.hazelcast;

import com.hazelcast.config.Config;

@FunctionalInterface
public interface HazelcastConfigCustomizer {
    public void customize(Config var1);
}

