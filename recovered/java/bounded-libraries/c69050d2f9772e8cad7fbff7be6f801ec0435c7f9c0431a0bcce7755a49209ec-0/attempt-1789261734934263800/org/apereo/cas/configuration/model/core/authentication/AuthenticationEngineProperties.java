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
import org.apereo.cas.configuration.model.core.authentication.GroovyAuthenticationEngineProcessorProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-authentication", automated=true)
@JsonFilter(value="AuthenticationEngineProperties")
public class AuthenticationEngineProperties
implements Serializable {
    private static final long serialVersionUID = -2475347572099983874L;
    @NestedConfigurationProperty
    private GroovyAuthenticationEngineProcessorProperties groovyPreProcessor = new GroovyAuthenticationEngineProcessorProperties();
    @NestedConfigurationProperty
    private GroovyAuthenticationEngineProcessorProperties groovyPostProcessor = new GroovyAuthenticationEngineProcessorProperties();

    @Generated
    public GroovyAuthenticationEngineProcessorProperties getGroovyPreProcessor() {
        return this.groovyPreProcessor;
    }

    @Generated
    public GroovyAuthenticationEngineProcessorProperties getGroovyPostProcessor() {
        return this.groovyPostProcessor;
    }

    @Generated
    public AuthenticationEngineProperties setGroovyPreProcessor(GroovyAuthenticationEngineProcessorProperties groovyPreProcessor) {
        this.groovyPreProcessor = groovyPreProcessor;
        return this;
    }

    @Generated
    public AuthenticationEngineProperties setGroovyPostProcessor(GroovyAuthenticationEngineProcessorProperties groovyPostProcessor) {
        this.groovyPostProcessor = groovyPostProcessor;
        return this;
    }
}

