/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.mongo;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-mongo-core")
public class MongoDbConnectionPoolProperties
implements Serializable {
    private static final long serialVersionUID = 8312213511918496060L;
    @DurationCapable
    private String lifeTime = "PT60S";
    @DurationCapable
    private String idleTime = "PT30S";
    @DurationCapable
    private String maxWaitTime = "PT60S";
    private int maxSize = 10;
    private int minSize = 1;
    private int perHost = 10;

    @Generated
    public String getLifeTime() {
        return this.lifeTime;
    }

    @Generated
    public String getIdleTime() {
        return this.idleTime;
    }

    @Generated
    public String getMaxWaitTime() {
        return this.maxWaitTime;
    }

    @Generated
    public int getMaxSize() {
        return this.maxSize;
    }

    @Generated
    public int getMinSize() {
        return this.minSize;
    }

    @Generated
    public int getPerHost() {
        return this.perHost;
    }

    @Generated
    public MongoDbConnectionPoolProperties setLifeTime(String lifeTime) {
        this.lifeTime = lifeTime;
        return this;
    }

    @Generated
    public MongoDbConnectionPoolProperties setIdleTime(String idleTime) {
        this.idleTime = idleTime;
        return this;
    }

    @Generated
    public MongoDbConnectionPoolProperties setMaxWaitTime(String maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
        return this;
    }

    @Generated
    public MongoDbConnectionPoolProperties setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    @Generated
    public MongoDbConnectionPoolProperties setMinSize(int minSize) {
        this.minSize = minSize;
        return this;
    }

    @Generated
    public MongoDbConnectionPoolProperties setPerHost(int perHost) {
        this.perHost = perHost;
        return this;
    }
}

