/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 *  org.springframework.core.io.FileSystemResource
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.configuration.model.support.uma;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@RequiresModule(name="cas-server-support-oauth-uma")
@JsonFilter(value="UmaRequestingPartyTokenProperties")
public class UmaRequestingPartyTokenProperties
implements Serializable {
    private static final long serialVersionUID = 3988708361481340920L;
    @DurationCapable
    private String maxTimeToLiveInSeconds = "PT3M";
    @NestedConfigurationProperty
    private SpringResourceProperties jwksFile = new SpringResourceProperties().setLocation((Resource)new FileSystemResource("/etc/cas/uma-keystore.jwks"));

    @Generated
    public String getMaxTimeToLiveInSeconds() {
        return this.maxTimeToLiveInSeconds;
    }

    @Generated
    public SpringResourceProperties getJwksFile() {
        return this.jwksFile;
    }

    @Generated
    public UmaRequestingPartyTokenProperties setMaxTimeToLiveInSeconds(String maxTimeToLiveInSeconds) {
        this.maxTimeToLiveInSeconds = maxTimeToLiveInSeconds;
        return this;
    }

    @Generated
    public UmaRequestingPartyTokenProperties setJwksFile(SpringResourceProperties jwksFile) {
        this.jwksFile = jwksFile;
        return this;
    }
}

