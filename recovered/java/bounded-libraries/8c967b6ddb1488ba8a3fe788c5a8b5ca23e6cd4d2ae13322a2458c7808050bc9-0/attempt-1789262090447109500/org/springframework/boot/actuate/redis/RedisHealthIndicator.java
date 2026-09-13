/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.redis.connection.RedisClusterConnection
 *  org.springframework.data.redis.connection.RedisConnection
 *  org.springframework.data.redis.connection.RedisConnectionFactory
 *  org.springframework.data.redis.core.RedisConnectionUtils
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.redis;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.redis.RedisHealth;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.util.Assert;

public class RedisHealthIndicator
extends AbstractHealthIndicator {
    private final RedisConnectionFactory redisConnectionFactory;

    public RedisHealthIndicator(RedisConnectionFactory connectionFactory) {
        super("Redis health check failed");
        Assert.notNull((Object)connectionFactory, (String)"ConnectionFactory must not be null");
        this.redisConnectionFactory = connectionFactory;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        RedisConnection connection = RedisConnectionUtils.getConnection((RedisConnectionFactory)this.redisConnectionFactory);
        try {
            this.doHealthCheck(builder, connection);
        }
        finally {
            RedisConnectionUtils.releaseConnection((RedisConnection)connection, (RedisConnectionFactory)this.redisConnectionFactory);
        }
    }

    private void doHealthCheck(Health.Builder builder, RedisConnection connection) {
        if (connection instanceof RedisClusterConnection) {
            RedisHealth.fromClusterInfo(builder, ((RedisClusterConnection)connection).clusterGetClusterInfo());
        } else {
            RedisHealth.up(builder, connection.info("server"));
        }
    }
}

