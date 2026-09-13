/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.web.tomcat;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-webapp-tomcat")
@JsonFilter(value="CasEmbeddedApacheTomcatRemoteAddressProperties")
public class CasEmbeddedApacheTomcatRemoteAddressProperties
implements Serializable {
    private static final long serialVersionUID = -32143821503580896L;
    @RequiredProperty
    private boolean enabled;
    private String allowedClientIpAddressRegex = ".+";
    private String deniedClientIpAddressRegex = ".+";

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public String getAllowedClientIpAddressRegex() {
        return this.allowedClientIpAddressRegex;
    }

    @Generated
    public String getDeniedClientIpAddressRegex() {
        return this.deniedClientIpAddressRegex;
    }

    @Generated
    public CasEmbeddedApacheTomcatRemoteAddressProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatRemoteAddressProperties setAllowedClientIpAddressRegex(String allowedClientIpAddressRegex) {
        this.allowedClientIpAddressRegex = allowedClientIpAddressRegex;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatRemoteAddressProperties setDeniedClientIpAddressRegex(String deniedClientIpAddressRegex) {
        this.deniedClientIpAddressRegex = deniedClientIpAddressRegex;
        return this;
    }
}

