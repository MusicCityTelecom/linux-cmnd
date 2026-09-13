/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.redis.connection.ClusterInfo
 */
package org.springframework.boot.actuate.redis;

import java.util.Properties;
import org.springframework.boot.actuate.health.Health;
import org.springframework.data.redis.connection.ClusterInfo;

final class RedisHealth {
    private RedisHealth() {
    }

    static Health.Builder up(Health.Builder builder, Properties info) {
        builder.withDetail("version", info.getProperty("redis_version"));
        return builder.up();
    }

    static Health.Builder fromClusterInfo(Health.Builder builder, ClusterInfo clusterInfo) {
        builder.withDetail("cluster_size", clusterInfo.getClusterSize());
        builder.withDetail("slots_up", clusterInfo.getSlotsOk());
        builder.withDetail("slots_fail", clusterInfo.getSlotsFail());
        if ("fail".equalsIgnoreCase(clusterInfo.getState())) {
            return builder.down();
        }
        return builder.up();
    }
}

