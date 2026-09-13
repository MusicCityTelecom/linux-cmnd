/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.hazelcast;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-hazelcast-core")
@JsonFilter(value="HazelcastClusterMulticastProperties")
public class HazelcastClusterMulticastProperties
implements Serializable {
    private static final long serialVersionUID = 1827784607045775145L;
    private String trustedInterfaces;
    private String group;
    private int port;
    private int timeout = 2;
    private int timeToLive = 32;
    private boolean enabled;

    @Generated
    public String getTrustedInterfaces() {
        return this.trustedInterfaces;
    }

    @Generated
    public String getGroup() {
        return this.group;
    }

    @Generated
    public int getPort() {
        return this.port;
    }

    @Generated
    public int getTimeout() {
        return this.timeout;
    }

    @Generated
    public int getTimeToLive() {
        return this.timeToLive;
    }

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public HazelcastClusterMulticastProperties setTrustedInterfaces(String trustedInterfaces) {
        this.trustedInterfaces = trustedInterfaces;
        return this;
    }

    @Generated
    public HazelcastClusterMulticastProperties setGroup(String group) {
        this.group = group;
        return this;
    }

    @Generated
    public HazelcastClusterMulticastProperties setPort(int port) {
        this.port = port;
        return this;
    }

    @Generated
    public HazelcastClusterMulticastProperties setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    @Generated
    public HazelcastClusterMulticastProperties setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
        return this;
    }

    @Generated
    public HazelcastClusterMulticastProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}

