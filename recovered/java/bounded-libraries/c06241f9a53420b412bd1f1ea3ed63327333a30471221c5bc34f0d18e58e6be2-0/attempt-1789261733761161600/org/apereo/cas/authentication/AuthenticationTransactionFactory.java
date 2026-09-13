/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication;

import java.io.Serializable;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.principal.Service;

@FunctionalInterface
public interface AuthenticationTransactionFactory
extends Serializable {
    public AuthenticationTransaction newTransaction(Service var1, Credential ... var2);

    default public AuthenticationTransaction newTransaction(Credential ... credentials) {
        return this.newTransaction((Service)null, credentials);
    }
}

