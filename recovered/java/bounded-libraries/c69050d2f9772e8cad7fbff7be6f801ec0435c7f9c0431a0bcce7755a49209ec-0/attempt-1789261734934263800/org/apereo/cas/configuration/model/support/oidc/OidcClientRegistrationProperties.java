/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.oidc;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-oidc")
@JsonFilter(value="OidcClientRegistrationProperties")
public class OidcClientRegistrationProperties
implements Serializable {
    private static final long serialVersionUID = 123128615694269276L;
    private DynamicClientRegistrationModes dynamicClientRegistrationMode = DynamicClientRegistrationModes.PROTECTED;
    @DurationCapable
    private String clientSecretExpiration = "0";
    private String initialAccessTokenUser;
    private String initialAccessTokenPassword;

    @Generated
    public DynamicClientRegistrationModes getDynamicClientRegistrationMode() {
        return this.dynamicClientRegistrationMode;
    }

    @Generated
    public String getClientSecretExpiration() {
        return this.clientSecretExpiration;
    }

    @Generated
    public String getInitialAccessTokenUser() {
        return this.initialAccessTokenUser;
    }

    @Generated
    public String getInitialAccessTokenPassword() {
        return this.initialAccessTokenPassword;
    }

    @Generated
    public OidcClientRegistrationProperties setDynamicClientRegistrationMode(DynamicClientRegistrationModes dynamicClientRegistrationMode) {
        this.dynamicClientRegistrationMode = dynamicClientRegistrationMode;
        return this;
    }

    @Generated
    public OidcClientRegistrationProperties setClientSecretExpiration(String clientSecretExpiration) {
        this.clientSecretExpiration = clientSecretExpiration;
        return this;
    }

    @Generated
    public OidcClientRegistrationProperties setInitialAccessTokenUser(String initialAccessTokenUser) {
        this.initialAccessTokenUser = initialAccessTokenUser;
        return this;
    }

    @Generated
    public OidcClientRegistrationProperties setInitialAccessTokenPassword(String initialAccessTokenPassword) {
        this.initialAccessTokenPassword = initialAccessTokenPassword;
        return this;
    }

    public static enum DynamicClientRegistrationModes {
        OPEN,
        PROTECTED;


        public boolean isProtected() {
            return this == PROTECTED;
        }
    }
}

