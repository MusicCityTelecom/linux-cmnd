/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.security.authentication;

import javax.servlet.http.HttpServletRequest;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.server.UserIdentity;

@FunctionalInterface
public interface AuthorizationService {
    public UserIdentity getUserIdentity(HttpServletRequest var1, String var2);

    public static AuthorizationService from(LoginService loginService, Object credentials) {
        return (request, name) -> loginService.login(name, credentials, request);
    }
}

