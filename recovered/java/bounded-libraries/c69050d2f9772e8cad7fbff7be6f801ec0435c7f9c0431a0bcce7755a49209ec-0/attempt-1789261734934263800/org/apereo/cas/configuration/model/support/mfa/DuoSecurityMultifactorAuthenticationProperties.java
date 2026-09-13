/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.mfa;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.apereo.cas.configuration.support.ExpressionLanguageCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-duo")
@JsonFilter(value="DuoSecurityMultifactorProperties")
public class DuoSecurityMultifactorAuthenticationProperties
extends BaseMultifactorAuthenticationProviderProperties {
    public static final String DEFAULT_IDENTIFIER = "mfa-duo";
    private static final long serialVersionUID = -4655375354167880807L;
    @RequiredProperty
    @ExpressionLanguageCapable
    private String duoIntegrationKey;
    @RequiredProperty
    @ExpressionLanguageCapable
    private String duoSecretKey;
    @RequiredProperty
    @Deprecated(since="6.4.0")
    @ExpressionLanguageCapable
    private String duoApplicationKey;
    @RequiredProperty
    @ExpressionLanguageCapable
    private String duoApiHost;
    private String registrationUrl;
    private boolean trustedDeviceEnabled;
    private boolean accountStatusEnabled = true;
    @ExpressionLanguageCapable
    private String duoAdminIntegrationKey;
    @ExpressionLanguageCapable
    private String duoAdminSecretKey;

    public DuoSecurityMultifactorAuthenticationProperties() {
        this.setId(DEFAULT_IDENTIFIER);
    }

    @Generated
    public String getDuoIntegrationKey() {
        return this.duoIntegrationKey;
    }

    @Generated
    public String getDuoSecretKey() {
        return this.duoSecretKey;
    }

    @Deprecated
    @Generated
    public String getDuoApplicationKey() {
        return this.duoApplicationKey;
    }

    @Generated
    public String getDuoApiHost() {
        return this.duoApiHost;
    }

    @Generated
    public String getRegistrationUrl() {
        return this.registrationUrl;
    }

    @Generated
    public boolean isTrustedDeviceEnabled() {
        return this.trustedDeviceEnabled;
    }

    @Generated
    public boolean isAccountStatusEnabled() {
        return this.accountStatusEnabled;
    }

    @Generated
    public String getDuoAdminIntegrationKey() {
        return this.duoAdminIntegrationKey;
    }

    @Generated
    public String getDuoAdminSecretKey() {
        return this.duoAdminSecretKey;
    }

    @Generated
    public DuoSecurityMultifactorAuthenticationProperties setDuoIntegrationKey(String duoIntegrationKey) {
        this.duoIntegrationKey = duoIntegrationKey;
        return this;
    }

    @Generated
    public DuoSecurityMultifactorAuthenticationProperties setDuoSecretKey(String duoSecretKey) {
        this.duoSecretKey = duoSecretKey;
        return this;
    }

    @Deprecated
    @Generated
    public DuoSecurityMultifactorAuthenticationProperties setDuoApplicationKey(String duoApplicationKey) {
        this.duoApplicationKey = duoApplicationKey;
        return this;
    }

    @Generated
    public DuoSecurityMultifactorAuthenticationProperties setDuoApiHost(String duoApiHost) {
        this.duoApiHost = duoApiHost;
        return this;
    }

    @Generated
    public DuoSecurityMultifactorAuthenticationProperties setRegistrationUrl(String registrationUrl) {
        this.registrationUrl = registrationUrl;
        return this;
    }

    @Generated
    public DuoSecurityMultifactorAuthenticationProperties setTrustedDeviceEnabled(boolean trustedDeviceEnabled) {
        this.trustedDeviceEnabled = trustedDeviceEnabled;
        return this;
    }

    @Generated
    public DuoSecurityMultifactorAuthenticationProperties setAccountStatusEnabled(boolean accountStatusEnabled) {
        this.accountStatusEnabled = accountStatusEnabled;
        return this;
    }

    @Generated
    public DuoSecurityMultifactorAuthenticationProperties setDuoAdminIntegrationKey(String duoAdminIntegrationKey) {
        this.duoAdminIntegrationKey = duoAdminIntegrationKey;
        return this;
    }

    @Generated
    public DuoSecurityMultifactorAuthenticationProperties setDuoAdminSecretKey(String duoAdminSecretKey) {
        this.duoAdminSecretKey = duoAdminSecretKey;
        return this;
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DuoSecurityMultifactorAuthenticationProperties)) {
            return false;
        }
        DuoSecurityMultifactorAuthenticationProperties other = (DuoSecurityMultifactorAuthenticationProperties)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$duoIntegrationKey = this.duoIntegrationKey;
        String other$duoIntegrationKey = other.duoIntegrationKey;
        if (this$duoIntegrationKey == null ? other$duoIntegrationKey != null : !this$duoIntegrationKey.equals(other$duoIntegrationKey)) {
            return false;
        }
        String this$duoSecretKey = this.duoSecretKey;
        String other$duoSecretKey = other.duoSecretKey;
        if (this$duoSecretKey == null ? other$duoSecretKey != null : !this$duoSecretKey.equals(other$duoSecretKey)) {
            return false;
        }
        String this$duoApplicationKey = this.duoApplicationKey;
        String other$duoApplicationKey = other.duoApplicationKey;
        if (this$duoApplicationKey == null ? other$duoApplicationKey != null : !this$duoApplicationKey.equals(other$duoApplicationKey)) {
            return false;
        }
        String this$duoApiHost = this.duoApiHost;
        String other$duoApiHost = other.duoApiHost;
        return !(this$duoApiHost == null ? other$duoApiHost != null : !this$duoApiHost.equals(other$duoApiHost));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DuoSecurityMultifactorAuthenticationProperties;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $duoIntegrationKey = this.duoIntegrationKey;
        result = result * 59 + ($duoIntegrationKey == null ? 43 : $duoIntegrationKey.hashCode());
        String $duoSecretKey = this.duoSecretKey;
        result = result * 59 + ($duoSecretKey == null ? 43 : $duoSecretKey.hashCode());
        String $duoApplicationKey = this.duoApplicationKey;
        result = result * 59 + ($duoApplicationKey == null ? 43 : $duoApplicationKey.hashCode());
        String $duoApiHost = this.duoApiHost;
        result = result * 59 + ($duoApiHost == null ? 43 : $duoApiHost.hashCode());
        return result;
    }
}

