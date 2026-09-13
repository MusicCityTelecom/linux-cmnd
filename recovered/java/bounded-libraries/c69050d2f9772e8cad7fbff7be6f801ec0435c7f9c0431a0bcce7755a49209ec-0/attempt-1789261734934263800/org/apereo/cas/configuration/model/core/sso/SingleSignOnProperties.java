/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.sso;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-authentication", automated=true)
@JsonFilter(value="SingleSignOnProperties")
public class SingleSignOnProperties
implements Serializable {
    private static final long serialVersionUID = -8777647966370741733L;
    private boolean ssoEnabled = true;
    private boolean createSsoCookieOnRenewAuthn = true;
    private boolean allowMissingServiceParameter = true;
    private boolean proxyAuthnEnabled = true;
    private boolean renewAuthnEnabled = true;
    private String requiredServicePattern;

    @Generated
    public boolean isSsoEnabled() {
        return this.ssoEnabled;
    }

    @Generated
    public boolean isCreateSsoCookieOnRenewAuthn() {
        return this.createSsoCookieOnRenewAuthn;
    }

    @Generated
    public boolean isAllowMissingServiceParameter() {
        return this.allowMissingServiceParameter;
    }

    @Generated
    public boolean isProxyAuthnEnabled() {
        return this.proxyAuthnEnabled;
    }

    @Generated
    public boolean isRenewAuthnEnabled() {
        return this.renewAuthnEnabled;
    }

    @Generated
    public String getRequiredServicePattern() {
        return this.requiredServicePattern;
    }

    @Generated
    public SingleSignOnProperties setSsoEnabled(boolean ssoEnabled) {
        this.ssoEnabled = ssoEnabled;
        return this;
    }

    @Generated
    public SingleSignOnProperties setCreateSsoCookieOnRenewAuthn(boolean createSsoCookieOnRenewAuthn) {
        this.createSsoCookieOnRenewAuthn = createSsoCookieOnRenewAuthn;
        return this;
    }

    @Generated
    public SingleSignOnProperties setAllowMissingServiceParameter(boolean allowMissingServiceParameter) {
        this.allowMissingServiceParameter = allowMissingServiceParameter;
        return this;
    }

    @Generated
    public SingleSignOnProperties setProxyAuthnEnabled(boolean proxyAuthnEnabled) {
        this.proxyAuthnEnabled = proxyAuthnEnabled;
        return this;
    }

    @Generated
    public SingleSignOnProperties setRenewAuthnEnabled(boolean renewAuthnEnabled) {
        this.renewAuthnEnabled = renewAuthnEnabled;
        return this;
    }

    @Generated
    public SingleSignOnProperties setRequiredServicePattern(String requiredServicePattern) {
        this.requiredServicePattern = requiredServicePattern;
        return this;
    }
}

