/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.authentication;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.GroovyAdaptiveAuthenticationIPIntelligenceProperties;
import org.apereo.cas.configuration.model.core.authentication.RestfulAdaptiveAuthenticationIPIntelligenceProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-authentication", automated=true)
public class AdaptiveAuthenticationIPIntelligenceProperties
implements Serializable {
    private static final long serialVersionUID = -9111174229142982880L;
    @NestedConfigurationProperty
    private RestfulAdaptiveAuthenticationIPIntelligenceProperties rest = new RestfulAdaptiveAuthenticationIPIntelligenceProperties();
    @NestedConfigurationProperty
    private GroovyAdaptiveAuthenticationIPIntelligenceProperties groovy = new GroovyAdaptiveAuthenticationIPIntelligenceProperties();
    private BlackDot blackDot = new BlackDot();

    @Generated
    public RestfulAdaptiveAuthenticationIPIntelligenceProperties getRest() {
        return this.rest;
    }

    @Generated
    public GroovyAdaptiveAuthenticationIPIntelligenceProperties getGroovy() {
        return this.groovy;
    }

    @Generated
    public BlackDot getBlackDot() {
        return this.blackDot;
    }

    @Generated
    public AdaptiveAuthenticationIPIntelligenceProperties setRest(RestfulAdaptiveAuthenticationIPIntelligenceProperties rest) {
        this.rest = rest;
        return this;
    }

    @Generated
    public AdaptiveAuthenticationIPIntelligenceProperties setGroovy(GroovyAdaptiveAuthenticationIPIntelligenceProperties groovy) {
        this.groovy = groovy;
        return this;
    }

    @Generated
    public AdaptiveAuthenticationIPIntelligenceProperties setBlackDot(BlackDot blackDot) {
        this.blackDot = blackDot;
        return this;
    }

    @RequiresModule(name="cas-server-core-authentication", automated=true)
    public static class BlackDot
    implements Serializable {
        private static final long serialVersionUID = -4655149615297049570L;
        private String url = "http://check.getipintel.net/check.php?ip=%s";
        @RequiredProperty
        private String emailAddress;
        private String mode = "DYNA_LIST";

        @Generated
        public String getUrl() {
            return this.url;
        }

        @Generated
        public String getEmailAddress() {
            return this.emailAddress;
        }

        @Generated
        public String getMode() {
            return this.mode;
        }

        @Generated
        public BlackDot setUrl(String url) {
            this.url = url;
            return this;
        }

        @Generated
        public BlackDot setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        @Generated
        public BlackDot setMode(String mode) {
            this.mode = mode;
            return this;
        }
    }
}

