/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.trusted;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.PersonDirectoryPrincipalResolverProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-trusted-webflow")
@JsonFilter(value="TrustedAuthenticationProperties")
public class TrustedAuthenticationProperties
extends PersonDirectoryPrincipalResolverProperties {
    private static final long serialVersionUID = 279410895614233349L;
    private String remotePrincipalHeader;
    private String name;
    private Integer order;

    @Generated
    public String getRemotePrincipalHeader() {
        return this.remotePrincipalHeader;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public Integer getOrder() {
        return this.order;
    }

    @Generated
    public TrustedAuthenticationProperties setRemotePrincipalHeader(String remotePrincipalHeader) {
        this.remotePrincipalHeader = remotePrincipalHeader;
        return this;
    }

    @Generated
    public TrustedAuthenticationProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public TrustedAuthenticationProperties setOrder(Integer order) {
        this.order = order;
        return this;
    }
}

