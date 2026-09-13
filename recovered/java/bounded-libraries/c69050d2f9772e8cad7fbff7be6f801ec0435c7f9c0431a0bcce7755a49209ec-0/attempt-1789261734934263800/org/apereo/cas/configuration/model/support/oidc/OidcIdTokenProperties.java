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
@JsonFilter(value="OidcIdTokenProperties")
public class OidcIdTokenProperties
implements Serializable {
    private static final long serialVersionUID = 813328615694269276L;
    @DurationCapable
    private String maxTimeToLiveInSeconds = "PT8H";
    private boolean includeIdTokenClaims = true;

    @Generated
    public String getMaxTimeToLiveInSeconds() {
        return this.maxTimeToLiveInSeconds;
    }

    @Generated
    public boolean isIncludeIdTokenClaims() {
        return this.includeIdTokenClaims;
    }

    @Generated
    public OidcIdTokenProperties setMaxTimeToLiveInSeconds(String maxTimeToLiveInSeconds) {
        this.maxTimeToLiveInSeconds = maxTimeToLiveInSeconds;
        return this;
    }

    @Generated
    public OidcIdTokenProperties setIncludeIdTokenClaims(boolean includeIdTokenClaims) {
        this.includeIdTokenClaims = includeIdTokenClaims;
        return this;
    }
}

