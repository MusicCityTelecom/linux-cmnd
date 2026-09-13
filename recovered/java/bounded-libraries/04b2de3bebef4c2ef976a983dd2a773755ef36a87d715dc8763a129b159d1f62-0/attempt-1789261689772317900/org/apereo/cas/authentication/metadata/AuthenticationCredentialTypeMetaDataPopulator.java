/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.AuthenticationBuilder
 *  org.apereo.cas.authentication.AuthenticationTransaction
 *  org.apereo.cas.authentication.Credential
 */
package org.apereo.cas.authentication.metadata;

import org.apereo.cas.authentication.AuthenticationBuilder;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.metadata.BaseAuthenticationMetaDataPopulator;

public class AuthenticationCredentialTypeMetaDataPopulator
extends BaseAuthenticationMetaDataPopulator {
    public void populateAttributes(AuthenticationBuilder builder, AuthenticationTransaction transaction) {
        transaction.getPrimaryCredential().ifPresent(c -> builder.mergeAttribute("credentialType", (Object)c.getClass().getSimpleName()));
    }

    public boolean supports(Credential credential) {
        return credential != null;
    }
}

