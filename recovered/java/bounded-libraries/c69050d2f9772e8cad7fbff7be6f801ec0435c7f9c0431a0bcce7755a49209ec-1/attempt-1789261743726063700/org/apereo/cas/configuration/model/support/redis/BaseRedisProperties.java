/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.redis;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.redis.RedisClusterProperties;
import org.apereo.cas.configuration.model.support.redis.RedisPoolProperties;
import org.apereo.cas.configuration.model.support.redis.RedisSentinelProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-redis-core")
public class BaseRedisProperties
implements Serializable {
    private static final long serialVersionUID = -2600996981339638782L;
    @RequiredProperty
    private boolean enabled = true;
    private String uri;
    @RequiredProperty
    private int database;
    @RequiredProperty
    private String host = "localhost";
    @RequiredProperty
    private String password;
    @RequiredProperty
    private int port = 6379;
    @DurationCapable
    private String timeout = "PT60S";
    @NestedConfigurationProperty
    private RedisPoolProperties pool = new RedisPoolProperties();
    @NestedConfigurationProperty
    private RedisSentinelProperties sentinel = new RedisSentinelProperties();
    @NestedConfigurationProperty
    private RedisClusterProperties cluster = new RedisClusterProperties();
    private long scanCount;
    private boolean useSsl;
    private Boolean shareNativeConnections;
    private String protocolVersion = "RESP3";
    @DurationCapable
    private String connectTimeout = "PT10S";
    private RedisReadFromTypes readFrom;

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public String getUri() {
        return this.uri;
    }

    @Generated
    public int getDatabase() {
        return this.database;
    }

    @Generated
    public String getHost() {
        return this.host;
    }

    @Generated
    public String getPassword() {
        return this.password;
    }

    @Generated
    public int getPort() {
        return this.port;
    }

    @Generated
    public String getTimeout() {
        return this.timeout;
    }

    @Generated
    public RedisPoolProperties getPool() {
        return this.pool;
    }

    @Generated
    public RedisSentinelProperties getSentinel() {
        return this.sentinel;
    }

    @Generated
    public RedisClusterProperties getCluster() {
        return this.cluster;
    }

    @Generated
    public long getScanCount() {
        return this.scanCount;
    }

    @Generated
    public boolean isUseSsl() {
        return this.useSsl;
    }

    @Generated
    public Boolean getShareNativeConnections() {
        return this.shareNativeConnections;
    }

    @Generated
    public String getProtocolVersion() {
        return this.protocolVersion;
    }

    @Generated
    public String getConnectTimeout() {
        return this.connectTimeout;
    }

    @Generated
    public RedisReadFromTypes getReadFrom() {
        return this.readFrom;
    }

    @Generated
    public BaseRedisProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public BaseRedisProperties setUri(String uri) {
        this.uri = uri;
        return this;
    }

    @Generated
    public BaseRedisProperties setDatabase(int database) {
        this.database = database;
        return this;
    }

    @Generated
    public BaseRedisProperties setHost(String host) {
        this.host = host;
        return this;
    }

    @Generated
    public BaseRedisProperties setPassword(String password) {
        this.password = password;
        return this;
    }

    @Generated
    public BaseRedisProperties setPort(int port) {
        this.port = port;
        return this;
    }

    @Generated
    public BaseRedisProperties setTimeout(String timeout) {
        this.timeout = timeout;
        return this;
    }

    @Generated
    public BaseRedisProperties setPool(RedisPoolProperties pool) {
        this.pool = pool;
        return this;
    }

    @Generated
    public BaseRedisProperties setSentinel(RedisSentinelProperties sentinel) {
        this.sentinel = sentinel;
        return this;
    }

    @Generated
    public BaseRedisProperties setCluster(RedisClusterProperties cluster) {
        this.cluster = cluster;
        return this;
    }

    @Generated
    public BaseRedisProperties setScanCount(long scanCount) {
        this.scanCount = scanCount;
        return this;
    }

    @Generated
    public BaseRedisProperties setUseSsl(boolean useSsl) {
        this.useSsl = useSsl;
        return this;
    }

    @Generated
    public BaseRedisProperties setShareNativeConnections(Boolean shareNativeConnections) {
        this.shareNativeConnections = shareNativeConnections;
        return this;
    }

    @Generated
    public BaseRedisProperties setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
        return this;
    }

    @Generated
    public BaseRedisProperties setConnectTimeout(String connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    @Generated
    public BaseRedisProperties setReadFrom(RedisReadFromTypes readFrom) {
        this.readFrom = readFrom;
        return this;
    }

    public static enum RedisReadFromTypes {
        UPSTREAM,
        UPSTREAMPREFERRED,
        MASTER,
        MASTERPREFERRED,
        SLAVE,
        SLAVEPREFERRED,
        REPLICA,
        REPLICAPREFERRED,
        ANY,
        ANYREPLICA,
        NEAREST;

    }
}

