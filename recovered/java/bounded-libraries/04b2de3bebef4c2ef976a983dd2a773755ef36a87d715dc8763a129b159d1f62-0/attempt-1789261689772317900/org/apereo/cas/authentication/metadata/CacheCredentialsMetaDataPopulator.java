/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apereo.cas.authentication.AuthenticationBuilder
 *  org.apereo.cas.authentication.AuthenticationTransaction
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.util.crypto.CipherExecutor
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.metadata;

import lombok.Generated;
import org.apache.commons.lang3.ArrayUtils;
import org.apereo.cas.authentication.AuthenticationBuilder;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.credential.UsernamePasswordCredential;
import org.apereo.cas.authentication.metadata.BaseAuthenticationMetaDataPopulator;
import org.apereo.cas.util.crypto.CipherExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheCredentialsMetaDataPopulator
extends BaseAuthenticationMetaDataPopulator {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheCredentialsMetaDataPopulator.class);
    private final CipherExecutor<String, String> cipherExecutor;

    public CacheCredentialsMetaDataPopulator() {
        this(null);
        LOGGER.warn("No cipher is specified to handle credential caching encryption");
    }

    public void populateAttributes(AuthenticationBuilder builder, AuthenticationTransaction transaction) {
        transaction.getPrimaryCredential().ifPresent(credential -> {
            LOGGER.debug("Processing request to capture the credential for [{}]", (Object)credential.getId());
            UsernamePasswordCredential upc = (UsernamePasswordCredential)credential;
            String psw = this.cipherExecutor == null ? upc.toPassword() : (String)this.cipherExecutor.encode((Object)upc.toPassword(), ArrayUtils.EMPTY_OBJECT_ARRAY);
            builder.addAttribute("credential", (Object)psw);
            LOGGER.debug("Credential is added as the authentication attribute [{}] to the authentication", (Object)"credential");
        });
    }

    public boolean supports(Credential credential) {
        return credential instanceof UsernamePasswordCredential;
    }

    @Override
    @Generated
    public String toString() {
        return "CacheCredentialsMetaDataPopulator(super=" + super.toString() + ", cipherExecutor=" + this.cipherExecutor + ")";
    }

    @Generated
    public CacheCredentialsMetaDataPopulator(CipherExecutor<String, String> cipherExecutor) {
        this.cipherExecutor = cipherExecutor;
    }
}

