/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.authentication;

import org.apereo.cas.authentication.AuthenticationBuilder;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.Credential;
import org.springframework.core.Ordered;

public interface AuthenticationMetaDataPopulator
extends Ordered {
    public void populateAttributes(AuthenticationBuilder var1, AuthenticationTransaction var2);

    public boolean supports(Credential var1);

    default public int getOrder() {
        return Integer.MIN_VALUE;
    }
}

