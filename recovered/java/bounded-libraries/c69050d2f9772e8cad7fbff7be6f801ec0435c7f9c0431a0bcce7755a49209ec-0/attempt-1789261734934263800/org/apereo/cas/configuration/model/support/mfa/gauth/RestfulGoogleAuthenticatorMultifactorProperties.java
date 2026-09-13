/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.mfa.gauth;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.BaseRestEndpointProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-gauth")
@JsonFilter(value="RestfulGoogleAuthenticatorMultifactorProperties")
public class RestfulGoogleAuthenticatorMultifactorProperties
extends BaseRestEndpointProperties {
    private static final long serialVersionUID = 4518622579150572559L;
    @RequiredProperty
    private String tokenUrl;

    @Generated
    public String getTokenUrl() {
        return this.tokenUrl;
    }

    @Generated
    public RestfulGoogleAuthenticatorMultifactorProperties setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
        return this;
    }
}

