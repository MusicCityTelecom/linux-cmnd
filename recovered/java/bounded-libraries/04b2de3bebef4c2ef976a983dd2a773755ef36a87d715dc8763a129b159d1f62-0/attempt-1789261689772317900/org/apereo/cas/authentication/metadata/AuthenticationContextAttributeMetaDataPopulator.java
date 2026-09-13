/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationBuilder
 *  org.apereo.cas.authentication.AuthenticationHandler
 *  org.apereo.cas.authentication.AuthenticationTransaction
 *  org.apereo.cas.authentication.Credential
 */
package org.apereo.cas.authentication.metadata;

import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationBuilder;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.metadata.BaseAuthenticationMetaDataPopulator;

public class AuthenticationContextAttributeMetaDataPopulator
extends BaseAuthenticationMetaDataPopulator {
    private final String authenticationContextAttribute;
    private final AuthenticationHandler authenticationHandler;
    private final String authenticationContextAttributeValue;

    public void populateAttributes(AuthenticationBuilder builder, AuthenticationTransaction transaction) {
        if (builder.hasAttribute("authenticationMethod", obj -> obj.toString().equals(this.authenticationHandler.getName()))) {
            builder.mergeAttribute(this.authenticationContextAttribute, (Object)this.authenticationContextAttributeValue);
        }
    }

    public boolean supports(Credential credential) {
        return this.authenticationHandler.supports(credential);
    }

    @Override
    @Generated
    public String toString() {
        return "AuthenticationContextAttributeMetaDataPopulator(super=" + super.toString() + ", authenticationContextAttribute=" + this.authenticationContextAttribute + ", authenticationHandler=" + this.authenticationHandler + ", authenticationContextAttributeValue=" + this.authenticationContextAttributeValue + ")";
    }

    @Generated
    public AuthenticationContextAttributeMetaDataPopulator(String authenticationContextAttribute, AuthenticationHandler authenticationHandler, String authenticationContextAttributeValue) {
        this.authenticationContextAttribute = authenticationContextAttribute;
        this.authenticationHandler = authenticationHandler;
        this.authenticationContextAttributeValue = authenticationContextAttributeValue;
    }
}

