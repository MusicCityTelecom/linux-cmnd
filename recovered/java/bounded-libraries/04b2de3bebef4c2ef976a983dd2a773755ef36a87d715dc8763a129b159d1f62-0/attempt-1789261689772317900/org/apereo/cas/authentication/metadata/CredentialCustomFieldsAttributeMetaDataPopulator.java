/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationBuilder
 *  org.apereo.cas.authentication.AuthenticationTransaction
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.util.CollectionUtils
 */
package org.apereo.cas.authentication.metadata;

import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationBuilder;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.credential.UsernamePasswordCredential;
import org.apereo.cas.authentication.metadata.BaseAuthenticationMetaDataPopulator;
import org.apereo.cas.util.CollectionUtils;

public class CredentialCustomFieldsAttributeMetaDataPopulator
extends BaseAuthenticationMetaDataPopulator {
    public void populateAttributes(AuthenticationBuilder builder, AuthenticationTransaction transaction) {
        transaction.getPrimaryCredential().ifPresent(cred -> {
            UsernamePasswordCredential upc = (UsernamePasswordCredential)UsernamePasswordCredential.class.cast(cred);
            upc.getCustomFields().forEach((key, value) -> builder.mergeAttribute(key, (Object)CollectionUtils.toCollection((Object)value)));
        });
    }

    public boolean supports(Credential credential) {
        return credential instanceof UsernamePasswordCredential;
    }

    @Override
    @Generated
    public String toString() {
        return "CredentialCustomFieldsAttributeMetaDataPopulator(super=" + super.toString() + ")";
    }

    @Generated
    public CredentialCustomFieldsAttributeMetaDataPopulator() {
    }
}

