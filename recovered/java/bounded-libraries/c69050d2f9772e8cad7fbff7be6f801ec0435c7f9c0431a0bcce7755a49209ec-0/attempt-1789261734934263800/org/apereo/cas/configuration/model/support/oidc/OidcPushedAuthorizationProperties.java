/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.oidc;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-oidc")
@JsonFilter(value="OidcPushedAuthorizationProperties")
public class OidcPushedAuthorizationProperties
implements Serializable {
    private static final long serialVersionUID = 632228615694269276L;
    private long numberOfUses = 1L;
    @DurationCapable
    private String maxTimeToLiveInSeconds = "PT30S";

    @Generated
    public long getNumberOfUses() {
        return this.numberOfUses;
    }

    @Generated
    public String getMaxTimeToLiveInSeconds() {
        return this.maxTimeToLiveInSeconds;
    }

    @Generated
    public OidcPushedAuthorizationProperties setNumberOfUses(long numberOfUses) {
        this.numberOfUses = numberOfUses;
        return this;
    }

    @Generated
    public OidcPushedAuthorizationProperties setMaxTimeToLiveInSeconds(String maxTimeToLiveInSeconds) {
        this.maxTimeToLiveInSeconds = maxTimeToLiveInSeconds;
        return this;
    }
}

