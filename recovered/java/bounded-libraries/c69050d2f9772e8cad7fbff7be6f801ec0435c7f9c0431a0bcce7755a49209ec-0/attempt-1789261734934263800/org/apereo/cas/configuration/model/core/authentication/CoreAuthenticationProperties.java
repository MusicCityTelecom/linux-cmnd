/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.authentication;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.AuthenticationEngineProperties;
import org.apereo.cas.configuration.model.core.authentication.GroovyAuthenticationHandlerResolutionProperties;
import org.apereo.cas.configuration.model.core.authentication.RegisteredServiceAuthenticationHandlerResolutionProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-authentication", automated=true)
@JsonFilter(value="CoreAuthenticationProperties")
public class CoreAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = -2244126985007049516L;
    @NestedConfigurationProperty
    private RegisteredServiceAuthenticationHandlerResolutionProperties serviceAuthenticationResolution = new RegisteredServiceAuthenticationHandlerResolutionProperties();
    @NestedConfigurationProperty
    private GroovyAuthenticationHandlerResolutionProperties groovyAuthenticationResolution = new GroovyAuthenticationHandlerResolutionProperties();
    @NestedConfigurationProperty
    private AuthenticationEngineProperties engine = new AuthenticationEngineProperties();

    @Generated
    public RegisteredServiceAuthenticationHandlerResolutionProperties getServiceAuthenticationResolution() {
        return this.serviceAuthenticationResolution;
    }

    @Generated
    public GroovyAuthenticationHandlerResolutionProperties getGroovyAuthenticationResolution() {
        return this.groovyAuthenticationResolution;
    }

    @Generated
    public AuthenticationEngineProperties getEngine() {
        return this.engine;
    }

    @Generated
    public CoreAuthenticationProperties setServiceAuthenticationResolution(RegisteredServiceAuthenticationHandlerResolutionProperties serviceAuthenticationResolution) {
        this.serviceAuthenticationResolution = serviceAuthenticationResolution;
        return this;
    }

    @Generated
    public CoreAuthenticationProperties setGroovyAuthenticationResolution(GroovyAuthenticationHandlerResolutionProperties groovyAuthenticationResolution) {
        this.groovyAuthenticationResolution = groovyAuthenticationResolution;
        return this;
    }

    @Generated
    public CoreAuthenticationProperties setEngine(AuthenticationEngineProperties engine) {
        this.engine = engine;
        return this;
    }
}

