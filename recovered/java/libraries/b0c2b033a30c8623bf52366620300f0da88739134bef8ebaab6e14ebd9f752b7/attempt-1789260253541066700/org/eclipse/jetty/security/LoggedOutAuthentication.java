/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.security;

import javax.servlet.ServletRequest;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.UserAuthentication;
import org.eclipse.jetty.security.authentication.LoginAuthenticator;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.UserIdentity;

public class LoggedOutAuthentication
implements Authentication.NonAuthenticated {
    private LoginAuthenticator _authenticator;

    public LoggedOutAuthentication(LoginAuthenticator authenticator) {
        this._authenticator = authenticator;
    }

    @Override
    public Authentication login(String username, Object password, ServletRequest request) {
        if (username == null) {
            return null;
        }
        UserIdentity identity = this._authenticator.login(username, password, request);
        if (identity != null) {
            IdentityService identityService = this._authenticator.getLoginService().getIdentityService();
            UserAuthentication authentication = new UserAuthentication("API", identity);
            if (identityService != null) {
                identityService.associate(identity);
            }
            return authentication;
        }
        return null;
    }
}

