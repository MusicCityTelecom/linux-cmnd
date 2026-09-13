/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.ConfigurationProperties
 */
package org.springframework.boot.autoconfigure.netty;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="spring.netty")
public class NettyProperties {
    private LeakDetection leakDetection = LeakDetection.SIMPLE;

    public LeakDetection getLeakDetection() {
        return this.leakDetection;
    }

    public void setLeakDetection(LeakDetection leakDetection) {
        this.leakDetection = leakDetection;
    }

    public static enum LeakDetection {
        DISABLED,
        SIMPLE,
        ADVANCED,
        PARANOID;

    }
}

