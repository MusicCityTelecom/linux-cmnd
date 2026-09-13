/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.replication;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.replication.CookieSessionReplicationProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-api", automated=true)
@JsonFilter(value="SessionReplicationProperties")
public class SessionReplicationProperties
implements Serializable {
    private static final long serialVersionUID = -3839399712674610962L;
    private boolean replicateSessions = true;
    @NestedConfigurationProperty
    private CookieSessionReplicationProperties cookie = new CookieSessionReplicationProperties();

    @Generated
    public boolean isReplicateSessions() {
        return this.replicateSessions;
    }

    @Generated
    public CookieSessionReplicationProperties getCookie() {
        return this.cookie;
    }

    @Generated
    public SessionReplicationProperties setReplicateSessions(boolean replicateSessions) {
        this.replicateSessions = replicateSessions;
        return this;
    }

    @Generated
    public SessionReplicationProperties setCookie(CookieSessionReplicationProperties cookie) {
        this.cookie = cookie;
        return this;
    }
}

