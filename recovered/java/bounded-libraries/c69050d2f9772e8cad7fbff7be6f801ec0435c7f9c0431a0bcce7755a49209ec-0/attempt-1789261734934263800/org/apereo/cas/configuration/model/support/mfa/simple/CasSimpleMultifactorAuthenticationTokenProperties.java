/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.mfa.simple;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.apereo.cas.configuration.model.support.mfa.simple.CoreCasSimpleMultifactorAuthenticationTokenProperties;
import org.apereo.cas.configuration.model.support.mfa.simple.RestfulCasSimpleMultifactorAuthenticationTokenProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-simple-mfa")
@JsonFilter(value="CasSimpleMultifactorAuthenticationTokenProperties")
public class CasSimpleMultifactorAuthenticationTokenProperties
extends BaseMultifactorAuthenticationProviderProperties {
    private static final long serialVersionUID = -6333748853833491119L;
    @NestedConfigurationProperty
    private CoreCasSimpleMultifactorAuthenticationTokenProperties core = new CoreCasSimpleMultifactorAuthenticationTokenProperties();
    @NestedConfigurationProperty
    private RestfulCasSimpleMultifactorAuthenticationTokenProperties rest = new RestfulCasSimpleMultifactorAuthenticationTokenProperties();

    @Generated
    public CoreCasSimpleMultifactorAuthenticationTokenProperties getCore() {
        return this.core;
    }

    @Generated
    public RestfulCasSimpleMultifactorAuthenticationTokenProperties getRest() {
        return this.rest;
    }

    @Generated
    public CasSimpleMultifactorAuthenticationTokenProperties setCore(CoreCasSimpleMultifactorAuthenticationTokenProperties core) {
        this.core = core;
        return this;
    }

    @Generated
    public CasSimpleMultifactorAuthenticationTokenProperties setRest(RestfulCasSimpleMultifactorAuthenticationTokenProperties rest) {
        this.rest = rest;
        return this;
    }
}

