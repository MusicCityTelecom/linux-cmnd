/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.redis;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-redis-core")
@JsonFilter(value="RedisPoolProperties")
public class RedisPoolProperties
implements Serializable {
    private static final long serialVersionUID = 8534823157764550894L;
    private int numTestsPerEvictionRun;
    private long softMinEvictableIdleTimeMillis;
    private long minEvictableIdleTimeMillis;
    private boolean lifo = true;
    private boolean fairness;
    private boolean testOnCreate;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean testWhileIdle;
    private int maxIdle = 8;
    private int minIdle;
    private int maxActive = 8;
    private int maxWait = -1;
    @RequiredProperty
    private boolean enabled;

    @Generated
    public int getNumTestsPerEvictionRun() {
        return this.numTestsPerEvictionRun;
    }

    @Generated
    public long getSoftMinEvictableIdleTimeMillis() {
        return this.softMinEvictableIdleTimeMillis;
    }

    @Generated
    public long getMinEvictableIdleTimeMillis() {
        return this.minEvictableIdleTimeMillis;
    }

    @Generated
    public boolean isLifo() {
        return this.lifo;
    }

    @Generated
    public boolean isFairness() {
        return this.fairness;
    }

    @Generated
    public boolean isTestOnCreate() {
        return this.testOnCreate;
    }

    @Generated
    public boolean isTestOnBorrow() {
        return this.testOnBorrow;
    }

    @Generated
    public boolean isTestOnReturn() {
        return this.testOnReturn;
    }

    @Generated
    public boolean isTestWhileIdle() {
        return this.testWhileIdle;
    }

    @Generated
    public int getMaxIdle() {
        return this.maxIdle;
    }

    @Generated
    public int getMinIdle() {
        return this.minIdle;
    }

    @Generated
    public int getMaxActive() {
        return this.maxActive;
    }

    @Generated
    public int getMaxWait() {
        return this.maxWait;
    }

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public RedisPoolProperties setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
        return this;
    }

    @Generated
    public RedisPoolProperties setSoftMinEvictableIdleTimeMillis(long softMinEvictableIdleTimeMillis) {
        this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
        return this;
    }

    @Generated
    public RedisPoolProperties setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
        return this;
    }

    @Generated
    public RedisPoolProperties setLifo(boolean lifo) {
        this.lifo = lifo;
        return this;
    }

    @Generated
    public RedisPoolProperties setFairness(boolean fairness) {
        this.fairness = fairness;
        return this;
    }

    @Generated
    public RedisPoolProperties setTestOnCreate(boolean testOnCreate) {
        this.testOnCreate = testOnCreate;
        return this;
    }

    @Generated
    public RedisPoolProperties setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
        return this;
    }

    @Generated
    public RedisPoolProperties setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
        return this;
    }

    @Generated
    public RedisPoolProperties setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
        return this;
    }

    @Generated
    public RedisPoolProperties setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
        return this;
    }

    @Generated
    public RedisPoolProperties setMinIdle(int minIdle) {
        this.minIdle = minIdle;
        return this;
    }

    @Generated
    public RedisPoolProperties setMaxActive(int maxActive) {
        this.maxActive = maxActive;
        return this;
    }

    @Generated
    public RedisPoolProperties setMaxWait(int maxWait) {
        this.maxWait = maxWait;
        return this;
    }

    @Generated
    public RedisPoolProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}

