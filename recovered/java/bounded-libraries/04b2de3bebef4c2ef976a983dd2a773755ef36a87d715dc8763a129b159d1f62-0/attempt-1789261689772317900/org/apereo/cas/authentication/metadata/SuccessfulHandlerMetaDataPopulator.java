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

import java.util.HashSet;
import java.util.Set;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationBuilder;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.metadata.BaseAuthenticationMetaDataPopulator;
import org.apereo.cas.util.CollectionUtils;

public class SuccessfulHandlerMetaDataPopulator
extends BaseAuthenticationMetaDataPopulator {
    public void populateAttributes(AuthenticationBuilder builder, AuthenticationTransaction transaction) {
        Set successes = builder.getSuccesses().keySet();
        if (successes.isEmpty()) {
            builder.mergeAttribute("successfulAuthenticationHandlers", new HashSet(0));
        } else {
            builder.mergeAttribute("successfulAuthenticationHandlers", (Object)CollectionUtils.wrap(successes));
        }
    }

    public boolean supports(Credential credential) {
        return true;
    }

    @Override
    @Generated
    public String toString() {
        return "SuccessfulHandlerMetaDataPopulator(super=" + super.toString() + ")";
    }
}

