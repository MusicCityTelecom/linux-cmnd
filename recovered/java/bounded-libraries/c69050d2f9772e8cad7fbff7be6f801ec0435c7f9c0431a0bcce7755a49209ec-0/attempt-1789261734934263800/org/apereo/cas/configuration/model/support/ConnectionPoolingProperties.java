/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support;

import java.io.Serializable;
import java.util.UUID;
import lombok.Generated;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-util", automated=true)
public class ConnectionPoolingProperties
implements Serializable {
    private static final long serialVersionUID = -5307463292890944799L;
    private int minSize = 6;
    private int maxSize = 18;
    @DurationCapable
    private String maxWait = "PT2S";
    @DurationCapable
    private String keepAliveTime = "0";
    @DurationCapable
    private String maximumLifetime = "PT10M";
    private boolean suspension;
    private long timeoutMillis = 1000L;
    private String name = UUID.randomUUID().toString();

    @Generated
    public int getMinSize() {
        return this.minSize;
    }

    @Generated
    public int getMaxSize() {
        return this.maxSize;
    }

    @Generated
    public String getMaxWait() {
        return this.maxWait;
    }

    @Generated
    public String getKeepAliveTime() {
        return this.keepAliveTime;
    }

    @Generated
    public String getMaximumLifetime() {
        return this.maximumLifetime;
    }

    @Generated
    public boolean isSuspension() {
        return this.suspension;
    }

    @Generated
    public long getTimeoutMillis() {
        return this.timeoutMillis;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public ConnectionPoolingProperties setMinSize(int minSize) {
        this.minSize = minSize;
        return this;
    }

    @Generated
    public ConnectionPoolingProperties setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    @Generated
    public ConnectionPoolingProperties setMaxWait(String maxWait) {
        this.maxWait = maxWait;
        return this;
    }

    @Generated
    public ConnectionPoolingProperties setKeepAliveTime(String keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
        return this;
    }

    @Generated
    public ConnectionPoolingProperties setMaximumLifetime(String maximumLifetime) {
        this.maximumLifetime = maximumLifetime;
        return this;
    }

    @Generated
    public ConnectionPoolingProperties setSuspension(boolean suspension) {
        this.suspension = suspension;
        return this;
    }

    @Generated
    public ConnectionPoolingProperties setTimeoutMillis(long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
        return this;
    }

    @Generated
    public ConnectionPoolingProperties setName(String name) {
        this.name = name;
        return this;
    }
}

