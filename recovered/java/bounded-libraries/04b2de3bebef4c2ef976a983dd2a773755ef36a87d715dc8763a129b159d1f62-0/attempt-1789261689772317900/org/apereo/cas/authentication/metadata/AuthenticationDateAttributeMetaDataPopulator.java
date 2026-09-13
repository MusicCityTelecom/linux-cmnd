/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationBuilder
 *  org.apereo.cas.authentication.AuthenticationTransaction
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.MultifactorAuthenticationCredential
 */
package org.apereo.cas.authentication.metadata;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationBuilder;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.MultifactorAuthenticationCredential;
import org.apereo.cas.authentication.metadata.BaseAuthenticationMetaDataPopulator;

public class AuthenticationDateAttributeMetaDataPopulator
extends BaseAuthenticationMetaDataPopulator {
    public void populateAttributes(AuthenticationBuilder builder, AuthenticationTransaction transaction) {
        builder.addAttribute("authenticationDate", (Object)ZonedDateTime.now(ZoneOffset.UTC).toEpochSecond());
    }

    public boolean supports(Credential credential) {
        return credential != null && !(credential instanceof MultifactorAuthenticationCredential);
    }

    @Override
    @Generated
    public String toString() {
        return "AuthenticationDateAttributeMetaDataPopulator(super=" + super.toString() + ")";
    }

    @Generated
    public AuthenticationDateAttributeMetaDataPopulator() {
    }
}

