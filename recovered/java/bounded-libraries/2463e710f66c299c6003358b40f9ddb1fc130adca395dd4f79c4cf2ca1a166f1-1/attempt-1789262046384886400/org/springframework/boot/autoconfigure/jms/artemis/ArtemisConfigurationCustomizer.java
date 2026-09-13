/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.activemq.artemis.core.config.Configuration
 */
package org.springframework.boot.autoconfigure.jms.artemis;

import org.apache.activemq.artemis.core.config.Configuration;

@FunctionalInterface
public interface ArtemisConfigurationCustomizer {
    public void customize(Configuration var1);
}

