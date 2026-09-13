/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.interrupt;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.interrupt.GroovyInterruptProperties;
import org.apereo.cas.configuration.model.support.interrupt.InterruptCookieProperties;
import org.apereo.cas.configuration.model.support.interrupt.InterruptCoreProperties;
import org.apereo.cas.configuration.model.support.interrupt.JsonInterruptProperties;
import org.apereo.cas.configuration.model.support.interrupt.RegexInterruptProperties;
import org.apereo.cas.configuration.model.support.interrupt.RestfulInterruptProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-interrupt-webflow")
@JsonFilter(value="InterruptProperties")
public class InterruptProperties
implements Serializable {
    private static final long serialVersionUID = -4945287309473842615L;
    @NestedConfigurationProperty
    private JsonInterruptProperties json = new JsonInterruptProperties();
    @NestedConfigurationProperty
    private GroovyInterruptProperties groovy = new GroovyInterruptProperties();
    @NestedConfigurationProperty
    private RestfulInterruptProperties rest = new RestfulInterruptProperties();
    @NestedConfigurationProperty
    private RegexInterruptProperties regex = new RegexInterruptProperties();
    @NestedConfigurationProperty
    private InterruptCoreProperties core = new InterruptCoreProperties();
    @NestedConfigurationProperty
    private InterruptCookieProperties cookie = new InterruptCookieProperties();

    @Generated
    public JsonInterruptProperties getJson() {
        return this.json;
    }

    @Generated
    public GroovyInterruptProperties getGroovy() {
        return this.groovy;
    }

    @Generated
    public RestfulInterruptProperties getRest() {
        return this.rest;
    }

    @Generated
    public RegexInterruptProperties getRegex() {
        return this.regex;
    }

    @Generated
    public InterruptCoreProperties getCore() {
        return this.core;
    }

    @Generated
    public InterruptCookieProperties getCookie() {
        return this.cookie;
    }

    @Generated
    public InterruptProperties setJson(JsonInterruptProperties json) {
        this.json = json;
        return this;
    }

    @Generated
    public InterruptProperties setGroovy(GroovyInterruptProperties groovy) {
        this.groovy = groovy;
        return this;
    }

    @Generated
    public InterruptProperties setRest(RestfulInterruptProperties rest) {
        this.rest = rest;
        return this;
    }

    @Generated
    public InterruptProperties setRegex(RegexInterruptProperties regex) {
        this.regex = regex;
        return this;
    }

    @Generated
    public InterruptProperties setCore(InterruptCoreProperties core) {
        this.core = core;
        return this;
    }

    @Generated
    public InterruptProperties setCookie(InterruptCookieProperties cookie) {
        this.cookie = cookie;
        return this;
    }
}

