/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.oidc.jwks;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.quartz.SchedulingProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-oidc")
@JsonFilter(value="OidcJsonWebKeyStoreRevocationProperties")
public class OidcJsonWebKeyStoreRevocationProperties
implements Serializable {
    private static final long serialVersionUID = 4955981831781991817L;
    @NestedConfigurationProperty
    private SchedulingProperties schedule = new SchedulingProperties().setEnabled(false).setRepeatInterval("P14D");

    @Generated
    public SchedulingProperties getSchedule() {
        return this.schedule;
    }

    @Generated
    public OidcJsonWebKeyStoreRevocationProperties setSchedule(SchedulingProperties schedule) {
        this.schedule = schedule;
        return this;
    }
}

