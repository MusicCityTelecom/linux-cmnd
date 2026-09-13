/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.pac4j;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.RestEndpointProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-pac4j-webflow")
@JsonFilter(value="Pac4jDelegatedAuthenticationRestfulProperties")
public class Pac4jDelegatedAuthenticationRestfulProperties
extends RestEndpointProperties {
    private static final long serialVersionUID = 3659099897056632608L;
    private String type = "pac4j";

    @Generated
    public String getType() {
        return this.type;
    }

    @Generated
    public Pac4jDelegatedAuthenticationRestfulProperties setType(String type) {
        this.type = type;
        return this;
    }
}

