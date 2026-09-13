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
import org.apereo.cas.configuration.model.core.util.EncryptionRandomizedSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.model.core.web.flow.GroovyWebflowProperties;
import org.apereo.cas.configuration.model.core.web.flow.WebflowAutoConfigurationProperties;
import org.apereo.cas.configuration.model.core.web.flow.WebflowLoginDecoratorProperties;
import org.apereo.cas.configuration.model.core.web.flow.WebflowSessionManagementProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-webflow")
@JsonFilter(value="WebflowProperties")
public class WebflowProperties
implements Serializable {
    private static final long serialVersionUID = 4949978905279568311L;
    @NestedConfigurationProperty
    private EncryptionRandomizedSigningJwtCryptographyProperties crypto = new EncryptionRandomizedSigningJwtCryptographyProperties();
    @NestedConfigurationProperty
    private WebflowAutoConfigurationProperties autoConfiguration = new WebflowAutoConfigurationProperties();
    @NestedConfigurationProperty
    private WebflowSessionManagementProperties session = new WebflowSessionManagementProperties();
    @NestedConfigurationProperty
    private WebflowLoginDecoratorProperties loginDecorator = new WebflowLoginDecoratorProperties();
    @NestedConfigurationProperty
    private GroovyWebflowProperties groovy = new GroovyWebflowProperties();

    @Generated
    public EncryptionRandomizedSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public WebflowAutoConfigurationProperties getAutoConfiguration() {
        return this.autoConfiguration;
    }

    @Generated
    public WebflowSessionManagementProperties getSession() {
        return this.session;
    }

    @Generated
    public WebflowLoginDecoratorProperties getLoginDecorator() {
        return this.loginDecorator;
    }

    @Generated
    public GroovyWebflowProperties getGroovy() {
        return this.groovy;
    }

    @Generated
    public WebflowProperties setCrypto(EncryptionRandomizedSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }

    @Generated
    public WebflowProperties setAutoConfiguration(WebflowAutoConfigurationProperties autoConfiguration) {
        this.autoConfiguration = autoConfiguration;
        return this;
    }

    @Generated
    public WebflowProperties setSession(WebflowSessionManagementProperties session) {
        this.session = session;
        return this;
    }

    @Generated
    public WebflowProperties setLoginDecorator(WebflowLoginDecoratorProperties loginDecorator) {
        this.loginDecorator = loginDecorator;
        return this;
    }

    @Generated
    public WebflowProperties setGroovy(GroovyWebflowProperties groovy) {
        this.groovy = groovy;
        return this;
    }
}

