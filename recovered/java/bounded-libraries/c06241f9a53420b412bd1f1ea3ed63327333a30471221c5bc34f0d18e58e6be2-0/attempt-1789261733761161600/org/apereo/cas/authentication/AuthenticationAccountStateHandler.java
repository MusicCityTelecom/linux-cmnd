/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication;

import java.util.List;
import javax.security.auth.login.LoginException;
import org.apereo.cas.authentication.MessageDescriptor;

@FunctionalInterface
public interface AuthenticationAccountStateHandler<AuthnResponse, Configuration> {
    public List<MessageDescriptor> handle(AuthnResponse var1, Configuration var2) throws LoginException;
}

