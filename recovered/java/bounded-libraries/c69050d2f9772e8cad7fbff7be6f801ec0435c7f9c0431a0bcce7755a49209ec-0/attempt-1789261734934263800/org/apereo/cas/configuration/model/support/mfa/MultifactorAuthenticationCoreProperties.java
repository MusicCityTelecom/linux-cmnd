/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.mfa;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-authentication", automated=true)
@JsonFilter(value="MultifactorAuthenticationCoreProperties")
public class MultifactorAuthenticationCoreProperties
implements Serializable {
    private static final long serialVersionUID = 7426521468929733907L;
    private String authenticationContextAttribute = "authnContextClass";
    private BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes globalFailureMode = BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes.CLOSED;
    private String contentType = "application/cas";
    @NestedConfigurationProperty
    private SpringResourceProperties providerSelectorGroovyScript = new SpringResourceProperties();
    private boolean providerSelectionEnabled;

    @Generated
    public String getAuthenticationContextAttribute() {
        return this.authenticationContextAttribute;
    }

    @Generated
    public BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes getGlobalFailureMode() {
        return this.globalFailureMode;
    }

    @Generated
    public String getContentType() {
        return this.contentType;
    }

    @Generated
    public SpringResourceProperties getProviderSelectorGroovyScript() {
        return this.providerSelectorGroovyScript;
    }

    @Generated
    public boolean isProviderSelectionEnabled() {
        return this.providerSelectionEnabled;
    }

    @Generated
    public MultifactorAuthenticationCoreProperties setAuthenticationContextAttribute(String authenticationContextAttribute) {
        this.authenticationContextAttribute = authenticationContextAttribute;
        return this;
    }

    @Generated
    public MultifactorAuthenticationCoreProperties setGlobalFailureMode(BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes globalFailureMode) {
        this.globalFailureMode = globalFailureMode;
        return this;
    }

    @Generated
    public MultifactorAuthenticationCoreProperties setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    @Generated
    public MultifactorAuthenticationCoreProperties setProviderSelectorGroovyScript(SpringResourceProperties providerSelectorGroovyScript) {
        this.providerSelectorGroovyScript = providerSelectorGroovyScript;
        return this;
    }

    @Generated
    public MultifactorAuthenticationCoreProperties setProviderSelectionEnabled(boolean providerSelectionEnabled) {
        this.providerSelectionEnabled = providerSelectionEnabled;
        return this;
    }
}

