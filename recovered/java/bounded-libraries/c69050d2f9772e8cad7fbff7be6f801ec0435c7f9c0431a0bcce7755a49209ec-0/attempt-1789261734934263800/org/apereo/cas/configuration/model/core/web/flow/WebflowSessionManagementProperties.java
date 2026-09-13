/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.web.flow;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.hazelcast.BaseHazelcastProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-webflow")
@JsonFilter(value="WebflowSessionManagementProperties")
public class WebflowSessionManagementProperties
implements Serializable {
    private static final long serialVersionUID = 7479028707118198914L;
    @DurationCapable
    private String lockTimeout = "PT30S";
    private int maxConversations = 5;
    private boolean compress;
    private boolean storage;
    @NestedConfigurationProperty
    private BaseHazelcastProperties hazelcast = new BaseHazelcastProperties();

    @Generated
    public String getLockTimeout() {
        return this.lockTimeout;
    }

    @Generated
    public int getMaxConversations() {
        return this.maxConversations;
    }

    @Generated
    public boolean isCompress() {
        return this.compress;
    }

    @Generated
    public boolean isStorage() {
        return this.storage;
    }

    @Generated
    public BaseHazelcastProperties getHazelcast() {
        return this.hazelcast;
    }

    @Generated
    public WebflowSessionManagementProperties setLockTimeout(String lockTimeout) {
        this.lockTimeout = lockTimeout;
        return this;
    }

    @Generated
    public WebflowSessionManagementProperties setMaxConversations(int maxConversations) {
        this.maxConversations = maxConversations;
        return this;
    }

    @Generated
    public WebflowSessionManagementProperties setCompress(boolean compress) {
        this.compress = compress;
        return this;
    }

    @Generated
    public WebflowSessionManagementProperties setStorage(boolean storage) {
        this.storage = storage;
        return this;
    }

    @Generated
    public WebflowSessionManagementProperties setHazelcast(BaseHazelcastProperties hazelcast) {
        this.hazelcast = hazelcast;
        return this;
    }
}

