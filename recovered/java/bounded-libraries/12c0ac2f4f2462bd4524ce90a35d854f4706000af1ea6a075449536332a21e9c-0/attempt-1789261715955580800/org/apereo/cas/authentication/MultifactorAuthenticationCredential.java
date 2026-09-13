/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.Credential
 */
package org.apereo.cas.authentication;

import org.apereo.cas.authentication.Credential;

@FunctionalInterface
public interface MultifactorAuthenticationCredential
extends Credential {
    default public String getProviderId() {
        return null;
    }

    default public void setProviderId(String providerId) {
    }
}

