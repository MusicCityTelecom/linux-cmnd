/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.web.flow;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.web.flow.GroovyWebflowLoginDecoratorProperties;
import org.apereo.cas.configuration.model.core.web.flow.RestfulWebflowLoginDecoratorProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-webflow")
@JsonFilter(value="WebflowLoginDecoratorProperties")
public class WebflowLoginDecoratorProperties
implements Serializable {
    private static final long serialVersionUID = 2949978905279568311L;
    @NestedConfigurationProperty
    private GroovyWebflowLoginDecoratorProperties groovy = new GroovyWebflowLoginDecoratorProperties();
    @NestedConfigurationProperty
    private RestfulWebflowLoginDecoratorProperties rest = new RestfulWebflowLoginDecoratorProperties();

    @Generated
    public GroovyWebflowLoginDecoratorProperties getGroovy() {
        return this.groovy;
    }

    @Generated
    public RestfulWebflowLoginDecoratorProperties getRest() {
        return this.rest;
    }

    @Generated
    public WebflowLoginDecoratorProperties setGroovy(GroovyWebflowLoginDecoratorProperties groovy) {
        this.groovy = groovy;
        return this;
    }

    @Generated
    public WebflowLoginDecoratorProperties setRest(RestfulWebflowLoginDecoratorProperties rest) {
        this.rest = rest;
        return this;
    }
}

