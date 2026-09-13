/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.pac4j.cas;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jBaseClientProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-pac4j-webflow")
@JsonFilter(value="Pac4jCasClientProperties")
public class Pac4jCasClientProperties
extends Pac4jBaseClientProperties
implements CasFeatureModule {
    private static final long serialVersionUID = -2738631545437677447L;
    @RequiredProperty
    private String loginUrl;
    @RequiredProperty
    private String protocol = "CAS20";

    public Pac4jCasClientProperties() {
        this.setCallbackUrlType(Pac4jBaseClientProperties.CallbackUrlTypes.PATH_PARAMETER);
    }

    @Generated
    public String getLoginUrl() {
        return this.loginUrl;
    }

    @Generated
    public String getProtocol() {
        return this.protocol;
    }

    @Generated
    public Pac4jCasClientProperties setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
        return this;
    }

    @Generated
    public Pac4jCasClientProperties setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }
}

