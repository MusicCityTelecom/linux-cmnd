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
import org.apereo.cas.configuration.model.core.util.ClientCertificateProperties;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-inwebo-mfa")
@JsonFilter(value="InweboMultifactorProperties")
public class InweboMultifactorAuthenticationProperties
extends BaseMultifactorAuthenticationProviderProperties {
    public static final String DEFAULT_IDENTIFIER = "mfa-inwebo";
    private static final long serialVersionUID = -942637204816051814L;
    @RequiredProperty
    private String serviceApiUrl = "https://api.myinwebo.com/FS?";
    @RequiredProperty
    private String consoleAdminUrl = "https://api.myinwebo.com/v2/services/ConsoleAdmin";
    @RequiredProperty
    private Long serviceId;
    @RequiredProperty
    private ClientCertificateProperties clientCertificate = new ClientCertificateProperties();
    @RequiredProperty
    private String siteAlias;
    private String siteDescription = "my secured site";
    private boolean trustedDeviceEnabled;
    private boolean pushEnabled = true;
    private boolean pushAuto = true;
    private BrowserAuthenticatorTypes browserAuthenticator = BrowserAuthenticatorTypes.M_ACCESS_WEB;

    public InweboMultifactorAuthenticationProperties() {
        this.setId(DEFAULT_IDENTIFIER);
    }

    @Generated
    public String getServiceApiUrl() {
        return this.serviceApiUrl;
    }

    @Generated
    public String getConsoleAdminUrl() {
        return this.consoleAdminUrl;
    }

    @Generated
    public Long getServiceId() {
        return this.serviceId;
    }

    @Generated
    public ClientCertificateProperties getClientCertificate() {
        return this.clientCertificate;
    }

    @Generated
    public String getSiteAlias() {
        return this.siteAlias;
    }

    @Generated
    public String getSiteDescription() {
        return this.siteDescription;
    }

    @Generated
    public boolean isTrustedDeviceEnabled() {
        return this.trustedDeviceEnabled;
    }

    @Generated
    public boolean isPushEnabled() {
        return this.pushEnabled;
    }

    @Generated
    public boolean isPushAuto() {
        return this.pushAuto;
    }

    @Generated
    public BrowserAuthenticatorTypes getBrowserAuthenticator() {
        return this.browserAuthenticator;
    }

    @Generated
    public InweboMultifactorAuthenticationProperties setServiceApiUrl(String serviceApiUrl) {
        this.serviceApiUrl = serviceApiUrl;
        return this;
    }

    @Generated
    public InweboMultifactorAuthenticationProperties setConsoleAdminUrl(String consoleAdminUrl) {
        this.consoleAdminUrl = consoleAdminUrl;
        return this;
    }

    @Generated
    public InweboMultifactorAuthenticationProperties setServiceId(Long serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    @Generated
    public InweboMultifactorAuthenticationProperties setClientCertificate(ClientCertificateProperties clientCertificate) {
        this.clientCertificate = clientCertificate;
        return this;
    }

    @Generated
    public InweboMultifactorAuthenticationProperties setSiteAlias(String siteAlias) {
        this.siteAlias = siteAlias;
        return this;
    }

    @Generated
    public InweboMultifactorAuthenticationProperties setSiteDescription(String siteDescription) {
        this.siteDescription = siteDescription;
        return this;
    }

    @Generated
    public InweboMultifactorAuthenticationProperties setTrustedDeviceEnabled(boolean trustedDeviceEnabled) {
        this.trustedDeviceEnabled = trustedDeviceEnabled;
        return this;
    }

    @Generated
    public InweboMultifactorAuthenticationProperties setPushEnabled(boolean pushEnabled) {
        this.pushEnabled = pushEnabled;
        return this;
    }

    @Generated
    public InweboMultifactorAuthenticationProperties setPushAuto(boolean pushAuto) {
        this.pushAuto = pushAuto;
        return this;
    }

    @Generated
    public InweboMultifactorAuthenticationProperties setBrowserAuthenticator(BrowserAuthenticatorTypes browserAuthenticator) {
        this.browserAuthenticator = browserAuthenticator;
        return this;
    }

    public static enum BrowserAuthenticatorTypes {
        NONE,
        VIRTUAL_AUTHENTICATOR,
        M_ACCESS_WEB;

    }
}

