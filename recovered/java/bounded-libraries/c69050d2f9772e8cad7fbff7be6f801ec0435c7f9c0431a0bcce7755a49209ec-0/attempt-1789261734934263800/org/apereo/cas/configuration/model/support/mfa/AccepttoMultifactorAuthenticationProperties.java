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
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.apereo.cas.configuration.model.support.replication.SessionReplicationProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-acceptto-mfa")
@JsonFilter(value="AccepttoMultifactorProperties")
public class AccepttoMultifactorAuthenticationProperties
extends BaseMultifactorAuthenticationProviderProperties {
    public static final String DEFAULT_IDENTIFIER = "mfa-acceptto";
    private static final long serialVersionUID = -2309444053833490009L;
    @RequiredProperty
    private String authnSelectionUrl = "https://mfa.acceptto.com/mfa/index";
    @RequiredProperty
    private String apiUrl = "https://mfa.acceptto.com/api/v9/";
    @RequiredProperty
    private String registrationApiUrl = "https://mfa.acceptto.com/api/integration/v1/mfa/authenticate";
    @RequiredProperty
    private String applicationId;
    @RequiredProperty
    private String secret;
    private String message = "Would you like to sign into CAS?";
    @RequiredProperty
    private String emailAttribute = "mail";
    private String groupAttribute;
    private long timeout = 120L;
    private boolean qrLoginEnabled = true;
    @RequiredProperty
    private String organizationId;
    @RequiredProperty
    private String organizationSecret;
    @RequiredProperty
    @NestedConfigurationProperty
    private SpringResourceProperties registrationApiPublicKey = new SpringResourceProperties();
    @NestedConfigurationProperty
    private SessionReplicationProperties sessionReplication = new SessionReplicationProperties();

    public AccepttoMultifactorAuthenticationProperties() {
        this.setId(DEFAULT_IDENTIFIER);
    }

    @Generated
    public String getAuthnSelectionUrl() {
        return this.authnSelectionUrl;
    }

    @Generated
    public String getApiUrl() {
        return this.apiUrl;
    }

    @Generated
    public String getRegistrationApiUrl() {
        return this.registrationApiUrl;
    }

    @Generated
    public String getApplicationId() {
        return this.applicationId;
    }

    @Generated
    public String getSecret() {
        return this.secret;
    }

    @Generated
    public String getMessage() {
        return this.message;
    }

    @Generated
    public String getEmailAttribute() {
        return this.emailAttribute;
    }

    @Generated
    public String getGroupAttribute() {
        return this.groupAttribute;
    }

    @Generated
    public long getTimeout() {
        return this.timeout;
    }

    @Generated
    public boolean isQrLoginEnabled() {
        return this.qrLoginEnabled;
    }

    @Generated
    public String getOrganizationId() {
        return this.organizationId;
    }

    @Generated
    public String getOrganizationSecret() {
        return this.organizationSecret;
    }

    @Generated
    public SpringResourceProperties getRegistrationApiPublicKey() {
        return this.registrationApiPublicKey;
    }

    @Generated
    public SessionReplicationProperties getSessionReplication() {
        return this.sessionReplication;
    }

    @Generated
    public AccepttoMultifactorAuthenticationProperties setAuthnSelectionUrl(String authnSelectionUrl) {
        this.authnSelectionUrl = authnSelectionUrl;
        return this;
    }

    @Generated
    public AccepttoMultifactorAuthenticationProperties setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
        return this;
    }

    @Generated
    public AccepttoMultifactorAuthenticationProperties setRegistrationApiUrl(String registrationApiUrl) {
        this.registrationApiUrl = registrationApiUrl;
        return this;
    }

    @Generated
    public AccepttoMultifactorAuthenticationProperties setApplicationId(String applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    @Generated
    public AccepttoMultifactorAuthenticationProperties setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    @Generated
    public AccepttoMultifactorAuthenticationProperties setMessage(String message) {
        this.message = message;
        return this;
    }

    @Generated
    public AccepttoMultifactorAuthenticationProperties setEmailAttribute(String emailAttribute) {
        this.emailAttribute = emailAttribute;
        return this;
    }

    @Generated
    public AccepttoMultifactorAuthenticationProperties setGroupAttribute(String groupAttribute) {
        this.groupAttribute = groupAttribute;
        return this;
    }

    @Generated
    public AccepttoMultifactorAuthenticationProperties setTimeout(long timeout) {
        this.timeout = timeout;
        return this;
    }

    @Generated
    public AccepttoMultifactorAuthenticationProperties setQrLoginEnabled(boolean qrLoginEnabled) {
        this.qrLoginEnabled = qrLoginEnabled;
        return this;
    }

    @Generated
    public AccepttoMultifactorAuthenticationProperties setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    @Generated
    public AccepttoMultifactorAuthenticationProperties setOrganizationSecret(String organizationSecret) {
        this.organizationSecret = organizationSecret;
        return this;
    }

    @Generated
    public AccepttoMultifactorAuthenticationProperties setRegistrationApiPublicKey(SpringResourceProperties registrationApiPublicKey) {
        this.registrationApiPublicKey = registrationApiPublicKey;
        return this;
    }

    @Generated
    public AccepttoMultifactorAuthenticationProperties setSessionReplication(SessionReplicationProperties sessionReplication) {
        this.sessionReplication = sessionReplication;
        return this;
    }
}

