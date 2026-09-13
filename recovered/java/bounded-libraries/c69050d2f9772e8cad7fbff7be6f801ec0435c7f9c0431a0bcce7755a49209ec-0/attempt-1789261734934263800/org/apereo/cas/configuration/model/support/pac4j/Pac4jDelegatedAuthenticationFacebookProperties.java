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
import org.apereo.cas.configuration.model.support.pac4j.Pac4jIdentifiableClientProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-pac4j-webflow")
@JsonFilter(value="Pac4jDelegatedAuthenticationFacebookProperties")
public class Pac4jDelegatedAuthenticationFacebookProperties
extends Pac4jIdentifiableClientProperties {
    private static final long serialVersionUID = -2737594266552466076L;
    private String scope;
    private String fields;

    public Pac4jDelegatedAuthenticationFacebookProperties() {
        this.setClientName("Facebook");
    }

    @Generated
    public String getScope() {
        return this.scope;
    }

    @Generated
    public String getFields() {
        return this.fields;
    }

    @Generated
    public Pac4jDelegatedAuthenticationFacebookProperties setScope(String scope) {
        this.scope = scope;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationFacebookProperties setFields(String fields) {
        this.fields = fields;
        return this;
    }
}

