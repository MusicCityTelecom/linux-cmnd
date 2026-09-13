/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.monitor;

import lombok.Generated;
import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-jdbc-monitor")
public class JdbcMonitorProperties
extends AbstractJpaProperties {
    private static final long serialVersionUID = -7139788158851782673L;
    private String validationQuery = "SELECT 1";
    @DurationCapable
    private String maxWait = "PT5S";

    @Generated
    public String getValidationQuery() {
        return this.validationQuery;
    }

    @Generated
    public String getMaxWait() {
        return this.maxWait;
    }

    @Generated
    public JdbcMonitorProperties setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
        return this;
    }

    @Generated
    public JdbcMonitorProperties setMaxWait(String maxWait) {
        this.maxWait = maxWait;
        return this;
    }
}

