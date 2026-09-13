/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.pac4j.oidc;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.pac4j.oidc.BasePac4jOidcClientProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-pac4j-webflow")
@JsonFilter(value="Pac4jAzureOidcClientProperties")
public class Pac4jAzureOidcClientProperties
extends BasePac4jOidcClientProperties {
    private static final long serialVersionUID = 1259382317533639638L;
    private String tenant;

    @Generated
    public String getTenant() {
        return this.tenant;
    }

    @Generated
    public Pac4jAzureOidcClientProperties setTenant(String tenant) {
        this.tenant = tenant;
        return this;
    }
}

