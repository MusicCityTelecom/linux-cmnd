/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.monitor;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.ConnectionPoolingProperties;
import org.apereo.cas.configuration.model.support.ldap.AbstractLdapProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-ldap-monitor")
@JsonFilter(value="LdapMonitorProperties")
public class LdapMonitorProperties
extends AbstractLdapProperties {
    private static final long serialVersionUID = 4722929378440179113L;
    @DurationCapable
    private String maxWait = "PT5S";
    private boolean enabled = true;
    @NestedConfigurationProperty
    private ConnectionPoolingProperties pool = new ConnectionPoolingProperties();

    public LdapMonitorProperties() {
        this.setMinPoolSize(0);
    }

    @Generated
    public String getMaxWait() {
        return this.maxWait;
    }

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public ConnectionPoolingProperties getPool() {
        return this.pool;
    }

    @Generated
    public LdapMonitorProperties setMaxWait(String maxWait) {
        this.maxWait = maxWait;
        return this;
    }

    @Generated
    public LdapMonitorProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public LdapMonitorProperties setPool(ConnectionPoolingProperties pool) {
        this.pool = pool;
        return this;
    }
}

