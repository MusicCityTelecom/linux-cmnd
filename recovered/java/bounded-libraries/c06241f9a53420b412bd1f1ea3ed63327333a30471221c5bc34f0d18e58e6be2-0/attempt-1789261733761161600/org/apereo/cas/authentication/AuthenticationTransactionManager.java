/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication;

import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.AuthenticationManager;
import org.apereo.cas.authentication.AuthenticationResultBuilder;
import org.apereo.cas.authentication.AuthenticationTransaction;

public interface AuthenticationTransactionManager {
    public AuthenticationTransactionManager handle(AuthenticationTransaction var1, AuthenticationResultBuilder var2) throws AuthenticationException;

    public AuthenticationManager getAuthenticationManager();
}

