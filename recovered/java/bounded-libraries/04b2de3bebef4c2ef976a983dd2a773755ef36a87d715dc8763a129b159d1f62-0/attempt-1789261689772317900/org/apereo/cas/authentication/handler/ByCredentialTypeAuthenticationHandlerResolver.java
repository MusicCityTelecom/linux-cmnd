/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationHandler
 *  org.apereo.cas.authentication.AuthenticationHandlerResolver
 *  org.apereo.cas.authentication.AuthenticationTransaction
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.util.CollectionUtils
 */
package org.apereo.cas.authentication.handler;

import java.util.Collection;
import java.util.Set;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationHandlerResolver;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.util.CollectionUtils;

public class ByCredentialTypeAuthenticationHandlerResolver
implements AuthenticationHandlerResolver {
    private final Collection<Class<? extends Credential>> credentials;

    public ByCredentialTypeAuthenticationHandlerResolver(Class<? extends Credential> ... credentials) {
        this(CollectionUtils.wrapSet((Object[])credentials));
    }

    public boolean supports(Set<AuthenticationHandler> handlers, AuthenticationTransaction transaction) {
        return this.credentials.stream().anyMatch(arg_0 -> ((AuthenticationTransaction)transaction).hasCredentialOfType(arg_0));
    }

    @Generated
    public ByCredentialTypeAuthenticationHandlerResolver(Collection<Class<? extends Credential>> credentials) {
        this.credentials = credentials;
    }
}

