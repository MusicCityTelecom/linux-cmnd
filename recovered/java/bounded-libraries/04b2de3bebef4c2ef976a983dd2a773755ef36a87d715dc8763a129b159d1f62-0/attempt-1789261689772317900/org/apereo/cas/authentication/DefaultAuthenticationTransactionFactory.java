/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.AuthenticationTransaction
 *  org.apereo.cas.authentication.AuthenticationTransactionFactory
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.DefaultAuthenticationTransaction
 *  org.apereo.cas.authentication.principal.Service
 */
package org.apereo.cas.authentication;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.AuthenticationTransactionFactory;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.DefaultAuthenticationTransaction;
import org.apereo.cas.authentication.principal.Service;

public class DefaultAuthenticationTransactionFactory
implements AuthenticationTransactionFactory {
    private static final long serialVersionUID = -3106762590844787854L;

    private static Set<Credential> sanitizeCredentials(Credential[] credentials) {
        if (credentials != null && credentials.length > 0) {
            return Arrays.stream(credentials).filter(Objects::nonNull).collect(Collectors.toCollection(LinkedHashSet::new));
        }
        return new HashSet<Credential>(0);
    }

    public AuthenticationTransaction newTransaction(Service service, Credential ... credentials) {
        Set<Credential> creds = DefaultAuthenticationTransactionFactory.sanitizeCredentials(credentials);
        return new DefaultAuthenticationTransaction(service, creds);
    }
}

