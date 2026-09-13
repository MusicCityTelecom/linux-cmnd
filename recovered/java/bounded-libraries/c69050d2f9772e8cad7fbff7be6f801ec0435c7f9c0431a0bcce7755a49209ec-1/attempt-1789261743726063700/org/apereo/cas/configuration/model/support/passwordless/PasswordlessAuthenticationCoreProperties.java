/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.passwordless;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-passwordless")
@JsonFilter(value="PasswordlessAuthenticationCoreProperties")
public class PasswordlessAuthenticationCoreProperties
implements Serializable {
    private static final long serialVersionUID = 6726382874579042117L;
    private boolean multifactorAuthenticationActivated;
    private boolean delegatedAuthenticationActivated;
    @NestedConfigurationProperty
    private SpringResourceProperties delegatedAuthenticationSelectorScript = new SpringResourceProperties();

    @Generated
    public boolean isMultifactorAuthenticationActivated() {
        return this.multifactorAuthenticationActivated;
    }

    @Generated
    public boolean isDelegatedAuthenticationActivated() {
        return this.delegatedAuthenticationActivated;
    }

    @Generated
    public SpringResourceProperties getDelegatedAuthenticationSelectorScript() {
        return this.delegatedAuthenticationSelectorScript;
    }

    @Generated
    public PasswordlessAuthenticationCoreProperties setMultifactorAuthenticationActivated(boolean multifactorAuthenticationActivated) {
        this.multifactorAuthenticationActivated = multifactorAuthenticationActivated;
        return this;
    }

    @Generated
    public PasswordlessAuthenticationCoreProperties setDelegatedAuthenticationActivated(boolean delegatedAuthenticationActivated) {
        this.delegatedAuthenticationActivated = delegatedAuthenticationActivated;
        return this;
    }

    @Generated
    public PasswordlessAuthenticationCoreProperties setDelegatedAuthenticationSelectorScript(SpringResourceProperties delegatedAuthenticationSelectorScript) {
        this.delegatedAuthenticationSelectorScript = delegatedAuthenticationSelectorScript;
        return this;
    }
}

